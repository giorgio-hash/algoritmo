import java.util.LinkedList;
import java.util.Map;

public class Checker implements Runnable{

    CheckerIF buffer;
    private final Object lock = new Object(); // Internal lock


    public Checker(CheckerIF buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while(true){

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized(lock){
                try {
                    LinkedList<Map.Entry<Integer, OrdinePQ>> list = buffer.getWindow();
                    if(list.isEmpty()){
                        System.out.println("nulla da controllare");
                    }
                    for(Map.Entry<Integer, OrdinePQ> entry : list){
                        double priorita = GestionePriorita.setPriorita(entry.getValue()); // calcola la priorita'
                        buffer.updatePQ(entry.getKey(), priorita); // aggiorna la indexed priority queue
                        System.out.println("controllo: " + entry.getValue());
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
