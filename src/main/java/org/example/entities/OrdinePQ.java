package entities;


import java.sql.Timestamp;
import java.time.Duration;

/**
 * Entità OrdinePQ: ordine con priorità e i parametri per calcolarla.
 */
public class OrdinePQ {
    /**
     * Identificatore dell'ordine
     */
    private int id;

    /**
     * Identificatore della comanda di cui l'ordine fa parte
     */
    private int idComanda;

    /**
     * Identificatore del piatto ordinato dal cliente
     */
    private String idPiatto;

    /**
     * Stato dell'ordine
     * 0: Ordine preso in carico
     * 1: Ordine in coda di preparazione
     * 2: Ordine in preparazione
     * 3: Ordine preparato
     */
    private Integer stato;

    /**
     * Istante temporale in cui viene effettuata l'ordinazione
     * pattern : "yyyy-MM-dd HH:mm:ss.SSS"
     */
    private Timestamp tOrdinazione;

    /**
     * Attributo urgenza del cliente
     * False : espresso non urgenza
     * True : espresso urgenza
     */
    private Boolean urgenzaCliente;

    /**
     * Parametro ingrediente principale specifico del piatto dell'ordine
     */
    private IngredientePrincipale ingredientePrincipale;

    /**
     * Valore della priorita dell'ordine
     */
    private Double valorePriorita;

    /**
     * parametro tempo di preparazione del piatto
     */
    private Duration tp;

    /**
     * parametro numero ordine effettuato dal cliente.
     */
    private int numOrdineEffettuato;

    /**
     * tempo passato in coda.
     */
    private Duration tInCoda;

    /**
     * prioritò al momento dell'inserimento in coda calcolata non considerando il tempo in coda.
     */
    private double prioritaIniziale;

    /**
     * Costruttore completo
     *
     * @param id codice identificativo
     * @param idComanda codice identificativo della comanda
     * @param idPiatto codice identificativo del piatto
     * @param stato stato dell'ordine
     * @param tOrdinazione istante di tempo in cui è stata piazzata l'ordinazione
     * @param urgenzaCliente urgenza del cliente
     * @param ingredientePrincipale tipo enumerativo di ingrediente principale
     * @param valorePriorita valore della priorità dell'ordine
     * @param tp tempo di preparazione
     * @param numOrdineEffettuato numero progressivo ordine effettuato dallo stesso cliente
     */
    public OrdinePQ(int id,
                    int idComanda,
                    String idPiatto,
                    Integer stato,
                    Timestamp tOrdinazione,
                    Boolean urgenzaCliente,
                    IngredientePrincipale ingredientePrincipale,
                    Double valorePriorita,
                    Duration tp,
                    int numOrdineEffettuato) {
        this.id = id;
        this.idComanda = idComanda;
        this.idPiatto = idPiatto;
        this.stato = stato;
        this.tOrdinazione = tOrdinazione;
        this.urgenzaCliente = urgenzaCliente;
        this.ingredientePrincipale = ingredientePrincipale;
        this.valorePriorita = valorePriorita;
        this.tp = tp;
        this.numOrdineEffettuato = numOrdineEffettuato;
        this.tInCoda = Duration.ZERO;
        this.prioritaIniziale = 0.0;
    }

    /**
     * Costruttore di OrdinePQ con solo i campi relativi ai parametri dell'algoritmo,
     * rimanda al costruttore principale.
     *
     * @param id codice identificativo
     * @param urgenzaCliente urgenza del cliente
     * @param ingredientePrincipale tipo enumerativo di ingrediente principale
     * @param tp tempo di preparazione
     * @param numOrdineEffettuato numero progressivo ordine effettuato dallo stesso cliente
     * @param tOrdinazione istante di tempo in cui è stata piazzata l'ordinazione
     */
    public OrdinePQ(int id,
                    Boolean urgenzaCliente,
                    IngredientePrincipale ingredientePrincipale,
                    Duration tp,
                    int numOrdineEffettuato,
                    Timestamp tOrdinazione) {
        this(
                id,
                0,
                "null",
                null,
                tOrdinazione,
                urgenzaCliente,
                ingredientePrincipale,
                null,
                tp,
                numOrdineEffettuato);
    }

    public int getId(){return id;}

    public Double getValorePriorita() {
        return valorePriorita;
    }

    public void setValorePriorita(Double valorePriorita) {
        this.valorePriorita = valorePriorita;
    }

    public Timestamp gettOrdinazione() {
        return tOrdinazione;
    }

    public Boolean getUrgenzaCliente() {
        return urgenzaCliente;
    }

    public IngredientePrincipale getIngredientePrincipale() {
        return ingredientePrincipale;
    }

    public Duration getTp() {
        return tp;
    }

    public int getNumOrdineEffettuato() {
        return numOrdineEffettuato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }

    public Duration gettInCoda() {
        return tInCoda;
    }

    public void settInCoda(Duration tInCoda) {
        this.tInCoda = tInCoda;
    }

    public void setPrioritaIniziale(Double prioritaIniziale){
        this.prioritaIniziale = prioritaIniziale;
    }

    public double getPrioritaIniziale() {
        return prioritaIniziale;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OrdinePQ{ id=").append(id);
        stringBuilder.append(", ").append(ingredientePrincipale.toString());
        if(valorePriorita!=null){
            stringBuilder.append(", valorePriorità=").append(String.format("%.3f", valorePriorita*-1));
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
