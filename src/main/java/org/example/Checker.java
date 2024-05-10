public class Checker implements Runnable{

    Buffer buffer;

    public Checker(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while(true){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                buffer.control();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
