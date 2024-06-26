package threads;

import buffer.ConsumerIF;
import entities.CodeIF;
import entities.OrdinePQ;
import util.Printer;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * Thread che ha il compito di estrarre ordini dal buffer e inserirli in cucina.
 */
public class Consumer implements Runnable{

    /**
     * Buffer centrale (da cui prende l'ordine)
     */
    private ConsumerIF buffer;

    /**
     * Buffer della cucina (in cui deve inserire l'ordine)
     */
    private CodeIF cucina;
    /**
     * Thread del Producer
     */
    private Producer producer;

    //log
    /**
     * identificatore locale
     */
    private int localIDGenerator = 0;
    /**
     * prefisso uuid
     */
    private final String uuid_prefix = "c";

    /**
     * Costruttore del thread Consumer
     *
     * @param buffer buffer centrale dal quale deve estrarre gli ordini
     * @param cucina buffer sul quale deve destinare gli ordini
     * @param producer thread del Producer
     */
    public Consumer(ConsumerIF buffer, CodeIF cucina, Producer producer) {
        this.buffer = buffer;
        this.cucina = cucina;
        this.producer = producer;
    }

    /**
     * Resta in attesa per un periodo,
     * richiede l'accesso esclusivo al buffer.
     * estrae l'ordine a priorità elevata,
     * rilascia l'accesso esclusivo.
     * acontrolla se lo può inserire in cucina, se si lo fa,
     * altrimenti rimette l'ordine nel buffer passandolo al producer.
     */
    @Override
    public void run() {

        Optional<OrdinePQ> ordinePQ;
        while(true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                System.out.println("Consumer: chiedo l'ordine a priorità più alta dal buffer...");

                //stampa di log
                //unique id per riga log
                localIDGenerator++;
                Printer.stampaLog(
                        uuid_prefix+localIDGenerator,
                        Thread.currentThread().getName(),
                        0,
                        false);

                ordinePQ = buffer.getMinPQ();
                ordinePQ.ifPresent(pq -> {
                    try {
                        if(!cucina.push(pq)){
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

                Printer.stampaLog(
                        uuid_prefix+localIDGenerator,
                        Thread.currentThread().getName(),
                        ordinePQ,
                        true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ordinePQ.ifPresent(ordinePQ1 -> System.out.println("Consumer: estratto: " + ordinePQ1));
        }
    }
}
