package threads;

import buffer.ProducerIF;
import entities.OrdinePQ;
import util.GestionePriorita;
import util.Printer;
import util.UniqueIdGenerator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable{

    private final ProducerIF buffer;
    private final Object lock = new Object(); // Internal lock

    //POLLING
    private final BlockingQueue<OrdinePQ> queue = new LinkedBlockingQueue<>();
    private final BlockingQueue<OrdinePQ> highPriorityQueue = new LinkedBlockingQueue<>();

    //log
    private int localIDGenerator = 0;
    private final String uuid_prefix = "p";

    public Producer(ProducerIF buffer) {
        this.buffer = buffer;
    }

    public void addToQueue(OrdinePQ value) {
        synchronized (lock) {
            queue.offer(value); // Add to the queue
        }
    }

    public void addToHighPriorityQueue(OrdinePQ ordinePQ){
        synchronized (lock) {
            highPriorityQueue.add(ordinePQ);
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
                    if(!highPriorityQueue.isEmpty()){
                        System.out.println("Producer: rilevato ordine da reinserire nel buffer.");
                        OrdinePQ ordinePQ = highPriorityQueue.poll();
                        //stampe di log
                        //unique id per riga log
                        localIDGenerator++;
                        Printer.stampaLog(
                                uuid_prefix+localIDGenerator,
                                Thread.currentThread().getName(),
                                0,
                                false);

                        buffer.insertInBuffer(ordinePQ);
                        //stampa di log
                        Printer.stampaLog(
                                uuid_prefix+localIDGenerator,
                                Thread.currentThread().getName(),
                                ordinePQ.getId(),
                                true);
                        System.out.println("Producer: reinserimento avvenuto con successo: " + ordinePQ);
                    } else {
                        if (!queue.isEmpty()) {
                            System.out.println("Producer: rilevato ordine in arrivo.");
                            //stampe di log
                            //unique id per riga log
                            localIDGenerator++;
                            Printer.stampaLog(
                                    uuid_prefix+localIDGenerator,
                                    Thread.currentThread().getName(),
                                    0,
                                    false);

                            OrdinePQ ordinePQ = queue.poll();
                            System.out.println("Producer: calcolo della priorità per: " + ordinePQ);
                            GestionePriorita.setPriorita(ordinePQ);
                            buffer.insertInBuffer(ordinePQ);
                            //util.Printer.stampa("polling producer",queue); // ! : lancia una eccezione
                            //stampa di log
                            Printer.stampaLog(
                                    uuid_prefix+localIDGenerator,
                                    Thread.currentThread().getName(),
                                    ordinePQ.getId(),
                                    true);
                            System.out.println("Producer: inserimento avvenuto con successo: " + ordinePQ);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Producer: errore - inserimento non avvenuto");
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
