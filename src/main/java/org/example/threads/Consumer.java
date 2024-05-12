package threads;

import buffer.ConsumerIF;
import entities.GestioneCode;
import entities.OrdinePQ;

import java.util.Optional;

public class Consumer implements Runnable{

    ConsumerIF buffer;
    GestioneCode gestioneCode;
    private final Object lock = new Object(); // Internal lock


    public Consumer(ConsumerIF buffer, GestioneCode gestioneCode) {
        this.buffer = buffer;
        this.gestioneCode = gestioneCode;
    }

    @Override
    public void run() {

        Optional<OrdinePQ> ordinePQ;
        while(true){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock) {
                try {
                    System.out.println("Consumer: chiedo l'ordine a priorità più alta dal buffer...");
                    ordinePQ = buffer.getMinPQ();
                    ordinePQ.ifPresent(pq -> gestioneCode.push(pq));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ordinePQ.ifPresent(ordinePQ1 -> System.out.println("Consumer: estratto: " + ordinePQ1));
            }
        }
    }
}
