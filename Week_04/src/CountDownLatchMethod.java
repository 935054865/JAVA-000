package src;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMethod {

    private volatile Integer result = null;




    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatchMethod method = new CountDownLatchMethod();
        Thread thread = new Thread(() -> {
            method.sum(32, latch);
        });
        thread.start();

        int result = method.getResult(latch);

        System.out.println(result);
    }

    private void sum(int num, CountDownLatch latch) {
        result = fibo(num);
        latch.countDown();
    }

    private int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    private int getResult(CountDownLatch latch) throws InterruptedException {
        latch.await();
        return result;
    }
}
