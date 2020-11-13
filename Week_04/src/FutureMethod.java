package src;

import java.util.concurrent.*;

public class FutureMethod {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Long> future = executor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return sum(24);
            }
        });

        try {
            long result = future.get(); //这是得到的返回值
            System.out.println(result);
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            executor.shutdown();
        }


    }

    private static long sum(int num) {
        return fibo(num);
    }

    private static long fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }
}
