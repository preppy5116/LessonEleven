import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedThreadPoolTest {
    FixedThreadPool fixedThreadPool;

    //Количество потоков 4
    @Test
    void givenFixedThreadPool_whenExecuteTask_thenHasStaticThreads() throws InterruptedException {
        fixedThreadPool = new FixedThreadPool(4);


        for (int i = 0; i < 8; i++)
            fixedThreadPool.execute(new Work(i));

        fixedThreadPool.start();

        assertEquals(4, fixedThreadPool.getThreadCount());
        Thread.sleep(2000);
        assertEquals(4, fixedThreadPool.getThreadCount());

        fixedThreadPool.interrupt();
    }

    //Все задания выполнены
    @Test
    void givenFixedThreadPool_whenExecuteTask_thenAllTaskDone() throws InterruptedException {
        fixedThreadPool = new FixedThreadPool(4);


        for (int i = 0; i < 8; i++)
            fixedThreadPool.execute(new Work(i));
        fixedThreadPool.start();
        Thread.sleep(2000);


        assertEquals(0, fixedThreadPool.getTaskCount());

        fixedThreadPool.interrupt();
    }

}