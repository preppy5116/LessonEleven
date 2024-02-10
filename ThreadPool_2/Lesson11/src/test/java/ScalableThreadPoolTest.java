import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScalableThreadPoolTest {

    ScalableThreadPool scalableThreadPool;

    @Test
    void givenScalableThreadPool_whenExecuteTask_thenHasMaxThreads() throws InterruptedException {

        scalableThreadPool = new ScalableThreadPool(2, 4);

        for (int i = 0; i < 8; i++)
            scalableThreadPool.execute(new Work(i));

        scalableThreadPool.start();
        assertEquals(4, scalableThreadPool.getThreadCount());

        scalableThreadPool.interrupt();
    }

    @Test
    void givenScalableThreadPool_whenAllTaskDone_thenHasMinThreads() throws InterruptedException {

        scalableThreadPool = new ScalableThreadPool(2, 4);

        for (int i = 0; i < 8; i++)
            scalableThreadPool.execute(new Work(i));

        scalableThreadPool.start();
        Thread.sleep(3000);

        assertEquals(2, scalableThreadPool.getThreadCount());
        scalableThreadPool.interrupt();
    }

    @Test
    void givenScalableThreadPool_whenExecuteTask_thenAllTasksDone() throws InterruptedException {

        scalableThreadPool = new ScalableThreadPool(2, 4);


        for (int i = 0; i < 8; i++)
            scalableThreadPool.execute(new Work(i));

        scalableThreadPool.start();
        Thread.sleep(3000);

        assertEquals(0, scalableThreadPool.getTaskCount());
        scalableThreadPool.interrupt();
    }
}