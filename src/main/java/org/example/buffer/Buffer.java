package buffer;

import entities.OrdinePQ;
import util.Printer;
import util.UniqueIdGenerator;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;

/**
 * Classe Buffer che può essere vista come una coda di BUFFER_SIZE elementi.
 * Contiene la Indexed Priority Queue (IndexMinPQ) e il Dizionario per le associazioni chiavi ordine.
 * Permette al thread PRODUCER di inserire un ordine all'interno del buffer.
 * Permette al thread CHECKER di ricevere una lista di ordini da aggiornare e di aggiornare il buffer.
 * Permette al thread CONSUMER di estrarre l'ordine a priorità piì elevata.
 * La sincronizzazione viene gestita tramite Semafori.
 */
public class Buffer implements ProducerIF, ConsumerIF, CheckerIF {

    private static Buffer INSTANCE; // Singleton class

    // Dichiarazione dei semafori per sincronizzare l'accesso al buffer
    private Semaphore FULL;   // Semaforo per indicare che il buffer è pieno
    private Semaphore EMPTY;  // Semaforo per indicare che il buffer è vuoto
    private Semaphore BUSY;   // Semaforo per gestire l'accesso esclusivo al buffer

    private IndexMinPQ<Double> indexMinPQ;

    private Dizionario dizionario;

    private final int BUFFER_SIZE = 10;

    private final int WINDOW_SIZE = 3;

    private int iterator; // variabile che scorre il buffer

    /**
     * Crea il Dizionario, La IndexMinPQ e inizializza i semafori.
     */
    private Buffer() {
        this.indexMinPQ = new IndexMinPQ<>(BUFFER_SIZE);
        this.dizionario = new Dizionario(BUFFER_SIZE);
        this.iterator = 1;

        EMPTY = new Semaphore(BUFFER_SIZE-1);  // Inizializzazione del semaforo EMPTY con il numero massimo di permessi
        FULL = new Semaphore(0);             // Inizializzazione del semaforo FULL con 0 permessi iniziali (buffer vuoto)
        BUSY = new Semaphore(1);             // Inizializzazione del semaforo BUSY con 1 permesso (accesso esclusivo)
    }

    /**
     * Crea un'istanza della classe Buffer in modo da poterne creare solamente una univoca.
     * Richiama il costruttore privato (Pattern Singleton class).
     *
     * @return istanza univoca della classe Buffer.
     */
    public static Buffer getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Buffer();
        }
        return INSTANCE;
    }

    /**
     *  Inserisci l'ordine all'interno del buffer.
     *
     * @param ordinePQ ordine da inserire.
     */
    @Override
    public void insertInBuffer(OrdinePQ ordinePQ) throws Exception {
        EMPTY.acquire();  // Acquisizione del semaforo EMPTY (si blocca se il buffer è pieno)
        BUSY.acquire();   // Acquisizione del semaforo BUSY per eseguire l'accesso esclusivo al buffer

        //stampe di log
        //unique id per riga log
        //UniqueIdGenerator.getInstance().newGeneratedId();
        //Printer.stampaLogProducer(UniqueIdGenerator.getInstance().getGeneratedId(),
        //        ordinePQ.getId(),
        //        false);


        System.out.println("***** Producer controlla il buffer *****");
        int id = dizionario.aggiungiOrdine(ordinePQ);
        indexMinPQ.insert(id, ordinePQ.getValorePriorita());


        //stampa di log
        //Printer.stampaLogProducer(UniqueIdGenerator.getInstance().getGeneratedId(),
        //        ordinePQ.getId(),
        //        true);

        Printer.stampa("Buffer: inserimento " + ordinePQ,indexMinPQ);

        if(dizionario.getSize() == BUFFER_SIZE)
        {
            Printer.stampa("pieno!",indexMinPQ);
        }

        System.out.println("***** Producer rilascia il buffer *****");
        BUSY.release();  // Rilascio del semaforo BUSY (fine dell'accesso esclusivo)
        FULL.release();  // Rilascio del semaforo FULL per segnalare che il buffer contiene un elemento in più
    }

    /**
     * Rimuovi l'ordine a priorità più elevata dal buffer.
     *
     * @return ordine a priorità più elevata.
     */
    @Override
    public Optional<OrdinePQ> getMinPQ() throws InterruptedException {

        FULL.acquire();  // Acquisizione del semaforo FULL (si blocca se il buffer è vuoto)
        BUSY.acquire();  // Acquisizione del semaforo BUSY per eseguire l'accesso esclusivo al buffer

        //stampa di log
        //unique id per riga log
        //UniqueIdGenerator.getInstance().newGeneratedId();
        //Printer.stampaLogConsumer(UniqueIdGenerator.getInstance().getGeneratedId(), 0, false);

        //System.out.println("***** Consumer controlla il buffer *****");
        int i = indexMinPQ.delMin();
        // System.out.println("estrazione " + i + " " + dizionario.cercaOrdine(i));
        //Printer.stampa("estrazione: " + i,indexMinPQ);

        //stampa di log
        //Printer.stampaLogConsumer(UniqueIdGenerator.getInstance().getGeneratedId(), i, true);

        //System.out.println("***** Consumer rilascia il buffer *****");
        BUSY.release();  // Rilascio del semaforo BUSY (fine dell'accesso esclusivo)
        EMPTY.release(); // Rilascio del semaforo EMPTY per segnalare che il buffer ha un posto libero in più

        return dizionario.rimuoviOrdine(i);

    }

    /**
     * Ritorna il semaforo di muta esclusivita'
     *
     * @return semaforo BUSY
     */
    public Semaphore getBusy() {   // Semaforo per gestire l'accesso esclusivo al buffer
        return BUSY;
    }

    /**
     * Restituisci una lista di coppie chiave, Ordine in modo da poter interrogare il buffer.
     * Questa lista segue una finestra di lunghezza predefinita,
     * che si sposta permettendo di iterare tutto il buffer con successive iterazioni.
     *
     * @return lista di coppie chiave, Ordine.
     */
    @Override
    public LinkedList<Map.Entry<Integer, OrdinePQ>> getWindow() {

        //System.out.println("controllo...");
        //System.out.println("ordini presenti nel buffer: " + dizionario.toString());

        //stampa di log
        //unique id per riga log
        //UniqueIdGenerator.getInstance().newGeneratedId();
        //Printer.stampaLogChecker(UniqueIdGenerator.getInstance().getGeneratedId(),
        //        false);


        int size = dizionario.getSize();
        int window = WINDOW_SIZE;
        LinkedList<Map.Entry<Integer, OrdinePQ>> list = new LinkedList<>();
        if (size<WINDOW_SIZE){
            window = size;
        }
        while(window > 0) {
            if (dizionario.cercaOrdine(iterator).isPresent()) {
                list.add(new AbstractMap.SimpleEntry<>(iterator, dizionario.cercaOrdine(iterator).get()));
                window -=1;
            }
            if(iterator <= BUFFER_SIZE - 1) {
                iterator += 1;
            }
            else {
                iterator = 0;
            }
        }

        //stampa di log
        //Printer.stampaLogChecker(UniqueIdGenerator.getInstance().getGeneratedId(),
        //        true);

        return list;
    }

    /**
     * Aggiorna la priorità di un ordine presente nella coda a priorià
     *
     * @param key identificativo dell'ordine nel dizionario
     * @param priorita valore di priorità
     */
    @Override
    public void updatePQ(int key, double priorita) {
        indexMinPQ.changeKey(key, priorita);
    }

}
