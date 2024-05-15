package threads;

import entities.OrdinePQ;
import util.GeneraOrdine;
import util.Printer;

import java.util.Random;

/**
 * Thread che simula il comportamento di un Cliente.
 */
public class Cliente implements Runnable{

    /**
     * istanza della classe random.
     */
    private final Random random = new Random();

    /**
     * Thread del producer.
     */
    private final Producer producer;

    //log
    /**
     * generatore locale di id
     */
    private int localIDGenerator = 0;

    /**
     * generatore del prefisso uuid
     */
    private static int uuid_prefix_generator=0;

    /**
     * prefisso uuid
     */
    private final String uuid_prefix;

    /**
     * Costruttore del thread cliente
     *
     * @param producer thread del producer
     */
    public Cliente(Producer producer) {
        this.producer = producer;
        uuid_prefix = (uuid_prefix_generator++) + "cl";
    }

    /**
     * Resta in attesa per un periodo randomico
     * piazza un ordine inviandolo alla coda del Producer.
     */
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

            Printer.stampaLog(
                    uuid_prefix+localIDGenerator,
                    Thread.currentThread().getName(),
                    ordinePQ,
                    true);
            // Aspetta un periodo randomico e ricomincia
            try {
                Thread.sleep(random.nextInt(10000,60000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
