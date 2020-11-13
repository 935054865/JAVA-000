package src;

public class NotifyAwait {

    private volatile Integer value = null;

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        final NotifyAwait aSynchronized = new NotifyAwait();
        Thread thread = new Thread(() -> {
            aSynchronized.sum(10);
        });
        thread.start();

        int result = aSynchronized.getValue();

        System.out.println("结果："+result);

        System.out.println("时间："+ (System.currentTimeMillis()-start) + " ms");
    }


    synchronized private void sum(int num) {
        value = fibo(num);
        notifyAll();
    }

    private int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    synchronized private int getValue() throws InterruptedException {
        while (value == null) {
            wait();
        }
        return value;
    }

}
