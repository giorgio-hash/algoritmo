public interface ProducerIF {

    /**
     *  Inserisci l'ordine all'interno del buffer.
     *
     * @param ordinePQ ordine da inserire.
     */
    void insertInBuffer(OrdinePQ ordinePQ) throws Exception;

}
