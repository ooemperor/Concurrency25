package savage;

public class SavageFair {

    static volatile boolean stopCook = false;

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
            System.out.println("Pot has been filled");
        }

        public void requestRefill() {
            this.requested = true;
        }

        public boolean isRefillRequested() {
            return this.requested;
        }
    }

    static class GroupMeal {
        private volatile int mealsServed;
        private int savageCount;

        public GroupMeal(int savageCount) {
            this.mealsServed = 0;
            this.savageCount = savageCount;
        }

        public void eat() {
            this.mealsServed++;
        }

        public int getMealsServed() {
            return this.mealsServed;
        }

        public int getRound() {
            // used to see which serving round we are doing currently
            // since we are using int we will get the result floored
            return this.mealsServed / this.savageCount;
        }
    }

    static class Cook implements Runnable {

        private Pot pot;

        Cook(Pot pot) {
            this.pot = pot;
        }

        @Override
        public void run() {
            while (!stopCook) {
                while (!this.pot.isRefillRequested() && !stopCook) {
                    //System.out.println("Cook awaiting empty pot");
                }
                System.out.println("Pot must be refilled");
                this.pot.fill();
            }
        }
    }

    static class Savage implements Runnable {

        private final Pot pot;
        private final int id;
        private GroupMeal groupMeal;

        public Savage(Pot pot, int id, GroupMeal groupMeal) {
            this.pot = pot;
            this.id = id;
            this.groupMeal = groupMeal;
        }

        @Override
        public void run() {
            int myRoundCount = 0;
            while (myRoundCount < 3) {
                // wait for the servingRound to get bigger until i reach my turn again, where i try to enter
                while (groupMeal.getRound() < myRoundCount);
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
                    // update the pot and the round now.
                    this.pot.consume();
                    this.groupMeal.eat();
                    myRoundCount++;
                }
            }
        }
    }

    public static void main(String[] args) {
        int savageCount = 12;
        GroupMeal groupMeal = new GroupMeal(savageCount);
        Thread[] savages = new Thread[savageCount];
        Pot pot = new Pot(10);

        for (int i = 0; i < savageCount; i++)
            savages[i] = new Thread(new Savage(pot, i, groupMeal));
        Thread cook = new Thread(new Cook(pot));
        stopCook = false;
        cook.start();
        for (int i = 0; i < savageCount; i++) {
            savages[i].start();
        }

        for (int i = 0; i < savageCount; i++) {
            try {
                savages[i].join();
                System.out.println("Savage " + i + " has been completed");
            } catch (InterruptedException e) {}
        }
        stopCook = true;
        try {
            cook.join();
            System.out.println("Cook has been completed");
        } catch (InterruptedException e) {}
    }
}

