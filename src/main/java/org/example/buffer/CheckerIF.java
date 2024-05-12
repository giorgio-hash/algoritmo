package buffer;

import entities.OrdinePQ;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public interface CheckerIF {

    LinkedList<Map.Entry<Integer, OrdinePQ>> getWindow() throws InterruptedException;

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
