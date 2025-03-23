/**
 * CounterSynced class for the incrementation in each thread
 * @author Michael Kaiser
 * @version 1.0
 */
package task01;

public class Counter implements ICounter {
    private long counter;

    public Counter(){
        this.counter = 0;
    }

    public long increment(){
        this.counter++;
        return this.counter;
    }

    public long decrement(){
        this.counter--;
        return this.counter;
    }

    public long getValue(){
        return this.counter;
    }

    public void setValue(long value){
        this.counter = value;
    }
}