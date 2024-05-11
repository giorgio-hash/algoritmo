import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Dizionario {

    private final Map<Integer, OrdinePQ> ordinePQMap; // {id : priorita}
    private final ArrayList<Boolean>  chiaviDisponibili;
    private int MAX_SIZE = 10;

    // Costruttore
    public Dizionario(int BUFFER_SIZE) {
        this.MAX_SIZE = BUFFER_SIZE;
        ordinePQMap = new HashMap<>();
        chiaviDisponibili = new ArrayList<>();
        // Aggiungi le chiavi al pool
        for(int i = 0; i <= MAX_SIZE ; i++){
            chiaviDisponibili.add(true);
        }
    }

    // Metodo per aggiungere una parola e il suo significato al dizionario
    public int aggiungiOrdine(OrdinePQ ordinePQ) throws Exception {
        int i = 1;
        while(!chiaviDisponibili.get(i) && i <= MAX_SIZE){
            i+=1;
        }
        if(chiaviDisponibili.get(i)){
            chiaviDisponibili.set(i,false);
            ordinePQMap.put(i, ordinePQ);
        }
        else{
            throw new Exception("Non ci sono più chiavi disponibili.");
        }
        return i;
    }

    // Metodo per rimuovere una chiave dal dizionario
    public Optional<OrdinePQ> rimuoviOrdine(int chiave) {
        // Aggiungi la chiave corrispondente alla lista delle chiavi disponibili
        chiaviDisponibili.set(chiave, true);
        // Rimuovi l'elemento dalla mappa
        return Optional.ofNullable(ordinePQMap.remove(chiave));
    }

    // Metodo per cercare la priorita di un id
    public Optional<OrdinePQ> cercaOrdine(int chiave) {
        return Optional.ofNullable(ordinePQMap.get(chiave));
    }

    public int getSize(){
        return ordinePQMap.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ordinePQMap.forEach((key, value) -> stringBuilder.append("Chiave: ").append(key).append(", Valore: ").append(value));
        return stringBuilder.toString();
    }
}

