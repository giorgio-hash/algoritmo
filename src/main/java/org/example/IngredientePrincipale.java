public enum IngredientePrincipale{

    RISO(0.5),
    PASTA(0.5),
    CARNE(0.5),
    PESCE(0.5);

    private double valore;

    // Costruttore privato per impostare il valore associato
    private IngredientePrincipale(double valore) {
        this.valore = valore;
    }

    // Metodo per ottenere il valore associato
    public double getValore() {
        return valore;
    }

    // Metodo per impostare il valore associato
    public void setValore(double nuovoValore) {
        this.valore = nuovoValore;
    }
}