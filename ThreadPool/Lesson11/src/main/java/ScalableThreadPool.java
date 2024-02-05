import java.util.ArrayDeque;
import java.util.Queue;

/* В конструкторе задается минимальное и максимальное число потоков.
* КолиКоличество запущенных потоков может быть увеличено от
* минимального до максимального, если при добавлении нового задания
* в очередь нет свободного потока для исполнения этого задания.
* При отсутствии задания в очереди, количество потоков опять должно
* быть уменьшено до минимальногозначения */
public class ScalableThreadPool  implements ThreadPool{
    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private final int minThreadCount;
    private final int maxThreadCount;
    private volatile int currentThreadCount;

    public ScalableThreadPool(int minThreadCount, int maxThreadCount) {
        this.minThreadCount = minThreadCount;
        this.maxThreadCount = maxThreadCount;
    }

    @Override
    public void start() {
        for (int i = 0; i <  minThreadCount; i++) {
            new MyThread().start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            if(!tasks.isEmpty() && (currentThreadCount < maxThreadCount)) {
                new MyThread().start();
                currentThreadCount++;
                System.out.println("currentThreadCount = " + currentThreadCount);
            }
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
                            if(currentThreadCount > minThreadCount) {
                                currentThreadCount--;
                                System.out.println("currentThreadCount = " + currentThreadCount);
                                return;
                            }
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
