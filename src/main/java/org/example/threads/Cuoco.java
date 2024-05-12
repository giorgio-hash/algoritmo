package threads;

import entities.GestioneCode;
import entities.IngredientePrincipale;
import entities.OrdinePQ;

import java.util.Optional;

public class Cuoco implements Runnable{

    private IngredientePrincipale ingredientePrincipale;
    private GestioneCode gestioneCode;

    public Cuoco(GestioneCode gestioneCode, IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.gestioneCode = gestioneCode;
    }

    @Override
    public void run() {

        while (true) {
            Optional<OrdinePQ> ordinePQ = gestioneCode.getOrder(ingredientePrincipale.toString());
            if (ordinePQ.isPresent()) {
                System.out.println("Cuoco " + ingredientePrincipale.toString() + ": preparando l'ordine: " + ordinePQ);
                try {
                    Thread.sleep(ordinePQ.get().getTp()); // cucina l'ordine (aspetta un tempo tp di preparazione)

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    gestioneCode.postNotifica(ordinePQ.get().getIngredientePrincipale(), ordinePQ.get());
                    System.out.println("Cuoco " + ingredientePrincipale.toString() + ": ordine completato: " + ordinePQ);
                } catch (InterruptedException e) {
                    System.out.println("Cuoco " + ingredientePrincipale.toString() + ": errore per : " + ordinePQ);
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Cuoco " + ingredientePrincipale.toString() + ": in attesa di ordini...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
