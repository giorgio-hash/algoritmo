package util;

import entities.IngredientePrincipale;
import entities.OrdinePQ;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class GestionePriorita {

    // VALORE DEI PESI
    private static final float p1 = 0.25f;
    private static final float p2 = 0.15f;
    private static final float p3 = 0.20f;
    private static final float p4 = 0.15f;
    private static final float p5 = 0.25f;

    private static final Duration tpmax = Duration.ofMinutes(10).plusSeconds(30); // tempo di preparazione massimo
    private static final Duration tmin= Duration.ofMinutes(1).plusSeconds(10); // tempo di preparazione minimo
    private static final Duration tempo_max_in_attesa = Duration.ofMinutes(1); // tempo massimo in attesa
    private static final int sogliaMax = 5;


    /**
     * Calcola la priorità dell'ordine e assegnala
     * somma pesata dei parametri:
     * y = p1x1 + p2x2 + p3x3 + p4x4 + p5 ∗ x5
     *
     * @param ordinePQ ordine
     * @return la priorità calcolata
     * @throws Exception
     */
    public static double setPriorita(OrdinePQ ordinePQ) throws Exception {
        double x1 = CalcolaIngredientePrincipale(ordinePQ.getIngredientePrincipale());
        double x2 = CalcolaTempoDiPreparazione(ordinePQ.getTp());
        double x3 = CalcolaUrgenzaDelCliente(ordinePQ.getUrgenzaCliente());
        double x4 = CalcolaNumeroOrdineEffettuato(ordinePQ.getNumOrdineEffettuato());
        double x5 = CalcolaTempoDiAttesa(ordinePQ.gettOrdinazione());

        double y = x1 * p1 + x2 * p2 + x3* p3 + x4 * p4 + x5 * p5;
        System.out.println("Gestione Priorita': y = " + y);
        if(y<0 || y >1){
            throw new Exception("valore priorita non valido");
        }
        ordinePQ.setValorePriorita(y*-1);
        return y*(-1);
    }

    /**
     * Funzione che calcola il parametro x1 riferito all’ingrediente principale
     *
     * @param ingredientePrincipale ingrediente principale dell'ordine
     * @return il parametro x1
     */
    private static double CalcolaIngredientePrincipale(IngredientePrincipale ingredientePrincipale) {
        // TODO: codaPostazione(ingredientePrincipale).getGradoRiempimento();
        double x1 = ingredientePrincipale.getValore();
        System.out.println("Gestione Priorita': x1 = " + (1 - x1));
        return 1 - x1;
    }

    /**
     * Funzione che calcola il parametro x2 riferito al tempo di preparazione
     *
     * @param tp oggetto Duration che contiene il tempo di preparazione dellìordine
     * @return il parametro x2
     */
    private static double CalcolaTempoDiPreparazione(Duration tp) {
        double x2;
        if(tp.toSeconds()<tpmax.toSeconds()){
            x2 = (double) (tp.toSeconds() - tmin.toSeconds()) / (tpmax.toSeconds() - tmin.toSeconds());
        } else {
            x2 = 1.0;
        }
        System.out.println("Gestione Priorita': x2 = " + x2);
        return x2;
    }

    /**
     * Funzione che calcola il parametro x3 riferito all’urgenza del cliente
     *
     * @param urgenzaCliente booleano 1 il cliente ha espresso urgenza, 0 altrimenti.
     * @return parametro x3.
     */
    private static double CalcolaUrgenzaDelCliente(Boolean urgenzaCliente) {
        int x3;
        if(!urgenzaCliente){
            x3 = 0;
        }
        else {
            x3 = 1;
        }
        System.out.println("Gestione Priorita': x3 = " + x3);
        return x3;
    }

    /**
     * Funzione che calcola il parametro x4 riferito al numero ordine effettuato.
     *
     * @param numOrdineEffettuato numero di ordine effettuato dal cliente.
     * @return parametro x4.
     */
    private static double CalcolaNumeroOrdineEffettuato(int numOrdineEffettuato) {
        double x4;
        if(numOrdineEffettuato < sogliaMax){
            x4 = (double) (numOrdineEffettuato - 1 ) / (sogliaMax - 1);
        }
        else{
            x4 = 1;
        }
        System.out.println("Gestione Priorita': x4 = " + (1 - x4));
        return 1 - x4;
    }

    /**
     * Funzione che calcola il parametro x5 riferito al tempo di attesa del cliente.
     *
     * @param tOrdinazione TimeStamp dell'istante di ordinazione dell'ordine.
     * @return parametro x5.
     */
    private static double CalcolaTempoDiAttesa(Timestamp tOrdinazione) {
        Timestamp tAttuale = Timestamp.from(Instant.now());
        long t = tAttuale.getTime() - tOrdinazione.getTime(); // in millisecondi
        double x5;
        if(t==0){
            x5 = 0;
        }
        else if(t < tempo_max_in_attesa.toMillis()){
            x5 = (double) t / (tempo_max_in_attesa.toMillis());
        }
        else{
            x5 = 1;
        }
        System.out.println("Gestione Priorita': x5 = " + x5);
        return x5;
    }


}
