import buffer.Buffer;
import entities.GestioneCode;
import entities.IngredientePrincipale;
import entities.OrdinePQ;
import threads.Checker;
import threads.Consumer;
import threads.Cuoco;
import threads.Producer;
import util.GeneraOrdine;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

            Buffer buffer = Buffer.getInstance();
            GestioneCode gestioneCode = GestioneCode.getINSTANCE();

            Producer p = new Producer(buffer);
            Consumer c = new Consumer(buffer, gestioneCode);
            Checker ch = new Checker(buffer);

            Cuoco cuocoRiso = new Cuoco(gestioneCode, IngredientePrincipale.RISO);
            Cuoco cuocoPasta = new Cuoco(gestioneCode, IngredientePrincipale.PASTA);
            Cuoco cuocoCarne = new Cuoco(gestioneCode, IngredientePrincipale.CARNE);
            Cuoco cuocoPesce = new Cuoco(gestioneCode, IngredientePrincipale.PESCE);

            Thread thread_p = new Thread(p);
            Thread thread_c = new Thread(c);
            Thread thread_ch = new Thread(ch);

            Thread thread_cuocoRiso = new Thread(cuocoRiso);
            Thread thread_cuocoPasta = new Thread(cuocoPasta);
            Thread thread_cuocoCarne = new Thread(cuocoCarne);
            Thread thread_cuocoPesce = new Thread(cuocoPesce);

            ArrayList<OrdinePQ> list = new ArrayList<OrdinePQ>();

            // Creazione ordini
            for (int i=0; i<10; i++){
                    list.add(GeneraOrdine.genOrdine(i));
            }

            // Creazione ordini
            for (int i=0; i<10; i++){
                    list.add(GeneraOrdine.genOrdineRandom());
            }

            thread_p.start();
            thread_ch.start();
            thread_c.start();

            thread_cuocoRiso.start();
            thread_cuocoPasta.start();
            thread_cuocoCarne.start();
            thread_cuocoPesce.start();

            for(int i = 0; i < 9; i++){

                    p.addToQueue(list.get(i));

                    try {
                            Thread.sleep(500);
                    } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                    }

            }

            // ripeti
            for(int i = 0; i < 9; i++){

                    p.addToQueue(list.get(i));

                    try {
                            Thread.sleep(500);
                    } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                    }

            }

    }
}