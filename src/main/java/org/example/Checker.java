import java.util.LinkedList;

public class Checker implements Runnable{

    CheckerIF buffer;

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

            try {
                LinkedList<OrdinePQ> list = buffer.getWindow();
                if(list.isEmpty()){
                    System.out.println("nulla da controllare");
                }
                for(OrdinePQ ordinePQ : list){
                    // ordinePQ.aggiornaPriorita();
                    // aggiorna buffer (la priority queue)
                    System.out.println("controllo: " + ordinePQ);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
