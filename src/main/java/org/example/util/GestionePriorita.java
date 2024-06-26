package util;

import entities.IngredientePrincipale;
import entities.OrdinePQ;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

/**
 * Classe di utilità che permette di generare il valore di priorità
 * per un dato ordine.
 */
public final class GestionePriorita {

    /**
     * Costruttore privato di classe di utilità GestionePriorità.
     */
    private GestionePriorita() { }
    // VALORE DEI PESI
    /**
     * peso relativo ad x1.
     */
    private static final float p1 = 0.25f;

    /**
     * peso relativo ad x2.
     */
    private static final float p2 = 0.15f;

    /**
     * peso relativo ad x3.
     */
    private static final float p3 = 0.20f;

    /**
     * peso relativo ad x4.
     */
    private static final float p4 = 0.15f;

    /**
     * peso relativo ad x5.
     */
    private static final float p5 = 0.25f;

    /**
     * tempo di preparazione massimo.
     */
    private static final Duration tpmax = Duration.ofMinutes(1).plusSeconds(0);

    /**
     * tempo di preparazione minimo.
     */
    private static final Duration tmin = Duration.ofMinutes(0).plusSeconds(10);

    /**
     * tempo massimo in attesa.
     */
    private static final Duration tempo_max_in_attesa = Duration.ofMinutes(3);

    /**
     * Soglia massima di ordini ordinabili da un cliente.
     */
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
        double x1 = calcolaIngredientePrincipale(ordinePQ.getIngredientePrincipale());
        double x2 = calcolaTempoDiPreparazione(ordinePQ.getTp());
        double x3 = CalcolaUrgenzaDelCliente(ordinePQ.getUrgenzaCliente());
        double x4 = CalcolaNumeroOrdineEffettuato(ordinePQ.getNumOrdineEffettuato());
        double x5 = CalcolaTempoDiAttesa(ordinePQ.gettOrdinazione());

        double y = x1 * p1 + x2 * p2 + x3* p3 + x4 * p4 + x5 * p5;
        System.out.println("Gestione Priorita': y = " + y);
        if(y<0 || y >1){
            throw new Exception("valore priorita non valido");
        }
        ordinePQ.setValorePriorita(y*-1);
        if(ordinePQ.getPrioritaIniziale() == 0.0){
            ordinePQ.setPrioritaIniziale(setPrioritaIniziale(x5, y));
        }
        return y*(-1);
    }

    /**
     * Funzione che calcola il parametro x1 riferito all’ingrediente principale
     *
     * @param ingredientePrincipale ingrediente principale dell'ordine
     * @return il parametro x1
     */
    private static double calcolaIngredientePrincipale(final IngredientePrincipale ingredientePrincipale) {
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
    private static double calcolaTempoDiPreparazione(final Duration tp) {
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

    /**
     * Calcola la priorità iniziale senza considerare il contributo del parametro x5 poichè nullo all'entrata in coda.
     *
     * @param x5 parametro x5.
     * @param y valore di priorità.
     * @return priorità iniziale.
     * @throws Exception eccezione se priorità è negativa o superiore a 1.
     */
    private static double setPrioritaIniziale(double x5, double y) throws Exception {
        double y1 = (y - x5 * p5)/(1-p5);
        System.out.println("Gestione Priorita iniziale': y' = " + y1);
        if(y1<0 || y1 >1){
            throw new Exception("valore priorita iniziale non valido");
        }
        return y1*-1;
    }


}
