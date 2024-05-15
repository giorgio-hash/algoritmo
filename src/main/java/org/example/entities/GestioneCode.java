package entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;

/**
 * Entità cucina, contiene e gestisce le code di postazione.
 */
public class GestioneCode implements CodeIF, FrontSignalPort {

    /**
     * Istanza per generare una Singleton class
     */
    private static GestioneCode INSTANCE;

    /**
     * Postazioni della cucina
     */
    private final HashMap<String, CodaPostazioneIF> postazioni;

    /**
     * numero massimo di ordini che possono essere nelle code di postazione contemporaneamente.
     */
    private final int MAX_CAPACITY = 10;

    /**
     * Semaforo per indicare che il buffer è pieno
     */
    private Semaphore FULL;

    /**
     * Semaforo per indicare che il buffer è vuoto
     */
    private Semaphore EMPTY;

    /**
     * Si creano le code di postazione.
     * Si inizializzano i semafori.
     */
    private GestioneCode() {
        this.postazioni = new HashMap<>();

        CodaPostazioneIF codaPostazioneA = new CodaPostazione(IngredientePrincipale.RISO);
        CodaPostazioneIF codaPostazioneB = new CodaPostazione(IngredientePrincipale.PASTA);
        CodaPostazioneIF codaPostazioneC = new CodaPostazione(IngredientePrincipale.CARNE);
        CodaPostazioneIF codaPostazioneD = new CodaPostazione(IngredientePrincipale.PESCE);

        postazioni.put("RISO", codaPostazioneA);
        postazioni.put("PASTA", codaPostazioneB);
        postazioni.put("CARNE", codaPostazioneC);
        postazioni.put("PESCE", codaPostazioneD);

        EMPTY = new Semaphore(MAX_CAPACITY);  // Inizializzazione del semaforo EMPTY con il numero massimo di permessi
        FULL = new Semaphore(0);  // Inizializzazione del semaforo FULL con 0 permessi iniziali (buffer vuoto)
    }

    /**
     * Crea un'istanza della classe GestioneCode in modo da poterne creare solamente una univoca.
     * Richiama il costruttore privato (Pattern Singleton class).
     *
     * @return istanza univoca della classe GestioneCode.
     */
    public static GestioneCode getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new GestioneCode();
        }
        return INSTANCE;
    }

    /**
     * Richiesta per ottenere una specifica coda di postazione in base all'identificativo ingrediente principale
     *
     * @param ingredientePrincipale identificativo della codaPostazione <i>String</i>
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>codaPostazioneDTO</i> oppure <i>null</i>
     */
    @Override
    public Optional<CodaPostazioneIF> getCodaPostazione(IngredientePrincipale ingredientePrincipale) {
        return Optional.ofNullable(postazioni.get(ingredientePrincipale.toString()));
    }

    /**
     * Richiesta per ottenere l'ordine che deve essere preparato in una specifica coda di postazione
     * in base all'identificativo ingrediente principale
     *
     * @param ingredientePrincipale identificativo della codaPostazione <i>String</i>
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>codaPostazioneDTO</i> oppure <i>null</i>
     */
    @Override
    public Optional<OrdinePQ> getOrder(String ingredientePrincipale) {
        Optional<CodaPostazioneIF> codaPostazione =
                Optional.ofNullable(postazioni.get(ingredientePrincipale.toUpperCase()));
        if(codaPostazione.isPresent()) {
            Optional<OrdinePQ> ordinePQ = codaPostazione.get().element();
            if (ordinePQ.isPresent()) {
                return ordinePQ;
            }
        }
        return Optional.empty();
    }

    /**
     * Notifica riguardo l'avvenuta preparazione di un ordine da parte di una determinata postazione della cucina.
     *
     * @param ingredientePrincipale identificativo della postazione della cucina responsabile
     */
    @Override
    public void postNotifica(IngredientePrincipale ingredientePrincipale, OrdinePQ ordinePQ)
            throws InterruptedException {

        FULL.acquire();  // Acquisizione del semaforo FULL (si blocca se il buffer è vuoto)

        Optional<CodaPostazioneIF> codaPostazione = Optional.ofNullable(postazioni.get(ingredientePrincipale.toString()));
        if(codaPostazione.isPresent()) {
            Optional<OrdinePQ> top_queue = codaPostazione.get().element();
            if(top_queue.isPresent() && top_queue.get().equals(ordinePQ)){
                Optional<OrdinePQ> ordineEntity = codaPostazione.get().remove();
                ordinePQ.setStato(3);
                if (ordineEntity.isPresent()) {
                    System.out.println("GestioneCode: rimosso ordine: " + ordineEntity);
                    System.out.println("GestioneCode: stampa di tutte le code: " + this);
                    EMPTY.release(); // Rilascio del semaforo EMPTY per segnalare che il buffer ha un posto libero in più
                    return;
                }
            }
            else System.out.println("GestioneCode: Ordine non presente o coda vuota");
        }
        else {
            System.out.println("GestioneCode: Coda non esiste");
        }
        System.out.println("GestioneCode: stampa di tutte le code: " + this);

        EMPTY.release(); // Rilascio del semaforo EMPTY per segnalare che il buffer ha un posto libero in più
    }

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
    @Override
    public boolean push(OrdinePQ ordinePQ) throws RuntimeException, InterruptedException {

        boolean res; // rispota ritornata

        boolean acquired = EMPTY.tryAcquire();  // Acquisizione del semaforo EMPTY (si blocca se il buffer è pieno)
        if(acquired) {
            CodaPostazioneIF coda_selezionata = postazioni.get(ordinePQ.getIngredientePrincipale().toString());
            res = coda_selezionata.insert(ordinePQ);
            if(!res){
                EMPTY.release();
                System.out.println("Postazione " +
                        ordinePQ.getIngredientePrincipale().toString() + " piena! : " + this);
                return false;
            }
            System.out.println("GestioneCode: Postazione Aggiornata: " +
                    postazioni.get(ordinePQ.getIngredientePrincipale().toString()));
            System.out.println("GestioneCode: stampa di tutte le code: " + this);
            FULL.release();  // Rilascio del semaforo FULL per segnalare che il buffer contiene un elemento in più
        } else{
            System.out.println("GestioneCode: Capacità massima di ordini in cucina raggiunta: " + MAX_CAPACITY);
            System.out.println("GestioneCode: stampa di tutte le code: " + this);
            res = false;
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CodaPostazioneIF> entry : postazioni.entrySet()) {
            sb.append("\n").append(entry.getKey()).append(", ").append(entry.getValue());
        }
        // Rimuovi l'ultima virgola e lo spazio in eccesso
        // Stampa il risultato
       return sb.toString().replaceAll(", $", "");
    }
}