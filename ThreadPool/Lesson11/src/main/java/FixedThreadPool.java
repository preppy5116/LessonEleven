import java.util.ArrayDeque;
import java.util.Queue;

/*Количество потоков задается в конструкторе и не меняется*/
public class FixedThreadPool implements ThreadPool {
    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private final int threadCount;

    public FixedThreadPool(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public void start() {
        for (int i = 0; i <  threadCount; i++) {
            new MyThread().start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notifyAll();
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            Runnable r;
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    r = tasks.poll();
                }
                try {
                    r.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
