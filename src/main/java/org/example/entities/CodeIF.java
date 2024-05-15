package entities;


/**
 * Interfaccia che consente di aggiungere ordini alla cucina
 */
public interface CodeIF {

    /**
     * Inserisci un ordine all'interno della rispettiva coda di postazione se essa è libera.
     * Richiede che non sia superato il numero massimo di ordini che possono stare contemporaneamente in cucina,
     * ossia non sia superato MAX_CAPACITY.
     *
     * @param ordinePQ ordine da inserire nella coda di postazione adeguata.
     * @return true se l'ordine è stato inserito, false altrimenti.
     * @throws RuntimeException eccezione di Runtime.
     * @throws InterruptedException eccezione di Interrupted.
     */
    boolean push(OrdinePQ ordinePQ) throws RuntimeException, InterruptedException;

}
