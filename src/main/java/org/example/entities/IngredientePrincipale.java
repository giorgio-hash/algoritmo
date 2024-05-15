package entities;

/**
 * Tipo di ingrediente principale con valore che indica il grado di riempimento della coda di postazione associata.
 */
public enum IngredientePrincipale{

    RISO(0.5),
    PASTA(0.5),
    CARNE(0.5),
    PESCE(0.5);

    /**
     * valore dell'ingrediente, indica il grado di riempimento della coda di postazione associata.
     */
    private double valore;

    /**
     * Costruttore privato per impostare il valore associato.
     */
    private IngredientePrincipale(double valore) {
        this.valore = valore;
    }

    /**
     * indica il grado di riempimento della coda di postazione associata.
     *
     * @return il valore associato il tipo enumerativo.
     */
    public double getValore() {
        return valore;
    }

    /**
     * imposta il valore associato, indica il grado di riempimento della coda di postazione associata.
     *
     * @param nuovoValore grado di riempimento della coda di postazione associata.
     */
    public void setValore(double nuovoValore) {
        this.valore = nuovoValore;
    }
}