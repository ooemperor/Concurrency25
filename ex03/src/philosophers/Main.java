package philosophers;

public class Main {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Fork[] forks = new Fork[5];

        for (int i = 0; i < 5; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < 5; i++) {
            Fork leftFork = forks[i];
            Fork rightFork;
            if (i == 4) {
                rightFork = forks[0];
            } else {
                rightFork = forks[i + 1];
            }

            if (i == 4) {
                // The last philosopher picks up the right fork first. This way we don't have a deadlock!
                philosophers[i] = new Philosopher(rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }

            Thread t = new Thread(philosophers[i], "Philosopher number " + (i + 1));
            t.start();
        }
    }
}