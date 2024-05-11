import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable{

    private final ProducerIF buffer;
    private final Object lock = new Object(); // Internal lock

    //POLLING
    private final BlockingQueue<OrdinePQ> queue = new LinkedBlockingQueue<>();
    public Producer(ProducerIF buffer) {
        this.buffer = buffer;
    }

    public void addToQueue(OrdinePQ value) {
        synchronized (lock) {
            queue.offer(value); // Add to the queue
        }
    }

    @Override
    public void run() {

        while(true){
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized(lock){
                try {
                    if(!queue.isEmpty()){
                        OrdinePQ ordinePQ = queue.poll();
                        GestionePriorita.setPriorita(ordinePQ); // TODO: Assegnare la priorita all'ordine
                        buffer.insertInBuffer(ordinePQ);
                        //Printer.stampa("polling producer",queue); // ! : lancia una eccezione
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
