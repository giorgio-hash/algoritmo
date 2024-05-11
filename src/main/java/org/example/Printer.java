import java.util.Iterator;

public class Printer {

    // Metodo privato per stampare un messaggio insieme allo stato attuale della coda
    public static void stampa(String mex,Iterable struttura){
        System.out.println(mex+"|| "+stampa_coda(struttura));
    }

    // Metodo privato per stampare lo stato attuale della coda
    public static void stampa(Iterable struttura){
        System.out.println(stampa_coda(struttura));
    }

    // Metodo per ottenere una rappresentazione testuale dello stato attuale della coda
    public static String stampa_coda(Iterable struttura){
        StringBuilder result = new StringBuilder();
        // Itera attraverso gli elementi della coda e li aggiunge alla stringa di risultato
        for (Iterator<Integer> it = struttura.iterator(); it.hasNext(); ) {
            int element = it.next();
            result.append(element).append(",");
        }
        // Rimuove l'eventuale virgola finale
        if (!result.isEmpty()) {
            result.deleteCharAt(result.length() - 1);
        }
        return "[" + result.toString() + "]";  // Restituisce una rappresentazione testuale della coda
    }
}
