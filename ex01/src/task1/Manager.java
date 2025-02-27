/**
 * Manager Class for the main execution of the task 1
 * @author Michael Kaiser
 * @version 1.0
 */

package task1;


import java.sql.Time;
import java.util.ArrayList;
import java.time.*;

/**
 * Manager class used for the execution of the tasks
 */
public class Manager {
    private int threadCount;
    private final ICounter counter;
    private final int lowerLimit;
    private final int upperLimit;
    private ArrayList<Thread> threads;

    /**
     * Constructor of the Manager class
     * @param threadCount the amounts of threads to run with
     * @param lowerLimit the lower starting point for the Prime search
     * @param upperLimit the upper limit (ending point) for the Prime search
     */
    public Manager(int threadCount, int lowerLimit, int upperLimit, ICounter counter){
        this.threadCount = threadCount;
        this.counter = counter;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.threads = new ArrayList<Thread>();
    }

    public int getThreadCount(){
        return this.threadCount;
    }

    /**
     * Run the task
     */
    public void run() throws InterruptedException {

        int interval = (this.upperLimit - this.lowerLimit) / this.threadCount;
        long start = System.currentTimeMillis() / 1000L;
        for (int i = 0; i<this.threadCount; i++){

            int lowLimit = this.lowerLimit + interval*i;
            int upLimit = this.lowerLimit + interval*(i+1);

            if (i == this.threadCount -1){
                upLimit = this.upperLimit;
            }


            Thread thread = new Thread(new PrimeRunnable(lowLimit, upLimit, this.counter));
            this.threads.add(thread);
            thread.start();
        }

        for (Thread thread : this.threads){
            thread.join();
        }

        long end = System.currentTimeMillis() / 1000L;


        long duration = end - start;

        System.out.println("Threads: " + this.threadCount + " Duration: " + duration + " sec Counter: " + this.counter.getValue());
    }
}
