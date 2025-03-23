/**
 * CounterSynced class for the incrementation in each thread
 * @author Michael Kaiser
 * @version 1.0
 */
package task01;

public class CounterSynced implements ICounter {
    private long counter;

    public CounterSynced(){
        this.counter = 0;
    }

    public synchronized long increment(){
        this.counter++;
        return this.counter;
    }

    public synchronized long decrement(){
        this.counter--;
        return this.counter;
    }

    public long getValue(){
        return this.counter;
    }

    public synchronized void setValue(long value){
        this.counter = value;
    }
}
