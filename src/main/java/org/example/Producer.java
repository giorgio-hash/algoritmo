import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable{

    private BufferIF buffer;
    private final Object lock = new Object(); // Internal lock

    //POLLING
    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
    public Producer(BufferIF buffer) {
        this.buffer = buffer;
    }

    public void addToQueue(String value) {
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
                        buffer.insertInBuffer((OrdinePQ) queue.poll());
                        Printer.stampa("polling producer",queue);
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
