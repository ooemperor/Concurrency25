package task02;

import java.util.Objects;

public class QueueItem {
    private final Thread thread;
    private final String operation;

    public QueueItem(Thread thread, String operation) {
        assert Objects.equals(operation, "read") || Objects.equals(operation, "write");
        this.thread = thread;
        this.operation = operation;
    }

    public String getOperation() {
        return this.operation;
    }

    public Thread getThread() {
        return this.thread;
    }
}
