public class Main {
    public static void main(String[] args) {
        ThreadPool pool1 = new FixedThreadPool(2);
        ThreadPool pool2 = new ScalableThreadPool(1 , 3);
//
//        pool1.execute(()-> System.out.println(1));
//        pool1.execute(()-> System.out.println(2));
//        pool1.execute(()-> System.out.println(3));
//        pool1.execute(()-> System.out.println(4));
//        pool1.execute(()-> System.out.println(5));
//        pool1.execute(()-> System.out.println(6));
//        pool1.start();

        pool2.execute(()-> System.out.println(1));
        pool2.execute(()-> System.out.println(2));
        pool2.execute(()-> System.out.println(3));
        pool2.execute(()-> System.out.println(4));
        pool2.execute(()-> System.out.println(5));
        pool2.execute(()-> System.out.println(6));
        pool2.start();
    }
}
