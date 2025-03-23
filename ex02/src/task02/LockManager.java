package task02;

import java.util.LinkedList;
import java.util.Queue;

public class LockManager {
    int readLockCount;
    int writeLockCount;
    boolean hasWriterWaiting = false;

    Queue<QueueItem> queue = new LinkedList<QueueItem>();

    public LockManager() {
        this.readLockCount = 0;
        this.writeLockCount = 0;
    }

    private synchronized void changeReadLock(int amount) {
        assert amount == 1 || amount == -1;
        this.readLockCount += amount;
    }

    private synchronized void changeWriteLock(int amount) {
        assert amount == 1 || amount == -1;
        this.readLockCount += amount;
    }

    public synchronized boolean lockRead(Thread thread) {
        QueueItem queueItem = new QueueItem(thread, "read");
        queue.add(queueItem);
        this.changeReadLock(1);
        this.checkExecuteNext();
        return true;
    }

    public synchronized void unlockRead() {
        assert this.readLockCount > 0;
        this.changeReadLock(-1);
    }

    public synchronized boolean lockWrite(Thread thread) throws InterruptedException {
        QueueItem queueItem = new QueueItem(thread, "write");
        queue.add(queueItem);
        this.hasWriterWaiting = true;
        this.checkExecuteNext();
        return true;
    }

    public synchronized void unlockWrite() {
        assert this.writeLockCount > 0;
        this.changeWriteLock(-1);
    }

    private void checkExecuteNext() {
        QueueItem next = this.queue.peek();
        if (next != null) {
            String operation = next.getOperation();
            switch (operation) {
                case "write":
                    if (!this.hasWriterWaiting) {
                        break;
                    }
                    if (this.writeLockCount == 0 && this.readLockCount == 0) {
                        // you can do your stuff
                        this.queue.poll();
                        this.changeWriteLock(1);
                        this.hasWriterWaiting = false;
                    }
                    break;

                case "read":
                    if (this.writeLockCount == 0 && !this.hasWriterWaiting) {
                        // we go ahead and do our stuff
                        this.queue.poll();
                        this.changeReadLock(1);
                    }
                    break;
            }
            this.checkExecuteNext();
        }
    }
}
