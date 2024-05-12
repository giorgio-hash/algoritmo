import buffer.Buffer;
import entities.CodaPostazione;
import entities.GestioneCode;
import entities.IngredientePrincipale;
import entities.OrdinePQ;
import threads.Checker;
import threads.Consumer;
import threads.Cuoco;
import threads.Producer;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
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


            // ORDINI CON PRIORITA' GIA' ASSEGNATA
//            entities.OrdinePQ ordine1 = new entities.OrdinePQ(1524,-0.50);
//            entities.OrdinePQ ordine2 = new entities.OrdinePQ(3645,-0.70);
//            entities.OrdinePQ ordine3 = new entities.OrdinePQ(2154,-0.20);
//            entities.OrdinePQ ordine4 = new entities.OrdinePQ(1000,-0.50);
//            entities.OrdinePQ ordine5 = new entities.OrdinePQ(5445,-0.95);
//            entities.OrdinePQ ordine6 = new entities.OrdinePQ(1054,-0.10);
//            entities.OrdinePQ ordine7 = new entities.OrdinePQ(1212,-0.77);
//            entities.OrdinePQ ordine8 = new entities.OrdinePQ(8888,-0.15);
//            entities.OrdinePQ ordine9 = new entities.OrdinePQ(2222,-0.25);

            OrdinePQ ordine1 = new OrdinePQ(1524,
                    false,
                    IngredientePrincipale.CARNE,
                    Duration.ofMinutes(0).plusSeconds(35),
                    1,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine2 = new OrdinePQ(3645,
                    true,
                    IngredientePrincipale.PESCE,
                    Duration.ofMinutes(0).plusSeconds(25),
                    2,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine3 = new OrdinePQ(2154,
                    false,
                    IngredientePrincipale.CARNE,
                    Duration.ofMinutes(0).plusSeconds(20),
                    3,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine4 = new OrdinePQ(1000,
                    false,
                    IngredientePrincipale.RISO,
                    Duration.ofMinutes(0).plusSeconds(30),
                    1,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine5 = new OrdinePQ(5445,
                    false,
                    IngredientePrincipale.PASTA,
                    Duration.ofMinutes(0).plusSeconds(10),
                    4,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine6 = new OrdinePQ(1054,
                    true,
                    IngredientePrincipale.RISO,
                    Duration.ofMinutes(0).plusSeconds(15),
                    1,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine7 = new OrdinePQ(1212,
                    false,
                    IngredientePrincipale.PESCE,
                    Duration.ofMinutes(0).plusSeconds(45),
                    3,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine8 = new OrdinePQ(8888,
                    true,
                    IngredientePrincipale.PESCE,
                    Duration.ofMinutes(1).plusSeconds(0),
                    6,
                    Timestamp.valueOf(LocalDateTime.now()));

            OrdinePQ ordine9 = new OrdinePQ(2222,
                    false,
                    IngredientePrincipale.PESCE,
                    Duration.ofMinutes(0).plusSeconds(55),
                    5,
                    Timestamp.valueOf(LocalDateTime.now()));

            CodaPostazione codaPasta = new CodaPostazione(IngredientePrincipale.PASTA);
            CodaPostazione codaRiso = new CodaPostazione(IngredientePrincipale.RISO);
            CodaPostazione codaCarne = new CodaPostazione(IngredientePrincipale.CARNE);
            CodaPostazione codaPesce = new CodaPostazione(IngredientePrincipale.PESCE);

            ArrayList<OrdinePQ> list = new ArrayList<OrdinePQ>();

            list.add(ordine1);
            list.add(ordine2);
            list.add(ordine3);
            list.add(ordine4);
            list.add(ordine5);
            list.add(ordine6);
            list.add(ordine7);
            list.add(ordine8);
            list.add(ordine9);

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