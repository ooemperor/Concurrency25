package task2;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeConsensus implements Consensus {

    /*
        We use the AtomicReference which should not use locks.
     */
    private final AtomicReference<Object> decision = new AtomicReference<>(null);

    public synchronized Object decide(Object v) {
        // first incoming thread sets the decision.
        decision.compareAndSet(null, v);
        return decision.get();
    }
}
