package threads;

import buffer.CheckerIF;
import entities.OrdinePQ;
import util.GestionePriorita;
import util.Printer;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Thread che ha il compito di aggiornare il buffer.
 */
public class Checker implements Runnable {

    /**
     * Buffer sul quale il checker deve compiere l'operazione di controllo.
     */
    private final CheckerIF buffer;

    //log
    /**
     * Generatore locale di id.
     */
    private int localIDGenerator = 0;

    /**
     * Prefisso uuid.
     */
    private final String uuid_prefix = "ch";

    /**
     * Costruttore del thread checker.
     *
     * @param checkerIF checkerIF sul quale il checker deve compiere l'operazione di controllo.
     */
    public Checker(final CheckerIF checkerIF) {
        this.buffer = checkerIF;
    }

    /**
     * Resta in attesa per un periodo,
     * richiede l'accesso esclusivo al buffer.
     * richiede dal buffer una lista di ordini da controllare,
     * aggiorna la priorità di ogni ordine controllato e in seguito ad ognuno aggiorna il buffer.
     * rilascia l'accesso esclusivo.
     */
    @Override
    public void run() {

        while(true) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Semaphore BUSY = buffer.getBusy();

            //stampa di log
            //unique id per riga log
            localIDGenerator++;
            Printer.stampaLog(
                    uuid_prefix+localIDGenerator,
                    Thread.currentThread().getName(),
                    0,
                    false);

            try {
                BUSY.acquire();
                System.out.println("***** Checker controlla il buffer *****");
                LinkedList<Map.Entry<Integer, OrdinePQ>> list = buffer.getWindow();
                if(list.isEmpty()){
                    System.out.println("Checker: nulla da controllare");
                }
                for(Map.Entry<Integer, OrdinePQ> entry : list){
                    double priorita = GestionePriorita.setPriorita(entry.getValue()); // calcola la priorita'
                    System.out.println("Checker: aggiorno la priorità di: " + entry.getValue());
                    buffer.updatePQ(entry.getKey(), priorita); // aggiorna la indexed priority queue
                    System.out.println("Checker: priorità aggiornata: " + entry.getValue());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("***** Checker rilascia il buffer *****");
                BUSY.release();
            }

            Printer.stampaLog(
                    uuid_prefix+localIDGenerator,
                    Thread.currentThread().getName(),
                    0,
                    true);

        }
    }
}
