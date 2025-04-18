package savage;

public class SavageBasic {
    /**
     * Class definition for the pot
     */
    static class Pot {
        private int capacity;
        private final int MAX_CAPACITY;
        // using volatile here since we want to have all the updates at all times
        private volatile boolean requested;

        public Pot(int capacity) {
            this.capacity = capacity;
            this.MAX_CAPACITY = capacity;
            this.requested = false;
        }

        public boolean isEmpty() {
            return this.capacity == 0;
        }

        public void consume() {
            this.capacity--;
        }

        public void fill() {
            this.capacity = this.MAX_CAPACITY;
            this.requested = false;
        }

        public void requestRefill() {
            this.requested = true;
        }

        public boolean isRefillRequested() {
            return this.requested;
        }
    }

    static class Cook implements Runnable {

        private Pot pot;

        Cook(Pot pot) {
            this.pot = pot;
        }

        @Override
        public void run() {
            // we do not do anything while the refill has not been requested --> no problem in sync
            while (!this.pot.isRefillRequested()) {
            }
            System.out.println("Pot must be refilled");
            this.pot.fill();
        }
    }

    static class Savage implements Runnable {

        private final Pot pot;
        private final int id;

        public Savage(Pot pot, int id) {
            this.pot = pot;
            this.id = id;
        }

        @Override
        public void run() {
            synchronized (pot) {
                // when the pot is empty and the refill has not been requested we request it.
                // since access to the pot is synchronized, we cannot request multiple and we wait.
                if (this.pot.isEmpty() && !this.pot.isRefillRequested()) {
                    System.out.println("Savage nr. " + this.id + " requested refill");
                    this.pot.requestRefill();
                }
                // still in critical phase, no other can enter
                while (this.pot.isEmpty()) ; // wait for the pot to become filled again
                System.out.println("Savage nr. " + this.id + " is now eating");
                this.pot.consume();
            }
        }
    }

    public static void main(String[] args) {
        int savageCount = 12;
        Thread[] savages = new Thread[savageCount];
        Pot pot = new Pot(10);

        for (int i = 0; i < savageCount; i++)
            savages[i] = new Thread(new Savage(pot, i));
        Thread cook = new Thread(new Cook(pot));

        cook.start();
        for (int i = 0; i < savageCount; i++) {
            savages[i].start();
        }
    }
}
