package task01;

public class Runner implements Runnable {
    private int threadId;
    private ICounter counter;
    private int n;

    public Runner(int threadId, ICounter counter, int n) {
        this.threadId = threadId;
        this.counter = counter;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            if (this.threadId % 2 == 0) {
                this.counter.increment();
            } else {
                this.counter.decrement();
            }
        }
    }
}
