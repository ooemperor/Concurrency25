package task3;

/**
 * Class for the Producer used in Exercise 3
 * @author Michael Kaiser
 * @version 1.0
 */
public class Producer implements Runnable {

    private final Buffer buffer;
    private int id;

    /**
     * Constructor for the Producer
     * @param buffer the buffer to push into
     * @param id the id of the Producer
     */
    public Producer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id  = id;
    }

    public int getId(){
        return this.id;
    }

    public void run(){
        this.produce();
    }

    public void produce(){
        System.out.println("Producer " + this.id + " started");
        while (true){
            // queue is not full
            // add your id and then push
            if (!this.buffer.isFull() && this.buffer.producerLock() == -1){
                this.buffer.setProducerId(this.id);
                if (this.buffer.producerLock() == this.id){
                    this.buffer.push("Product " + this.id);
                }
                this.buffer.removeProducer();
            }
            else{
                continue;
            }
        }
    }
}
