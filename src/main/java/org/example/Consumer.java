public class Consumer implements Runnable{

    Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        String out = null;
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                out = (String) buffer.extract();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("estratto: "+out);
        }
    }
}
