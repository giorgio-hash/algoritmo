package entities;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

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

    public CodaPostazione(IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.numeroOrdiniPresenti = 0;
        this.gradoRiempimento = 0.0;
        this.queue = new LinkedList<>();
        ingredientePrincipale.setValore(gradoRiempimento);
    }

    public boolean insert(OrdinePQ ordineDTO) {
        if (numeroOrdiniPresenti >= capacita)
            return false;
        boolean status = queue.offer(ordineDTO);
        if (status) {
            this.numeroOrdiniPresenti += 1;
            this.gradoRiempimento = ((double) numeroOrdiniPresenti / capacita);
            ingredientePrincipale.setValore(gradoRiempimento);
        }
        return status;
    }

    public Optional<OrdinePQ> remove() {
        Optional<OrdinePQ> ordinePQ = Optional.ofNullable(queue.poll());
        if (ordinePQ.isPresent()) {
            this.numeroOrdiniPresenti -= 1;
            this.gradoRiempimento = ((double) numeroOrdiniPresenti / capacita);
            ingredientePrincipale.setValore(gradoRiempimento);
        }
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