package threads;

import entities.GestioneCode;
import entities.IngredientePrincipale;
import entities.OrdinePQ;
import util.Printer;

import java.util.Optional;

/**
 * Thread che simula il comportamento di un cuoco.
 */
public class Cuoco implements Runnable{

    private IngredientePrincipale ingredientePrincipale;
    private GestioneCode gestioneCode;

    //log
    private int localIDGenerator = 0;
    private static int uuid_prefix_generator=0;
    private String uuid_prefix;


    public Cuoco(GestioneCode gestioneCode, IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.gestioneCode = gestioneCode;
        uuid_prefix = (uuid_prefix_generator++) + "cu";
    }

    /**
     * Controlla la coda di postazione associata.
     * Se trova un ordine resta in attesa per il tempo di preparazione dell'ordine.
     */
    @Override
    public void run() {

        while (true) {

            System.out.println("Cuoco " + ingredientePrincipale.toString() + ": osservo la coda di postazione: " +
                    gestioneCode.getCodaPostazione(ingredientePrincipale).toString());

            Optional<OrdinePQ> ordinePQ = gestioneCode.getOrder(ingredientePrincipale.toString());
            if (ordinePQ.isPresent()) {

                //stampa di log
                //unique id per riga log
                localIDGenerator++;
                Printer.stampaLog(
                        uuid_prefix+localIDGenerator,
                        Thread.currentThread().getName(),
                        0,
                        false);

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

                Printer.stampaLog(
                        uuid_prefix+localIDGenerator,
                        Thread.currentThread().getName(),
                        ordinePQ,
                        true);

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
