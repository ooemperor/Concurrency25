package philosophers;

public class Philosopher implements Runnable {

    // The forks on either side of this Philosopher
    private Fork leftFork;
    private Fork rightFork;

    /**
     * Constructor for the philosopher
     * @param leftFork fork for the left hand
     * @param rightFork fork for the right hand.
     */
    public Philosopher(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void doSometing(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": " + action);
        Thread.sleep(((int) (100 * Math.random())));
    }

    @Override
    public void run() {
        try {
            while (true) {
                doSometing("Is ready");
                synchronized (leftFork) { // because it is synchronized we do not have the problem that we have conflict in eating.
                    doSometing("Takes left fork");
                    synchronized (rightFork) {
                        doSometing("Takes right fork \n" + "eats");
                        doSometing("Puts down right fork");
                    }
                    doSometing("Puts down left fork");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}


