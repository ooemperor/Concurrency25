package task2;

public class LockConsensus implements Consensus {
    Object decision = null;


    public synchronized Object decide(Object v) {
        if (decision == null)
            decision = v;
        return decision;
    }
}