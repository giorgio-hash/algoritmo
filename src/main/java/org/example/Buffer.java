import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Buffer {

    private Semaphore FULL;
    private Semaphore EMPTY;
    private Semaphore BUSY;


    private final int BUFFER_SIZE;
    private final Queue<String> queue = new LinkedList<>();

    public Buffer(int max_val) {
        this.BUFFER_SIZE = max_val;
        EMPTY = new Semaphore(BUFFER_SIZE);
        FULL = new Semaphore(0);
        BUSY = new Semaphore(1);
    }

    public void insert(Object item) throws InterruptedException {

        EMPTY.acquire();
        BUSY.acquire();

        if(item instanceof String)
            queue.add((String) item);

        stampa("inserimento");

        if(queue.size() == BUFFER_SIZE)
        {
            stampa("pieno!");
        }
        BUSY.release();
        FULL.release();
    }

    public Object extract() throws InterruptedException {

        FULL.acquire();
        BUSY.acquire();

        System.out.println("estrazione");
        Object out = queue.poll();

        if(FULL.availablePermits() == 0) {
            FULL.release();
            System.out.println("posto libero");
        }

        BUSY.release();
        EMPTY.release();

        return out;
    }


    public void control() throws InterruptedException {
        BUSY.acquire();

        stampa("controllo");

        BUSY.release();

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
