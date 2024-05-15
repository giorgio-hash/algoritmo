package entities;

import java.util.Optional;

/**
 * Interfaccia per interagire con la coda di una singola postazione della cucina.
 */
public interface CodaPostazioneIF {

    /**
     * inserisci l'ordine specificato nella coda se non viola il vincolo di capacita'.
     *
     * @param ordineDTO ordine da inserire.
     * @return {@code true} se è stato aggiunto correttamente, {@code false} altrimenti.
     */
    boolean insert(OrdinePQ ordineDTO) throws InterruptedException;

    /**
     * rimuovi l'elemento in testa alla coda se presente
     *
     * @return Optional contentente la testa della coda se non è vuota, Optional contenente null altrimenti
     */
    Optional<OrdinePQ> remove() throws InterruptedException;

    /**
     * restituisce ma non rimuove l'elemento in testa alla coda se presente
     *
     * @return Optional contentente la testa della coda se non è vuota, Optional contenente null altrimenti
     */
    Optional<OrdinePQ> element();

}
