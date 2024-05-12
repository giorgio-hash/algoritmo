package threads;

import buffer.ProducerIF;
import entities.OrdinePQ;
import util.GestionePriorita;

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
                        System.out.println("Producer: rilevato ordine in arrivo.");
                        OrdinePQ ordinePQ = queue.poll();
                        System.out.println("Producer: calcolo della priorità per: " + ordinePQ);
                        GestionePriorita.setPriorita(ordinePQ);
                        buffer.insertInBuffer(ordinePQ);
                        //util.Printer.stampa("polling producer",queue); // ! : lancia una eccezione
                        System.out.println("Producer: inserimento avvenuto con successo: " + ordinePQ);
                    }
                } catch (Exception e) {
                    System.out.println("Producer: errore - inserimento non avvenuto");
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
