package entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Entita' CUCINA
public class GestioneCode {

    private HashMap<String, CodaPostazione> postazioni;

    public GestioneCode() {
        this.postazioni = new HashMap<>();

        CodaPostazione codaPostazioneA = new CodaPostazione(IngredientePrincipale.RISO);
        CodaPostazione codaPostazioneB = new CodaPostazione(IngredientePrincipale.PASTA);
        CodaPostazione codaPostazioneC = new CodaPostazione(IngredientePrincipale.CARNE);
        CodaPostazione codaPostazioneD = new CodaPostazione(IngredientePrincipale.PESCE);

        postazioni.put("RISO", codaPostazioneA);
        postazioni.put("PASTA", codaPostazioneB);
        postazioni.put("CARNE", codaPostazioneC);
        postazioni.put("PESCE", codaPostazioneD);
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

    public void push(OrdinePQ ordinePQ) throws RuntimeException {
        CodaPostazione coda_selezionata = postazioni.get(ordinePQ.getIngredientePrincipale().toString());
        coda_selezionata.insert(ordinePQ);
        System.out.println("Coda Aggiornata: " + postazioni.get(ordinePQ.getIngredientePrincipale().toString()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CodaPostazione> entry : postazioni.entrySet()) {
            sb.append(entry.getKey()).append(", ").append(entry.getValue()).append(" - ");
        }
        // Rimuovi l'ultima virgola e lo spazio in eccesso
        String result = sb.toString().replaceAll(", $", "");
        // Stampa il risultato
       return result;
    }
}