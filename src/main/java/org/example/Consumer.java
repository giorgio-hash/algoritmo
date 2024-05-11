import java.util.Optional;

public class Consumer implements Runnable{

    ConsumerIF buffer;

    public Consumer(ConsumerIF buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        Optional<OrdinePQ> out = Optional.empty();
        while(true){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                out = buffer.getMinPQ();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            out.ifPresent(ordinePQ -> System.out.println("estratto: " + ordinePQ));
        }
    }
}
