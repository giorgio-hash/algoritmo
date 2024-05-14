package threads;

import entities.OrdinePQ;
import util.GeneraOrdine;
import util.Printer;

import java.util.Random;

public class Cliente implements Runnable{

    private final Random random = new Random();
    private final Producer producer;

    //log
    private int localIDGenerator = 0;
    private final String uuid_prefix = "cl";
    public Cliente(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {

        while (true){

            //stampa di log
            //unique id per riga log
            localIDGenerator++;
            Printer.stampaLog(
                    uuid_prefix+localIDGenerator,
                    Thread.currentThread().getName(),
                    0,
                    false);

            // Aggiungi un ordine random nel sistema
            OrdinePQ ordinePQ = GeneraOrdine.genOrdineRandom();
            producer.addToQueue(ordinePQ);
            System.out.println("Cliente: aggiungo un ordine nel sistema: " + ordinePQ);

            // Aspetta un periodo randomico e ricomincia
            try {
                Thread.sleep(random.nextInt(10000,100000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Printer.stampaLog(
                    uuid_prefix+localIDGenerator,
                    Thread.currentThread().getName(),
                    ordinePQ.getId(),
                    true);

        }
    }

}
