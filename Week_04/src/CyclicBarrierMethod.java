package src;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierMethod {

    private volatile Integer result = null;
    CyclicBarrier cyclicBarrier;

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法


        final CyclicBarrierMethod method = new CyclicBarrierMethod();
        CyclicBarrier barrier = new CyclicBarrier(1, ()-> {
            int result = 0; //这是得到的返回值
            try {
                result = method.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(result);
        });
        method.setBarrier(barrier);

        Thread thread = new Thread(() -> {
            try {
                method.sum(23);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        // 然后退出main线程
    }


    private void setBarrier(CyclicBarrier barrier) {
        this.cyclicBarrier = barrier;
    }

    private void sum(int num) throws BrokenBarrierException, InterruptedException {
        result = fibo(num);
        cyclicBarrier.await();
    }

    private int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    private int getResult() throws InterruptedException {
        return result;
    }
}
