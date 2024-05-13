package threads;

import entities.OrdinePQ;
import util.GeneraOrdine;

import java.util.Random;

public class Cliente implements Runnable{

    private final Random random = new Random();
    private final Producer producer;

    public Cliente(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {

        while (true){

            // Aggiungi un ordine random nel sistema
            OrdinePQ ordinePQ = GeneraOrdine.genOrdineRandom();
            producer.addToQueue(ordinePQ);
            System.out.println("Cliente: aggiungo un ordine nel sistema: " + ordinePQ);

            // Aspetta un periodo randomico e ricomincia
            try {
                Thread.sleep(random.nextInt(10000,40000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
