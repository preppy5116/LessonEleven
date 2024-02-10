import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private final int minThreadCount;
    private final int maxThreadCount;
    private final ArrayList<CustomThread> threads;
    private final Queue<Runnable> tasks;

    private boolean isActive = false;

    public ScalableThreadPool(int min, int max) {
        this.minThreadCount = min;
        this.maxThreadCount = max;

        threads = new ArrayList<>(max);
        tasks = new LinkedList<>();

        for (int i = 0; i < minThreadCount; i++) {
            threads.add(new CustomThread());
        }
    }

    @Override
    public void start() {
        for (CustomThread thread : threads)
            thread.start();
        isActive = true;
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notify();
            checkThreadCount();
        }
    }

    @Override
    public void interrupt() {
        isActive = false;
        threads.forEach(Thread::interrupt);
    }


    private void checkThreadCount() {
        if (tasks.size() > threads.size() && threads.size() < maxThreadCount) {
            threads.add(new CustomThread());
            System.out.println("add extend thread " + threads.get(threads.size() - 1).getName());

            if (isActive)
                threads.get(threads.size() - 1).start();
        }
        if (tasks.size() < threads.size() && threads.size() > minThreadCount) {
            System.out.println("remove extend thread " + threads.get(threads.size() - 1).getName());
            threads.get(threads.size() - 1).interrupt();
            threads.remove(threads.size() - 1);
        }
    }

    public int getThreadCount() {
        return threads.size();
    }

    public int getTaskCount() {
        return tasks.size();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            Runnable r;
            while (!Thread.interrupted()) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            checkThreadCount();
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
