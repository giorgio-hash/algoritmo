package threads;

import buffer.CheckerIF;
import entities.OrdinePQ;
import util.GestionePriorita;
import util.Printer;
import util.UniqueIdGenerator;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Checker implements Runnable{

    CheckerIF buffer;
    private final Object lock = new Object(); // Internal lock

    //log
    private int localIDGenerator = 0;
    private final String uuid_prefix = "ch";

    public Checker(CheckerIF buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while(true){

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

            synchronized(lock){
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
            }

            Printer.stampaLog(
                    uuid_prefix+localIDGenerator,
                    Thread.currentThread().getName(),
                    0,
                    true);

        }
    }
}
