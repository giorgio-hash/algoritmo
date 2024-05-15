package util;

import entities.OrdinePQ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Classe di utilità che permette di generare un file di report csv con i dati della simulazione.
 */
public final class OrderWaitingTimeLogger {

    /**
     * Costruttore privato per classe di utilità OrderWaitingTimeLogger.
     */
    private OrderWaitingTimeLogger() { }
    /**
     * cartella di output.
     */
    private static final String OUTPUT_FOLDER = "output";

    /**
     * cartella in cui mettere il file csv
     */
    private static final String CSV_SUBFOLDER = "csv";

    /**
     * nome e percorso del file csv di output.
     */
    private static final String CSV_FILE = OUTPUT_FOLDER + File.separator + CSV_SUBFOLDER + File.separator + "waiting_time.csv";

    /**
     * booleano che serve per sovrascrivere il file inizialmente e poi di aggiornarlo.
     */
    private static boolean append = false;

    /**
     * booleano che serve per stampare l'intestazione la prima volta e poi ignorarla.
     */
    private static boolean intestazione = false;

    /**
     * Numero progressivo iniziale.
     */
    private static int orderCount = 1;

    /**
     * Scrivi i parametri in considerazione in una nuova riga nel file di output.
     *
     * @param ordinePQ ordine estratto dalla cucina e quindi completato.
     */
    public static void logOrder(OrdinePQ ordinePQ) {

        Timestamp orderTimeStamp = ordinePQ.gettOrdinazione();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, append))) {

            append = true;
            // Scrivi l'intestazione solo se il file non esiste già
            if (!intestazione) {
                writer.write("Numero," +
                        "ID," +
                        "tempo_attesa," +
                        "ingr_principale," +
                        "t_prep," +
                        "urgenza," +
                        "numero_ordine_eff," +
                        "t_coda," +
                        "priorita_iniz," +
                        "priorita_fin"); // Intestazione della colonna per il timestamp
                writer.newLine();
                intestazione = true;
            }

            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            long difference = now.getTime() - orderTimeStamp.getTime(); // millis
            double seconds = (double) difference / 1000; // seconds
            writer.write(orderCount +
                    "," + ordinePQ.getId() +
                    "," + seconds +
                    "," + ordinePQ.getIngredientePrincipale() +
                    "," + ordinePQ.getTp().getSeconds() +
                    "," + ordinePQ.getUrgenzaCliente() +
                    "," + ordinePQ.getNumOrdineEffettuato() +
                    "," + ordinePQ.gettInCoda().toSeconds() +
                    "," + String.format("%.0f", ordinePQ.getPrioritaIniziale()*-100) +
                    "," + String.format("%.0f", ordinePQ.getValorePriorita()*-100));
            writer.newLine();
            orderCount += 1;
            System.out.println("File csv: Scrittura completata con successo");
        } catch (IOException e) {
            System.err.println("File csv: errore nella scrittura: " + e.getMessage());
        }
    }

}
