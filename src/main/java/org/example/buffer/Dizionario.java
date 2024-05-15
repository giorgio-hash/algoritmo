package buffer;

import entities.OrdinePQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * La Classe Dizionario permette di creare una mappa tra l'ordine e una chiave intera tra 0 e MAX_SIZE - 1
 * generata automaticamente dalla stessa classe dizionario sulla base delle chiavi disponibili al momento
 * dell'inserimento.
 */
class Dizionario {

    private final Map<Integer, OrdinePQ> ordinePQMap; // {chiave : ordine}
    private final ArrayList<Boolean>  chiaviDisponibili;
    private final int MAX_SIZE;

    /**
     * Crea un dizionario nuovo con dimensione pari al BUFFER_SIZE.
     * Crea un array di chiavi disponibili e lo popola aggiungendo tutti i numeri da zero a BUFFER_SIZE.
     *
     * @param BUFFER_SIZE dimensione massima del dizionario.
     */
    Dizionario(int BUFFER_SIZE) {
        this.MAX_SIZE = BUFFER_SIZE;
        ordinePQMap = new HashMap<>();
        chiaviDisponibili = new ArrayList<>();
        // Aggiungi le chiavi al pool
        for(int i = 0; i < MAX_SIZE ; i++){
            chiaviDisponibili.add(true);
        }
    }

    /**
     * Aggiungi un ordine all'interno del dizionario con chiave assegnata automaticamente in base a quelle disponibili.
     *
     * @param ordinePQ entità ordine da aggiungere nel dizionario.
     * @return restituisce la chiave a cui viene associato l'ordine.
     * @throws Exception se non ci sono più chiavi disponibili.
     */
    int aggiungiOrdine(OrdinePQ ordinePQ) throws Exception {
        int i = 0;
        while(i < MAX_SIZE && !chiaviDisponibili.get(i)){
            i+=1;
        }
        if(chiaviDisponibili.get(i)){
            chiaviDisponibili.set(i,false);
            ordinePQMap.put(i, ordinePQ);
        }
        else{
            throw new Exception("Dizionario: Non ci sono più chiavi disponibili.");
        }
        return i;
    }

    /**
     * Rimuovi e restituisci l'ordine dal dizionario che ha come chiave quella passata come parametro.
     *
     * @param chiave chiave dell'ordine da rimuovere.
     * @return Opzionale che contiene l'ordine rimosso se esiste, null altrimenti.
     */
    Optional<OrdinePQ> rimuoviOrdine(int chiave) {
        // Aggiungi la chiave corrispondente alla lista delle chiavi disponibili
        chiaviDisponibili.set(chiave, true);
        // Rimuovi l'elemento dalla mappa
        return Optional.ofNullable(ordinePQMap.remove(chiave));
    }

    /**
     * Cerca e restituisci (senza rimozione) l'ordine all'interno del dizionario con chiave quella passata come
     * parametro.
     *
     * @param chiave chiave dell'ordine da cercare.
     * @return Opzionale che contiene l'ordine se esiste, null altrimenti.
     */
    Optional<OrdinePQ> cercaOrdine(int chiave) {
        return Optional.ofNullable(ordinePQMap.get(chiave));
    }

    /**
     * Restituisce la dimensione attuale del dizionario.
     *
     * @return dimensione del dizionario.
     */
    int getSize(){
        return ordinePQMap.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ordinePQMap.forEach((key, value) -> stringBuilder.append("Chiave: ").append(key).append(", Valore: ").append(value));
        return stringBuilder.toString();
    }
}

