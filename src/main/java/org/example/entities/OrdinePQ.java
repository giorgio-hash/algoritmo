package entities;

import java.sql.Timestamp;
import java.time.Duration;

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
     * parametro numero ordine effettuato dal cliente
     */
    private int numOrdineEffettuato;

    public OrdinePQ(int id, int idComanda, String idPiatto, Integer stato, Timestamp tOrdinazione, Boolean urgenzaCliente, IngredientePrincipale ingredientePrincipale , Double valorePriorita, Duration tp, int numOrdineEffettuato) {
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
    }

    public OrdinePQ(int id, Double valorePriorita) {
        this(id,0,null,null,null,null,null,valorePriorita,null,1);
    }

    public OrdinePQ(int id, Boolean urgenzaCliente, IngredientePrincipale ingredientePrincipale, Duration tp, int numOrdineEffettuato, Timestamp tOrdinazione) {
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

    public Double getValorePriorita() {
        return valorePriorita;
    }

    public void setValorePriorita(Double valorePriorita) {
        this.valorePriorita = valorePriorita;
    }

    public Timestamp gettOrdinazione() {
        return tOrdinazione;
    }

    public void settOrdinazione(Timestamp tOrdinazione) {
        this.tOrdinazione = tOrdinazione;
    }

    public Boolean getUrgenzaCliente() {
        return urgenzaCliente;
    }

    public void setUrgenzaCliente(Boolean urgenzaCliente) {
        this.urgenzaCliente = urgenzaCliente;
    }

    public IngredientePrincipale getIngredientePrincipale() {
        return ingredientePrincipale;
    }

    public void setIngredientePrincipale(IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
    }

    public Duration getTp() {
        return tp;
    }

    public void setTp(Duration tp) {
        this.tp = tp;
    }

    public int getNumOrdineEffettuato() {
        return numOrdineEffettuato;
    }

    public void setNumOrdineEffettuato(int numOrdineEffettuato) {
        this.numOrdineEffettuato = numOrdineEffettuato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
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