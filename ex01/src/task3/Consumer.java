package task3;

/**
 * Class for the Consumer used in Exercise 3
 * @author Michael Kaiser
 * @version 1.0
 */
public class Consumer implements Runnable{

    private final Buffer buffer;
    private int id;

    /**
     * Constructor of the Consumer
     * @param buffer the buffer to read from
     * @param id the id of the consumer
     */
    public Consumer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void run(){
        this.consume();
    }

    public void consume(){
        System.out.println("Consumer " + this.id + " started");
        while (true){
            // queue is not empty
            // add your id and then push
            if (!this.buffer.isEmpty() && this.buffer.consumerLock() == -1){
                this.buffer.setConsumerId(this.id);
                if (this.buffer.consumerLock() == this.id){
                    String product = this.buffer.pull();
                    System.out.println(product);
                }
                this.buffer.removeConsumer();
            }
            else{
                continue;
            }
        }
    }
}
