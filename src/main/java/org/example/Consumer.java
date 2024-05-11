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

        Optional<OrdinePQ> ordinePQ = Optional.empty();
        while(true){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (lock) {
                try {
                    ordinePQ = buffer.getMinPQ();
                    ordinePQ.ifPresent(pq -> gestioneCode.push(pq));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ordinePQ.ifPresent(ordinePQ1 -> System.out.println("estratto: " + ordinePQ1));
            }
        }
    }
}
