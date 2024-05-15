package entities;

import java.util.Optional;

/**
 * Interfaccia che permette al cuoco di interagire con la cucina.
 */
public interface FrontSignalPort {

    /**
     * Richiesta per ottenere una specifica coda di postazione in base all'identificativo ingrediente principale
     *
     * @param ingredientePrincipale identificativo della codaPostazione <i>String</i>
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>codaPostazioneDTO</i> oppure <i>null</i>
     */
    Optional<CodaPostazioneIF> getCodaPostazione(IngredientePrincipale ingredientePrincipale);

    /**
     * Richiesta per ottenere l'ordine che deve essere preparato in una specifica coda di postazione
     * in base all'identificativo ingrediente principale
     *
     * @param ingredientePrincipale identificativo della codaPostazione <i>String</i>
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>codaPostazioneDTO</i> oppure <i>null</i>
     */
    Optional<OrdinePQ> getOrder(String ingredientePrincipale);

    /**
     * Notifica riguardo l'avvenuta preparazione di un ordine da parte di una determinata postazione della cucina.
     *
     * @param ingredientePrincipale identificativo della postazione della cucina responsabile
     */
    void postNotifica(IngredientePrincipale ingredientePrincipale, OrdinePQ ordinePQ)
            throws InterruptedException;


}