package util;

import entities.OrdinePQ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderWaitingTimeLogger {
    private static final String OUTPUT_FOLDER = "output";
    private static final String CSV_SUBFOLDER = "csv";
    private static final String CSV_FILE = OUTPUT_FOLDER + File.separator + CSV_SUBFOLDER + File.separator + "waiting_time.csv";
    private static boolean append = false;
    private static boolean intestazione = false;
    private static int orderCount = 1; // Numero progressivo iniziale

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
                        "priorita"); // Intestazione della colonna per il timestamp
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
                    "," + String.format("%.0f", ordinePQ.getValorePriorita()*-100) + "%");
            writer.newLine();
            orderCount += 1;
            System.out.println("File csv: Scrittura completata con successo");
        } catch (IOException e) {
            System.err.println("File csv: errore nella scrittura: " + e.getMessage());
        }
    }

}