package task3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for the Buffer used in Exercise 3
 * @author Michael Kaiser
 * @version 1.0
 */
public class Buffer {
    private int size;
    private Queue<String> queue;
    private int consumerId;
    private int producerId;

    /**
     * Constructor for the buffer size
     * @param size the size of the buffer
     */
    public Buffer(int size){
        this.size = size;
        this.queue = new LinkedList<String>() {};
        this.producerId = -1;
        this.consumerId = -1;
    }

    private synchronized String manipulateQueue(String operation, String value){
        if (operation == "push"){
            this.queue.offer(value);
            return "";
        }
        else if (operation == "pull"){
            return this.queue.poll();
        }
        return "";
    }


    /**
     * Push method used by producers
     * For the sake of not having unnecessary code we are using strings
     * @param product The product to push
     */
    public synchronized void push(String product){
        if (this.queue.size() <= this.size){
            this.manipulateQueue("push", product);
            //this.queue.offer(product);
        }
    }

    /**
     * Pulls an element out of the Buffer
     * @return the element at the head of the queue
     */
    public synchronized String pull(){
        return this.manipulateQueue("pull", "");
        //return this.queue.poll();
    }

    /**
     * Check if the queue is empty
     * @return True if the queue is empty
     */
    public boolean isEmpty(){
        return this.queue.size() == 0;
    }

    /**
     * Check if the queue is full
     * @return True if it is full
     */
    public boolean isFull(){
        return this.queue.size() == this.size;
    }

    public synchronized void setConsumerId(int id){
        if (this.consumerId == -1){
            this.consumerId = id;
        }
    }

    public synchronized void removeConsumer(){
        this.consumerId = -1;
    }

    public synchronized void setProducerId(int id){
        if (this.producerId == -1){
            this.producerId = id;
        }
    }

    public synchronized void removeProducer(){
        this.producerId = -1;
    }

    public int producerLock(){
        return this.producerId;
    }

    public int consumerLock(){
        return this.consumerId;
    }

}
