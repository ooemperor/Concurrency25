import task1.*;
import task3.Buffer;
import task3.Consumer;
import task3.Producer;
import java.util.ArrayList;


/**
 * Main class file for the exercise 01
 * @author Michael Kaiser
 * @version 1.0
 */
public class Main {
    public static void main(String[] args){
        System.out.println("Starting");

        runTask1();
        runTask2();
        runTask3();
    }

    public static void runTask1() {
        runTaskGeneric(1);
    }

    public static void runTask2() {
        runTaskGeneric(2);
    }

    public static void runTaskGeneric(int task){
        System.out.println("Running Task " + task);
        Integer[] threadCounts = new Integer[] {1, 2, 4, 8, 16};

        for (int threadCount : threadCounts){
            ICounter counter;

            // run the task 01 for the specified thread count
            if (task == 2){
                counter = new CounterSynced();
            } else {
                counter = new Counter();
            }


            Manager manager = new Manager(threadCount, 10000000, 100000000, counter);
            try{
                manager.run();
            }
            catch (InterruptedException exception){
                System.out.println("ERROR Thread has been interrupted");
            }
        }
    }

    public static void runTask3(){
        System.out.println("Running Task 3");
        int amount = 4;
        int bufferSize = 10;
        ArrayList<Thread> producers = new ArrayList<Thread>();
        ArrayList<Thread> consumers = new ArrayList<Thread>();


        Buffer buffer = new Buffer(bufferSize);
        for (int i = 0; i < amount; i++){
            Thread threadProducer = new Thread(new Producer(buffer, i));
            producers.add(threadProducer);

            Thread threadConsumer = new Thread(new Consumer(buffer, i));
            consumers.add(threadConsumer);

            threadProducer.start();
            threadConsumer.start();
        }
    }
}
