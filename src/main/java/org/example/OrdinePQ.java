import java.sql.Timestamp;

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
     * 0 : espresso non urgenza
     * 1 : espresso urgenza
     * 2 : default
     */
    private Integer urgenzaCliente;

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
    private Timestamp tDiPreparazione;

    /**
     * parametro numero ordine effettuato dal cliente
     */
    private int numOrdineEffettuato;

    public OrdinePQ(int id, int idComanda, String idPiatto, Integer stato, Timestamp tOrdinazione, Integer urgenzaCliente, IngredientePrincipale ingredientePrincipale, Double valorePriorita, Timestamp tDiPreparazione, int numOrdineEffettuato) {
        this.id = id;
        this.idComanda = idComanda;
        this.idPiatto = idPiatto;
        this.stato = stato;
        this.tOrdinazione = tOrdinazione;
        this.urgenzaCliente = urgenzaCliente;
        this.ingredientePrincipale = ingredientePrincipale;
        this.valorePriorita = valorePriorita;
        this.tDiPreparazione = tDiPreparazione;
        this.numOrdineEffettuato = numOrdineEffettuato;
    }

    public OrdinePQ(int id, Double valorePriorita) {
        this(id,0,null,null,null,null,null,valorePriorita,null,1);
    }

    public Double getValorePriorita() {
        return valorePriorita;
    }

    public void setValorePriorita(Double valorePriorita) {
        this.valorePriorita = valorePriorita;
    }

    @Override
    public String toString() {
        return "OrdinePQ{" +
                " id=" + id +
                ", valorePriorita=" + valorePriorita*-1 +
                '}';
    }
}