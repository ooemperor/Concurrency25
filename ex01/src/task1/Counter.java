/**
 * Counter class for the incrementation in each thread
 * @author Michael Kaiser
 * @version 1.0
 */
package task1;

public class Counter implements ICounter {
    private int counter;

    public Counter(){
        this.counter = 0;
    }

    public void increment(){
        this.counter++;
    }

    public void decrement(){
        this.counter--;
    }

    public int getValue(){
        return this.counter;
    }
}
