package threads;

import entities.FrontSignalPort;
import entities.GestioneCode;
import entities.IngredientePrincipale;
import entities.OrdinePQ;
import util.Printer;

import java.util.Optional;

/**
 * Thread che simula il comportamento di un cuoco.
 */
public class Cuoco implements Runnable{

    /**
     * Ingrediente principale sul quale è specializzato il cuoco
     */
    private IngredientePrincipale ingredientePrincipale;

    /**
     * Buffer da quale prende le ordinazioni
     */
    private final FrontSignalPort buffer;

    //log
    /**
     * id locale
     */
    private int localIDGenerator = 0;
    /**
     * predisso uuid generatore
     */
    private static int uuid_prefix_generator=0;
    /**
     * prefisso uuid
     */
    private String uuid_prefix;


    /**
     * Costruttore del thread cuoco
     *
     * @param buffer coda dalla quale il cuoco prende le ordinazioni
     * @param ingredientePrincipale ingrediente principale sul quale è specializzato il cuoco
     */
    public Cuoco(GestioneCode buffer, IngredientePrincipale ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.buffer = buffer;
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
                    buffer.getCodaPostazione(ingredientePrincipale).toString());

            Optional<OrdinePQ> ordinePQ = buffer.getOrder(ingredientePrincipale.toString());
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
                    buffer.postNotifica(ordinePQ.get().getIngredientePrincipale(), ordinePQ.get());
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
