package task01;
import java.util.concurrent.atomic.AtomicLong;

public class CounterAtomic implements ICounter {
	AtomicLong n;

	public CounterAtomic() {
		n = new AtomicLong(0);
	}

	@Override
	public long increment() {
		return n.getAndIncrement();
	}

	@Override
	public long decrement() {
		return n.getAndDecrement();
	}

	@Override
	public long getValue() {
		return n.get();
	}

	public void setValue(long value) {
		n.set(value);
	}
}
