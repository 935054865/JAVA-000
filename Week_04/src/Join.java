package src;

import java.util.concurrent.atomic.AtomicInteger;

public class Join {

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        AtomicInteger atomicInteger = new AtomicInteger();
        Thread thread = new Thread(()-> {
            atomicInteger.set(sum());
        });
        thread.start();
        thread.join();

        int result = atomicInteger.get();

        System.out.println("结果："+result);

        System.out.println("时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(45);
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

}
