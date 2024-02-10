public interface ThreadPool {
    void start();
    void execute(Runnable task);

    void interrupt();
}
