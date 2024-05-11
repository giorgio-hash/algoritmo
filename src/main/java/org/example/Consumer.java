public class Consumer implements Runnable{

    BufferIF buffer;

    public Consumer(BufferIF buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        Object out = null;
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                out = buffer.getMinPQ();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("estratto: "+out);
        }
    }
}
