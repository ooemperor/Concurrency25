package task1;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class v3 {

    public static Queue<String> queue = new ConcurrentLinkedQueue<String>();

    public static class Consumer implements Runnable {
        int amount;

        public Consumer(int amount) {
            this.amount = amount;
        }

        @Override
        public void run(){
            System.out.println("Consumer starting");
            while (amount > 0) {
                try {
                    queue.remove();
                    this.amount--;
                } catch(NoSuchElementException e) {
                }
            }
            System.out.println("Consumer done");
        }
    }

    public static class Producer implements Runnable {
        int amount;

        public Producer(int amount) {
            this.amount = amount;
        }

        @Override
        public void run(){
            System.out.println("Producer starting");
            while (amount-- > 0) {
                String val = "test";
                queue.add(val);
            }
            System.out.println("Producer done");
        }
    }

    public static void main(String[] args) {
        int N = 10000000;
        int T = 2;

        System.out.println("Starting with version 1");
        long startTime = System.currentTimeMillis();

        Thread[] producers = new Thread[T];
        Thread[] consumers = new Thread[T];


        for (int i = 0; i < T; i++) {
            producers[i] = new Thread(new Producer(N));
            consumers[i] = new Thread(new Consumer(N));
        }


        // Start threads
        for (int i = 0; i < T; i++) {
            producers[i].start();
            consumers[i].start();
        }
        // Wait for threads completion
        for (int i = 0; i < T; i++) {
            try {
                producers[i].join();
                consumers[i].join();
            } catch (InterruptedException e) {
                // do nothing, we should not need to handle any error
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Duration: " + duration + " ms");

    }
}
