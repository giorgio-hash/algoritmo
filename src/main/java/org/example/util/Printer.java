package util;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;

public class Printer {


    private Printer(){

    }

    public static void stampaLog(String unique_id, String thread_name , int id_ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                        + "-secs:" + System.nanoTime()
                        + "-id:" + id_ordine
                        + "-thread:" + thread_name
                        + "-state:" + (begin_end?"end":"begin")
                        + ":::");
    }

    public static void stampaLogCuoco(int unique_id, int id_ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                + "-secs:" + System.nanoTime()
                + "-id:" + id_ordine
                + "-thread:cuoco"
                + "-state:" + (begin_end?"end":"begin")
                + ":::");
    }

    public static void stampaLogChecker(int unique_id, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                + "-secs:" + System.nanoTime()
                + "-id:0"
                + "-thread:checker"
                + "-state:" + (begin_end?"end":"begin")
                + ":::");
    }
    public static void stampaLogConsumer(int unique_id, int id_ordine, boolean begin_end){
        System.out.println(
                "uuid:" + unique_id
                + "-secs:" + System.nanoTime()
                + "-id:" + id_ordine
                + "-thread:consumer"
                + "-state:" + (begin_end?"end":"begin")
                + ":::");
    }
    public static void stampaLogProducer(int unique_id, int id_ordine, boolean begin_end){
            System.out.println(
                    "uuid:" + unique_id
                        + "-secs:" + System.nanoTime()
                        + "-id:" + id_ordine
                        + "-thread:producer"
                        + "-state:" + (begin_end?"end":"begin")
                        + ":::");
    }

    // Metodo per stampare un messaggio insieme allo stato attuale della coda
    public static void stampa(String mex,Iterable struttura){
        System.out.println(mex+"|| "+stampa_coda(struttura));
    }

    // Metodo per stampare lo stato attuale della coda
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
