package buffer;

import entities.OrdinePQ;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Interfaccia che permette al thread Checker di interagire con il Buffer
 */
public interface CheckerIF {

    /**
     * Restituisci una lista di coppie chiave, Ordine in modo da poter interrogare il buffer.
     * Questa lista segue una finestra di lunghezza predefinita,
     * che si sposta permettendo di iterare tutto il buffer con successive iterazioni.
     *
     * @return lista di coppie chiave, Ordine.
     */
    LinkedList<Map.Entry<Integer, OrdinePQ>> getWindow();

    /**
     * Aggiorna la priorità di un ordine presente nella coda a priorià
     *
     * @param key identificativo dell'ordine nel dizionario
     * @param priorita valore di priorità
     */
    void updatePQ(int key, double priorita) throws InterruptedException;

    /**
     * Ritorna il semaforo di muta esclusivita'
     *
     * @return semaforo BUSY
     */
    Semaphore getBusy();
}
