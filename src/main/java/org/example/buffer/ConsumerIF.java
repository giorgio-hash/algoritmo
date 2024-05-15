package buffer;

import entities.OrdinePQ;

import java.util.Optional;

/**
 * Interfaccia che permette al thread Consumer di interagire con il Buffer.
 */
public interface ConsumerIF {

    /**
     * Rimuovi l'ordine a priorità più elevata dal buffer.
     *
     * @return ordine a priorità più elevata.
     */
    Optional<OrdinePQ> getMinPQ() throws InterruptedException;

}
