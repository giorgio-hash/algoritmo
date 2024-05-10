import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable{

    private Buffer buffer;
    private final Object lock = new Object(); // Internal lock

    //POLLING
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    public Producer(Buffer buffer) {
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
                        buffer.insert(queue.poll());
                        stampa("polling producer");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



    private void stampa(String mex){
        System.out.println(mex+"|| "+stampa_coda());
    }
    private void stampa(){
        System.out.println(stampa_coda());
    }


    public String stampa_coda(){
        StringBuilder result = new StringBuilder();
        for (String element : queue) {
            result.append(element).append(",");
        }
        // Remove the trailing comma
        if (!result.isEmpty()) {
            result.deleteCharAt(result.length() - 1);
        }
        return "[" + result.toString() + "]";
    }
}
