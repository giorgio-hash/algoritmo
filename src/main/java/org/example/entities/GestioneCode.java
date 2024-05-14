package entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;

// Entita' CUCINA
public class GestioneCode {

    private static GestioneCode INSTANCE; // Singleton class

    private final HashMap<String, CodaPostazione> postazioni;

    /**
     * numero massimo di ordini che possono essere nelle code di postazione contemporaneamente.
     */
    private final int MAX_CAPACITY = 10;

    private Semaphore FULL;   // Semaforo per indicare che il buffer è pieno
    private Semaphore EMPTY;  // Semaforo per indicare che il buffer è vuoto

    private GestioneCode() {
        this.postazioni = new HashMap<>();

        CodaPostazione codaPostazioneA = new CodaPostazione(IngredientePrincipale.RISO);
        CodaPostazione codaPostazioneB = new CodaPostazione(IngredientePrincipale.PASTA);
        CodaPostazione codaPostazioneC = new CodaPostazione(IngredientePrincipale.CARNE);
        CodaPostazione codaPostazioneD = new CodaPostazione(IngredientePrincipale.PESCE);

        postazioni.put("RISO", codaPostazioneA);
        postazioni.put("PASTA", codaPostazioneB);
        postazioni.put("CARNE", codaPostazioneC);
        postazioni.put("PESCE", codaPostazioneD);

        EMPTY = new Semaphore(MAX_CAPACITY);  // Inizializzazione del semaforo EMPTY con il numero massimo di permessi
        FULL = new Semaphore(0);             // Inizializzazione del semaforo FULL con 0 permessi iniziali (buffer vuoto)
    }

    public static GestioneCode getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new GestioneCode();
        }
        return INSTANCE;
    }

    public Optional<CodaPostazione> getCodaPostazione(IngredientePrincipale ingredientePrincipale) {
        return Optional.ofNullable(postazioni.get(ingredientePrincipale.toString()));
    }

    public Optional<OrdinePQ> getOrder(String ingredientePrincipale) {
        Optional<CodaPostazione> codaPostazione = Optional.ofNullable(postazioni.get(ingredientePrincipale.toUpperCase()));
        if(codaPostazione.isPresent()) {
            Optional<OrdinePQ> ordinePQ = codaPostazione.get().element();
            if (ordinePQ.isPresent()) {
                return ordinePQ;
            }
        }
        return Optional.empty();
    }

    public Optional<OrdinePQ> postNotifica(IngredientePrincipale ingredientePrincipale, OrdinePQ ordinePQ) throws InterruptedException {

        FULL.acquire();  // Acquisizione del semaforo FULL (si blocca se il buffer è vuoto)

        Optional<CodaPostazione> codaPostazione = Optional.ofNullable(postazioni.get(ingredientePrincipale.toString()));
        if(codaPostazione.isPresent()) {
            Optional<OrdinePQ> top_queue = codaPostazione.get().element();
            if(top_queue.isPresent() && top_queue.get().equals(ordinePQ)){
                Optional<OrdinePQ> ordineEntity = codaPostazione.get().remove();
                ordinePQ.setStato(3);
                if (ordineEntity.isPresent()) {
                    System.out.println("GestioneCode: rimosso ordine: " + ordineEntity);
                    System.out.println("GestioneCode: stampa di tutte le code: " + this);
                    EMPTY.release(); // Rilascio del semaforo EMPTY per segnalare che il buffer ha un posto libero in più
                    return ordineEntity;
                }
            }
            else System.out.println("GestioneCode: Ordine non presente o coda vuota");
        }
        else {
            System.out.println("GestioneCode: Coda non esiste");
        }
        System.out.println("GestioneCode: stampa di tutte le code: " + this);

        EMPTY.release(); // Rilascio del semaforo EMPTY per segnalare che il buffer ha un posto libero in più
        return Optional.empty();
    }

    public boolean push(OrdinePQ ordinePQ) throws RuntimeException, InterruptedException {

        boolean res;

        boolean acquired = EMPTY.tryAcquire();  // Acquisizione del semaforo EMPTY (si blocca se il buffer è pieno)
        if(acquired) {
            CodaPostazione coda_selezionata = postazioni.get(ordinePQ.getIngredientePrincipale().toString());
            res = coda_selezionata.insert(ordinePQ);
            if(!res){
                EMPTY.release();
                System.out.println("Postazione " + ordinePQ.getIngredientePrincipale().toString() + " piena! : " + this);
                return false;
            }
            System.out.println("GestioneCode: Postazione Aggiornata: " + postazioni.get(ordinePQ.getIngredientePrincipale().toString()));
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
        for (Map.Entry<String, CodaPostazione> entry : postazioni.entrySet()) {
            sb.append("\n").append(entry.getKey()).append(", ").append(entry.getValue());
        }
        // Rimuovi l'ultima virgola e lo spazio in eccesso
        String result = sb.toString().replaceAll(", $", "");
        // Stampa il risultato
       return result;
    }
}