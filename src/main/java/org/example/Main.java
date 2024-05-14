import buffer.Buffer;
import entities.GestioneCode;
import entities.IngredientePrincipale;
import entities.OrdinePQ;
import threads.*;
import util.GeneraOrdine;

import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

            Buffer buffer = Buffer.getInstance();
            GestioneCode gestioneCode = GestioneCode.getINSTANCE();

            Producer producer = new Producer(buffer);
            Consumer consumer = new Consumer(buffer, gestioneCode, producer);
            Checker checker = new Checker(buffer);

            Cuoco cuocoRiso = new Cuoco(gestioneCode, IngredientePrincipale.RISO);
            Cuoco cuocoPasta = new Cuoco(gestioneCode, IngredientePrincipale.PASTA);
            Cuoco cuocoCarne = new Cuoco(gestioneCode, IngredientePrincipale.CARNE);
            Cuoco cuocoPesce = new Cuoco(gestioneCode, IngredientePrincipale.PESCE);

            Cliente cliente = new Cliente(producer);

            Thread thread_producer = new Thread(producer);
            Thread thread_consumer = new Thread(consumer);
            Thread thread_checker = new Thread(checker);
            thread_producer.setName("producer");
            thread_consumer.setName("consumer");
            thread_checker.setName("checker");

            Thread thread_cuocoRiso = new Thread(cuocoRiso);
            Thread thread_cuocoPasta = new Thread(cuocoPasta);
            Thread thread_cuocoCarne = new Thread(cuocoCarne);
            Thread thread_cuocoPesce = new Thread(cuocoPesce);
            Thread thread_cliente = new Thread(cliente);
            thread_cuocoRiso.setName("cuoco_riso");
            thread_cuocoPasta.setName("cuoco_pasta");
            thread_cuocoCarne.setName("cuoco_carne");
            thread_cuocoPesce.setName("cuoco_pesce");
            thread_cliente.setName("cliente");

            ArrayList<OrdinePQ> list = new ArrayList<OrdinePQ>();

//            // Creazione ordini
//            for (int i=0; i<10; i++){
//                    list.add(GeneraOrdine.genOrdine(i));
//            }

            //stampa di log
            System.out.println("begin:" + System.nanoTime());

            // Creazione ordini
            for (int i=0; i<10; i++){
                    list.add(GeneraOrdine.genOrdineRandom());
            }

            thread_producer.start();
            thread_checker.start();
            thread_consumer.start();

            thread_cuocoRiso.start();
            thread_cuocoPasta.start();
            thread_cuocoCarne.start();
            thread_cuocoPesce.start();
            thread_cliente.start();

            // lista di ordini iniziali
            for(int j=0; j < 2; j++) {
                    for (int i = 0; i < 10; i++) {
                            producer.addToQueue(list.get(i));
                            try {
                                    Thread.sleep(500);
                            } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                            }
                    }
            }

    }
}