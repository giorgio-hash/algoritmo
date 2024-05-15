package threads;

import buffer.ProducerIF;
import entities.OrdinePQ;
import util.GestionePriorita;
import util.Printer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Thread che permette di inserire un ordine all'interno del buffer.
 */
public class Producer implements Runnable{

    /**
     * Buffer sul quale il producer deve inserire gli ordini.
     */
    private final ProducerIF buffer;

    /**
     * lock interno per sincronizzazione.
     */
    private final Object lock = new Object();

    //POLLING
    /**
     * Coda interna del producer, sulla quale riceve gli ordini dai clienti.
     */
    private final BlockingQueue<OrdinePQ> queue = new LinkedBlockingQueue<>();

    /**
     * Coda ad alta priorità del producer, sulla quale riceve gli ordini da reinserire immediatamente nel buffer.
     */
    private final BlockingQueue<OrdinePQ> highPriorityQueue = new LinkedBlockingQueue<>();

    //log
    /**
     * generatore locale di id.
     */
    private int localIDGenerator = 0;

    /**
     * prefisso uuid.
     */
    private final String uuid_prefix = "p";

    /**
     * Costruttore del thread producer
     *
     * @param buffer buffer sul quale il producer deve inserire gli ordini.
     */
    public Producer(ProducerIF buffer) {
        this.buffer = buffer;
    }

    /**
     * Aggiungi un ordine alla coda interna del producer
     * @param value ordine da aggiungere alla coda.
     */
    public void addToQueue(OrdinePQ value) {
        synchronized (lock) {
            queue.offer(value); // Add to the queue
            System.out.print("producer queue: " + queue.toString());
        }
    }

    /**
     * Aggiungi un ordine da inserire immediatamente nel buffer.
     *
     * @param ordinePQ ordine da inserire immediatamente nel buffer.
     */
    public void addToHighPriorityQueue(OrdinePQ ordinePQ){
        synchronized (lock) {
            highPriorityQueue.add(ordinePQ);
        }
    }

    /**
     * Resta in attesa per un periodo,
     * controlla se ha ordini in arrivo sulla coda ad alta priorità, se si:
     * richiede l'accesso esclusivo al buffer.
     * aggiunge l'ordine ad alta priorità nel buffer
     * rilascia l'accesso esclusivo.
     * altrimenti controlla se ha ordini in arrivo sulla coda interna, se si:
     * richiede l'accesso esclusivo al buffer.
     * aggiunge l'ordine nel buffer
     * rilascia l'accesso esclusivo.
     */
    @Override
    public void run() {

        while(true){
            try {
                Thread.sleep(500);
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
                                ordinePQ,
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
                                    ordinePQ,
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
