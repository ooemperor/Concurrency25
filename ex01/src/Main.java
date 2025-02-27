import task1.Counter;
import task1.CounterSynced;
import task1.ICounter;
import task1.Manager;

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
}
