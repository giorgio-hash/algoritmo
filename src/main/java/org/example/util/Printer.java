package util;

import entities.OrdinePQ;

import java.util.Iterator;
import java.util.Optional;

/**
 * Classe di utilità che permette di generare dei log personalizzati
 */
public class Printer {

    /**
     * Stampa il log del thread (lavora con ordine opzionale)
     *
     * @param unique_id identificatore univoco del thread.
     * @param thread_name nome del thread.
     * @param ordine opzionale che contiene l'entità ordine.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLog(String unique_id, String thread_name , Optional<OrdinePQ> ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                        + "-secs:" + System.nanoTime()
                        + "-id:" + (ordine.isPresent()?ordine.get().getId():0)
                        + "-type:" + (ordine.isPresent()?ordine.get().getIngredientePrincipale():0)
                        + "-thread:" + thread_name
                        + "-state:" + (begin_end?"end":"begin")
                        + ":::");
    }

    /**
     * Stampa il log del thread
     *
     * @param unique_id identificatore univoco del thread.
     * @param thread_name nome del thread.
     * @param ordine entità ordine.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLog(String unique_id, String thread_name , OrdinePQ ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                        + "-secs:" + System.nanoTime()
                        + "-id:" + ordine.getId()
                        + "-type:" + ordine.getIngredientePrincipale()
                        + "-thread:" + thread_name
                        + "-state:" + (begin_end?"end":"begin")
                        + ":::");
    }

    /**
     * Stampa il log del thread (dato l'id dell'ordine)
     *
     * @param unique_id identificatore univoco del thread.
     * @param thread_name nome del thread.
     * @param id_ordine id dell'entità ordine.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLog(String unique_id, String thread_name , int id_ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                        + "-secs:" + System.nanoTime()
                        + "-id:" + id_ordine
                        + "-type:" + 0
                        + "-thread:" + thread_name
                        + "-state:" + (begin_end?"end":"begin")
                        + ":::");
    }

    /**
     * Stampa il log del thread Cuoco
     *
     * @param unique_id identificatore univoco del thread.
     * @param id_ordine id dell'entità ordine.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLogCuoco(int unique_id, int id_ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                + "-secs:" + System.nanoTime()
                + "-id:" + id_ordine
                + "-thread:cuoco"
                + "-state:" + (begin_end?"end":"begin")
                + ":::");
    }

    /**
     * Stampa il log del thread Checker
     *
     * @param unique_id identificatore univoco del thread.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLogChecker(int unique_id, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                + "-secs:" + System.nanoTime()
                + "-id:0"
                + "-thread:checker"
                + "-state:" + (begin_end?"end":"begin")
                + ":::");
    }

    /**
     * Stampa il log del thread Consumer
     *
     * @param unique_id identificatore univoco del thread.
     * @param id_ordine id dell'entità ordine.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLogConsumer(int unique_id, int id_ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                + "-secs:" + System.nanoTime()
                + "-id:" + id_ordine
                + "-thread:consumer"
                + "-state:" + (begin_end?"end":"begin")
                + ":::");
    }

    /**
     * Stampa il log del thread Producer
     *
     * @param unique_id identificatore univoco del thread.
     * @param id_ordine id dell'entità ordine.
     * @param begin_end booleano che dice se il thread è iniziato o è finito.
     */
    public static void stampaLogProducer(int unique_id, int id_ordine, boolean begin_end){
            System.out.println(
                    "uuid:" + unique_id
                        + "-secs:" + System.nanoTime()
                        + "-id:" + id_ordine
                        + "-thread:producer"
                        + "-state:" + (begin_end?"end":"begin")
                        + ":::");
    }

    /**
     * Metodo per stampare un messaggio insieme allo stato attuale della coda
     *
     * @param mex messaggio.
     * @param struttura struttura dati iterabile
     */
    public static void stampa(String mex,Iterable struttura){
        System.out.println(mex+"|| "+stampa_coda(struttura));
    }

    /**
     * Metodo per stampare lo stato attuale della coda
     *
     * @param struttura struttura dati iterabile
     */
    public static void stampa(Iterable struttura){
        System.out.println(stampa_coda(struttura));
    }

    /**
     * Metodo per ottenere una rappresentazione testuale dello stato attuale della coda
     * @param struttura struttura dati iterabile
     * @return stringa contenente una rappresentazione testuale dello stato attuale della coda
     */
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
