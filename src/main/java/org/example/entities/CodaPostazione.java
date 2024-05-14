package entities;

import util.OrderWaitingTimeLogger;

import java.sql.Timestamp;
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
    private IngredientePrincipale ingredientePrincipale;

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
    private Queue<OrdinePQ> queue;

    /**
     * Semaforo di mutua esclusività
     */
    private Semaphore BUSY;

    public CodaPostazione(IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.numeroOrdiniPresenti = 0;
        this.gradoRiempimento = 0.0;
        this.queue = new LinkedList<>();
        ingredientePrincipale.setValore(gradoRiempimento);
        BUSY = new Semaphore(1);             // Inizializzazione del semaforo BUSY con 1 permesso (accesso esclusivo)
    }

    public boolean insert(OrdinePQ ordineDTO) throws InterruptedException {
        BUSY.acquire();
        if (numeroOrdiniPresenti >= capacita) {
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

    public Optional<OrdinePQ> element() {
        return Optional.ofNullable(queue.peek());
    }

    public boolean isFull() {
        return numeroOrdiniPresenti == capacita;
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