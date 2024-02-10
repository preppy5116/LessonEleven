public class Main {
    public static void main(String[] args) {
        fixedTask();
//        scalableTask();


    }

    public static void fixedTask() {
        FixedThreadPool fixedThreadPool = new FixedThreadPool(4);
        for (int i = 0; i < 8; i++)
            fixedThreadPool.execute(new Work(i));

        fixedThreadPool.start();

        //Подождем немного и запустим еще 2 задачи
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fixedThreadPool.execute(new Work(8));
        fixedThreadPool.execute(new Work(9));

        fixedThreadPool.execute(new Work(10));
        fixedThreadPool.execute(new Work(11));

        fixedThreadPool.interrupt();
    }

    public static void scalableTask() {
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(4, 6);

        scalableThreadPool.start();

        for (int i = 0; i < 10; i++)
            scalableThreadPool.execute(new Work(i));

        //Подождем немного и запустим еще 10 задач
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.err.println("Add another 10 tasks");

        scalableThreadPool.execute(new Work(10));
        scalableThreadPool.execute(new Work(11));
        scalableThreadPool.execute(new Work(12));
        scalableThreadPool.execute(new Work(13));
        scalableThreadPool.execute(new Work(14));
        scalableThreadPool.execute(new Work(15));
        scalableThreadPool.execute(new Work(16));
        scalableThreadPool.execute(new Work(17));
        scalableThreadPool.execute(new Work(18));
        scalableThreadPool.execute(new Work(19));

    }
}


