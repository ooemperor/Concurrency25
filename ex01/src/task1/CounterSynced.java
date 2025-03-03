/**
 * CounterSynced class for the incrementation in each thread
 * @author Michael Kaiser
 * @version 1.0
 */
package task1;

public class CounterSynced implements ICounter{
    private int counter;

    public CounterSynced(){
        this.counter = 0;
    }

    public synchronized void increment(){
        this.counter++;
    }

    public void decrement(){
        this.counter--;
    }

    public int getValue(){
        return this.counter;
    }
}
