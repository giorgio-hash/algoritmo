import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Buffer buffer = new Buffer(10);
        Producer p = new Producer(buffer);
        Consumer c = new Consumer(buffer);
        Checker ch = new Checker(buffer);

        Thread thread_p = new Thread(p);
        Thread thread_c = new Thread(c);
        Thread thread_ch = new Thread(ch);

        thread_p.start();
        thread_ch.start();
        thread_c.start();

        Random rand = new Random();

        while(true){

            int randomNum = rand.nextInt(10)+1;

            p.addToQueue(Integer.toString(randomNum));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}