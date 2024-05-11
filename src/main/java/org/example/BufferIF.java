import java.util.Optional;

public interface BufferIF {

    /**
     *  Inserisci l'ordine all'interno del buffer.
     *
     * @param ordinePQ ordine da inserire.
     */
    void insertInBuffer(OrdinePQ ordinePQ) throws Exception;

    /**
     * Rimuovi l'ordine a priorità più elevata dal buffer.
     *
     * @return ordine a priorità più elevata.
     */
    Optional<OrdinePQ> getMinPQ() throws InterruptedException;

}
