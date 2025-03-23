import task01.*;

import java.util.ArrayList;


public class Main2 {

    public static void main(String[] args){
        System.out.println("Starting");

        runTask1();
    }

    public static void runTask1(){
        System.out.println("Running Task 1" );
        Counter counter = new Counter();
        CounterSynced counterSynced = new CounterSynced();
        CounterAtomic counterAtomic = new CounterAtomic();

        System.out.println("Running normal counter");
        subTask1(counter);

        System.out.println("Running atomic counter");
        subTask1(counterAtomic);

        System.out.println("Running synced counter");
        subTask1(counterSynced);

        System.out.println("Task 1 done");


    }

    public static void subTask1(ICounter counter){
        Integer[] threadCounts = new Integer[] {2, 4, 8, 16};


        for (int threadCount : threadCounts){
            counter.setValue(0);
            long start = System.currentTimeMillis();
            ArrayList<Thread> threads = new ArrayList<>();
            for (int i=0; i<threadCount; i++){
                Thread thread = new Thread(new Runner(i, counter, 10000000));
                threads.add(thread);
            }
            for (Thread thread : threads){
                thread.start();
            }
            for (Thread thread : threads){
                try {
                    thread.join();
                }
                catch (InterruptedException exception){
                    System.out.println("ERROR Thread has been interrupted");
                }
            }

            long end = System.currentTimeMillis();
            long duration = end - start;

            System.out.println("Threads: " + threadCount + " Duration: " + duration + " msec Counter: " + counter.getValue());
        }
    }
}
