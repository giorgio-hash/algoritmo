package entities;

import util.OrderWaitingTimeLogger;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Coda di una singola postazione della cucina
 */
public class CodaPostazione {

    /**
     * identificativo della coda di postazione (IN MAIUSCOLO)
     */
    private final IngredientePrincipale ingredientePrincipale;

    /**
     * numero ordini presenti in coda
     */
    private int numeroOrdiniPresenti;

    /**
     * grado di riempimento attuale della coda
     */
    private double gradoRiempimento;

    /**
     * capacita' massima della coda
     */
    private final int capacita = 5;

    /**
     * coda di ordinazioni
     */
    private final Queue<OrdinePQ> queue;

    /**
     * Semaforo di mutua esclusività
     */
    private final Semaphore BUSY;

    /**
     * Assegna la tipologia di coda in base all'ingrediente principale.
     * Inizializza il numero di ordini presenti e il grado di riempimento a zero.
     * Crea la coda di ordini della postazioni con capacita' pari alla costante capacita.
     * Inizializza il semaforo di mutua escluìsione.
     *
     * @param ingredientePrincipale tipo di coda di postazione.
     */
    public CodaPostazione(IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.numeroOrdiniPresenti = 0;
        this.gradoRiempimento = 0.0;
        this.queue = new LinkedList<>();
        ingredientePrincipale.setValore(gradoRiempimento);
        BUSY = new Semaphore(1); // Inizializzazione del semaforo BUSY con 1 permesso (accesso esclusivo)
    }

    /**
     * inserisci l'ordine specificato nella coda se non viola il vincolo di capacita'.
     *
     * @param ordineDTO ordine da inserire.
     * @return {@code true} se è stato aggiunto correttamente, {@code false} altrimenti.
     */
    public boolean insert(OrdinePQ ordineDTO) throws InterruptedException {
        BUSY.acquire();
        if (isFull()) {
            BUSY.release();
            return false;
        }
        boolean status = queue.offer(ordineDTO);
        if (status) {
            this.numeroOrdiniPresenti += 1;
            this.gradoRiempimento = ((double) numeroOrdiniPresenti / capacita);
            ingredientePrincipale.setValore(gradoRiempimento);
        }
        BUSY.release();
        return status;
    }

    /**
     * rimuovi l'elemento in testa alla coda se presente
     *
     * @return Optional contentente la testa della coda se non è vuota, Optional contenente null altrimenti
     */
    public Optional<OrdinePQ> remove() throws InterruptedException {
        BUSY.acquire();
        Optional<OrdinePQ> ordinePQ = Optional.ofNullable(queue.poll());
        if (ordinePQ.isPresent()) {
            OrderWaitingTimeLogger.logOrder(ordinePQ.get());
            this.numeroOrdiniPresenti -= 1;
            this.gradoRiempimento = ((double) numeroOrdiniPresenti / capacita);
            ingredientePrincipale.setValore(gradoRiempimento);
        }
        BUSY.release();
        return ordinePQ;
    }

    /**
     * restituisce ma non rimuove l'elemento in testa alla coda se presente
     *
     * @return Optional contentente la testa della coda se non è vuota, Optional contenente null altrimenti
     */
    public Optional<OrdinePQ> element() {
        return Optional.ofNullable(queue.peek());
    }

    /**
     * permette di capire se la coda è piena oppure no
     *
     * @return {@code true} se la coda è piena, {@code false} altrimenti
     */
    public boolean isFull() {
        return numeroOrdiniPresenti >= capacita;
    }

    @Override
    public String toString() {
        return "entities.CodaPostazione{" +
                "ingredientePrincipale=" + ingredientePrincipale +
                ", numeroOrdiniPresenti=" + numeroOrdiniPresenti +
                ", gradoRiempimento=" + gradoRiempimento +
                ", capacita=" + capacita +
                ", queue=" + queue +
                '}';
    }

}