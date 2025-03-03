package task1;

/**
 * PrimeRunnable Class for task 01
 *
 * @author Michael Kaiser
 * @version 1.0
 */
public class PrimeRunnable implements Runnable{

    private final int lowerLimit;
    private final int upperLimit;
    private final ICounter counter;

    /**
     * Constructor
     * @param lowerLimit the lower limit for the prime search
     * @param upperLimit the upper limit for the prime search
     * @param counter the counter to use for counting
     */
    public PrimeRunnable(int lowerLimit, int upperLimit, ICounter counter){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.counter = counter;
    }

    /**
     * Main run method
     */
    public void run(){
        for (int i = lowerLimit; i < upperLimit; i++){
            if (this.isPrime(i)){
                this.counter.increment();
            }
        }
    }

    /**
     * Checking if a number is prime or not
     * @param number The number to check if its prime or not
     * @return True if the number is prime, else false
     */
    private boolean isPrime(int number){
        // fetching the upperLimit of possible factors
        int root = (int) Math.sqrt(number);
        // checking all the number

        if (number == 1){
            return false;
        }
        for (int i = 2; i <= root; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }
}
