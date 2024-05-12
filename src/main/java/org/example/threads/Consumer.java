package threads;

import buffer.ConsumerIF;
import entities.GestioneCode;
import entities.OrdinePQ;

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
                ordinePQ = buffer.getMinPQ();
                ordinePQ.ifPresent(pq -> {
                    try {
                        if(!gestioneCode.push(pq)){
                            System.out.println("Consumer: problemi nell'inserimento di: " + pq);
                            System.out.println("Consumer: reinserisco nel buffer l'ordine: " + pq);
                            producer.addToHighPriorityQueue(pq);
                        }
                    } catch (Exception e) {
                        System.out.println("Consumer: problemi nell'inserimento di: " + pq);
                        throw new RuntimeException(e);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ordinePQ.ifPresent(ordinePQ1 -> System.out.println("Consumer: estratto: " + ordinePQ1));
        }
    }
}
