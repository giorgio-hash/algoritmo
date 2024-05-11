import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class Buffer implements ProducerIF, ConsumerIF, CheckerIF {


    // Dichiarazione dei semafori per sincronizzare l'accesso al buffer
    private Semaphore FULL;   // Semaforo per indicare che il buffer è pieno
    private Semaphore EMPTY;  // Semaforo per indicare che il buffer è vuoto
    private Semaphore BUSY;   // Semaforo per gestire l'accesso esclusivo al buffer

    private IndexMinPQ<Double> indexMinPQ;

    private Dizionario dizionario;

    private final int BUFFER_SIZE = 10;

    private final int WINDOW_SIZE = 3;

    private int iterator;



    public Buffer() {
        this.indexMinPQ = new IndexMinPQ<>(BUFFER_SIZE);
        this.dizionario = new Dizionario();
        this.iterator = 1;

        EMPTY = new Semaphore(BUFFER_SIZE);  // Inizializzazione del semaforo EMPTY con il numero massimo di permessi
        FULL = new Semaphore(0);             // Inizializzazione del semaforo FULL con 0 permessi iniziali (buffer vuoto)
        BUSY = new Semaphore(1);             // Inizializzazione del semaforo BUSY con 1 permesso (accesso esclusivo)

    }

    @Override
    public void insertInBuffer(OrdinePQ ordinePQ) throws Exception {
        EMPTY.acquire();  // Acquisizione del semaforo EMPTY (si blocca se il buffer è pieno)
        BUSY.acquire();   // Acquisizione del semaforo BUSY per eseguire l'accesso esclusivo al buffer

        indexMinPQ.insert(dizionario.aggiungiOrdine(ordinePQ),ordinePQ.getValorePriorita());

        Printer.stampa("inserimento" + ordinePQ,indexMinPQ);

        if(dizionario.getSize() == BUFFER_SIZE)
        {
            Printer.stampa("pieno!",indexMinPQ);
        }

        BUSY.release();  // Rilascio del semaforo BUSY (fine dell'accesso esclusivo)
        FULL.release();  // Rilascio del semaforo FULL per segnalare che il buffer contiene un elemento in più
    }

    @Override
    public Optional<OrdinePQ> getMinPQ() throws InterruptedException {

        FULL.acquire();  // Acquisizione del semaforo FULL (si blocca se il buffer è vuoto)
        BUSY.acquire();  // Acquisizione del semaforo BUSY per eseguire l'accesso esclusivo al buffer

        int i = indexMinPQ.delMin();
        // System.out.println("estrazione " + i + " " + dizionario.cercaOrdine(i));
        Printer.stampa("estrazione: " + i,indexMinPQ);

        BUSY.release();  // Rilascio del semaforo BUSY (fine dell'accesso esclusivo)
        EMPTY.release(); // Rilascio del semaforo EMPTY per segnalare che il buffer ha un posto libero in più

        return dizionario.rimuoviOrdine(i);

    }


    @Override
    public LinkedList<OrdinePQ> getWindow() throws InterruptedException {

        BUSY.acquire();  // Acquisizione del semaforo BUSY per eseguire un controll

        System.out.println("controllo...");
        System.out.println("ordini presenti nel buffer: " + dizionario.toString());

        int size = dizionario.getSize();
        int window = WINDOW_SIZE;
        LinkedList<OrdinePQ> list = new LinkedList<>();
        if (size<WINDOW_SIZE){
            window = size;
        }
        while(window > 0) {
            if (dizionario.cercaOrdine(iterator).isPresent()) {
                list.add(dizionario.cercaOrdine(iterator).get());
                window -=1;
            }
            if(iterator <= BUFFER_SIZE - 1) {
                iterator += 1;
            }
            else {
                iterator = 1;
            }
        }

        BUSY.release();  // Rilascio del semaforo BUSY dopo il controllo

        return list;
    }

}
