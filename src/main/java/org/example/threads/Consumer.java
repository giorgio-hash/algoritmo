package threads;

import buffer.ConsumerIF;
import entities.GestioneCode;
import entities.OrdinePQ;
import util.Printer;
import util.UniqueIdGenerator;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class Consumer implements Runnable{

    ConsumerIF buffer;
    GestioneCode gestioneCode;
    Producer producer;

    public Consumer(ConsumerIF buffer, GestioneCode gestioneCode, Producer producer) {
        this.buffer = buffer;
        this.gestioneCode = gestioneCode;
        this.producer = producer;
    }

    @Override
    public void run() {

        Optional<OrdinePQ> ordinePQ;
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                System.out.println("Consumer: chiedo l'ordine a priorità più alta dal buffer...");

                //stampa di log
                //unique id per riga log
                UniqueIdGenerator.getInstance().newGeneratedId();
                Printer.stampaLogConsumer(UniqueIdGenerator.getInstance().getGeneratedId(),
                        0,
                        false);

                ordinePQ = buffer.getMinPQ();
                ordinePQ.ifPresent(pq -> {
                    try {
                        if(!gestioneCode.push(pq)){
                            System.out.println("Consumer: problemi nell'inserimento di: " + pq);
                            System.out.println("Consumer: reinserisco nel buffer l'ordine: " + pq);
                            producer.addToHighPriorityQueue(pq);
                        }
                        Timestamp tAttuale = Timestamp.from(Instant.now());
                        long t = tAttuale.getTime() - pq.gettOrdinazione().getTime(); // in millisecondi
                        pq.settInCoda(Duration.ofMillis(t));
                    } catch (Exception e) {
                        System.out.println("Consumer: problemi nell'inserimento di: " + pq);
                        throw new RuntimeException(e);
                    }
                });

                Printer.stampaLogConsumer(UniqueIdGenerator.getInstance().getGeneratedId(),
                        ordinePQ.map(OrdinePQ::getId).orElse(0),
                        true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ordinePQ.ifPresent(ordinePQ1 -> System.out.println("Consumer: estratto: " + ordinePQ1));
        }
    }
}
