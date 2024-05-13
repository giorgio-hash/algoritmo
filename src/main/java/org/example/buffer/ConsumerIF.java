package buffer;

import entities.OrdinePQ;

import java.util.Optional;

public interface ConsumerIF {


    /**
     * Rimuovi l'ordine a priorità più elevata dal buffer.
     *
     * @return ordine a priorità più elevata.
     */
    Optional<OrdinePQ> getMinPQ() throws InterruptedException;

    /**
     *  Rinserisci l'ordine all'interno del buffer.
     *
     * @param ordinePQ ordine da re_inserire.
     */
    void insertInBuffer(OrdinePQ ordinePQ) throws Exception;

}
