import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
/**
        BufferIF buffer = new GestioneBuffer();

        OrdinePQ ordine1 = new OrdinePQ(1524,-0.5);
        OrdinePQ ordine2 = new OrdinePQ(3645,-0.7);
        OrdinePQ ordine3 = new OrdinePQ(2154,-0.2);
        OrdinePQ ordine4 = new OrdinePQ(1000,-0.5);
        OrdinePQ ordine5 = new OrdinePQ(5445,-0.9);
        OrdinePQ ordine6 = new OrdinePQ(1054,-0.1);

        buffer.insertInBuffer(ordine1);
        buffer.insertInBuffer(ordine2);
        buffer.insertInBuffer(ordine3);

        System.out.println(buffer.getMinPQ());
        System.out.println(buffer.getMinPQ());
        System.out.println();
        buffer.insertInBuffer(ordine4);
        buffer.insertInBuffer(ordine5);
        buffer.insertInBuffer(ordine6);

        System.out.println(buffer.getMinPQ());
        System.out.println(buffer.getMinPQ());
        System.out.println(buffer.getMinPQ());
        System.out.println(buffer.getMinPQ());
        System.out.println();

//        Dizionario dizionario = new Dizionario();

//        OrdinePQ ordine1 = new OrdinePQ(1524,0.5);
//        OrdinePQ ordine2 = new OrdinePQ(3645,0.7);
//        OrdinePQ ordine3 = new OrdinePQ(2154,0.2);

//        // Aggiungo alcune parole e significati al dizionario
//        int id1 = dizionario.aggiungiOrdine(ordine1);
//        int id2 = dizionario.aggiungiOrdine(ordine2);
//        int id3 = dizionario.aggiungiOrdine(ordine3);
//
//        // Cerco il significato di alcune parole
//        System.out.println("Il significato di 1 è: " + dizionario.cercaOrdine(id1));
//        System.out.println("Il significato di 2 è: " + dizionario.cercaOrdine(id2));
//        System.out.println("Il significato di 3 è: " + dizionario.cercaOrdine(id3));
//
//        // Rimuovo una parola dal dizionario
//        dizionario.rimuoviOrdine(id1);
//
//        // Aggiungo un'altra parola
//        dizionario.aggiungiOrdine(ordine1);
//
//        IndexMinPQ<Double> pq = new IndexMinPQ<>(10);
//        pq.insert(id1,dizionario.cercaOrdine(id1).getValorePriorita());
//        pq.insert(id2,dizionario.cercaOrdine(id2).getValorePriorita());
//        pq.insert(id3,dizionario.cercaOrdine(id3).getValorePriorita());
//
//        // delete and print each key
//        while (!pq.isEmpty()) {
//            int i = pq.delMin();
//            System.out.println("estrazione " + i + " " + dizionario.cercaOrdine(i));
//            dizionario.rimuoviOrdine(i);
//        }
//        System.out.println();
//
//        id1 = dizionario.aggiungiOrdine(ordine1);
//        id2 = dizionario.aggiungiOrdine(ordine2);
//        id3 = dizionario.aggiungiOrdine(ordine3);
//
//        pq.insert(id1,dizionario.cercaOrdine(id1).getValorePriorita());
//        pq.insert(id2,dizionario.cercaOrdine(id2).getValorePriorita());
//        pq.insert(id3,dizionario.cercaOrdine(id3).getValorePriorita());
//
//        int i = pq.delMin();
//        System.out.println("inserimento " + i + " " + dizionario.cercaOrdine(i));
//        System.out.println();
//
//        OrdinePQ ordine4 = new OrdinePQ(1000,0.5);
//        OrdinePQ ordine5 = new OrdinePQ(5445,0.9);
//        OrdinePQ ordine6 = new OrdinePQ(1054,0.1);
//        int id4 = dizionario.aggiungiOrdine(ordine4);
//        int id5 = dizionario.aggiungiOrdine(ordine5);
//        int id6 = dizionario.aggiungiOrdine(ordine6);
//        pq.insert(id4,dizionario.cercaOrdine(id4).getValorePriorita());
//        pq.insert(id5,dizionario.cercaOrdine(id5).getValorePriorita());
//        pq.insert(id6,dizionario.cercaOrdine(id6).getValorePriorita());
//        // delete and print each key
//        while (!pq.isEmpty()) {
//            i = pq.delMin();
//            System.out.println(i + " " + dizionario.cercaOrdine(i));
//        }
//        System.out.println();
**/

            Buffer buffer = new Buffer();
            Producer p = new Producer(buffer);
            Consumer c = new Consumer(buffer);
            Checker ch = new Checker(buffer);

            Thread thread_p = new Thread(p);
            Thread thread_c = new Thread(c);
            Thread thread_ch = new Thread(ch);


            OrdinePQ ordine1 = new OrdinePQ(1524,-0.50);
            OrdinePQ ordine2 = new OrdinePQ(3645,-0.70);
            OrdinePQ ordine3 = new OrdinePQ(2154,-0.20);
            OrdinePQ ordine4 = new OrdinePQ(1000,-0.50);
            OrdinePQ ordine5 = new OrdinePQ(5445,-0.95);
            OrdinePQ ordine6 = new OrdinePQ(1054,-0.10);
            OrdinePQ ordine7 = new OrdinePQ(1212,-0.77);
            OrdinePQ ordine8 = new OrdinePQ(8888,-0.15);
            OrdinePQ ordine9 = new OrdinePQ(2222,-0.25);

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



//            for(int i = 0; i < 9; i++){
//
//                    buffer.insertInBuffer(list.get(i));
//
//                    try {
//                            Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                    }
//            }

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