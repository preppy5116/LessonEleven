import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final int poolSize;
    private final CustomThread[] threads;
    private final Queue<Runnable> tasks;
    private boolean isActive = false;

    public FixedThreadPool(int threadCount) {
        this.poolSize = threadCount;
        tasks = new LinkedList<>();
        threads = new CustomThread[threadCount];
    }

    @Override
    public void start() {
        isActive = true;
        for (int i = 0; i < poolSize; i++) {
            threads[i] = new CustomThread();
            threads[i].start();
            System.out.println(threads[i].getName() + " started..");
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notify();
        }
    }

    @Override
    public void interrupt() {
        isActive = false;
        for (CustomThread thread : threads) {
            thread.interrupt();
        }
    }
    public int getThreadCount() {
        return threads.length;
    }
    public int getTaskCount() {
        return tasks.size();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            Runnable r;
            while(isActive) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            System.out.println(this.getName() + " is waiting...");
                            tasks.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    r = tasks.remove();
                }
                r.run();
            }
        }
    }
}

