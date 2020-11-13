package src;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockMethod {

    private volatile Integer result = null;
    private Lock lock = new ReentrantLock();
    private Condition lockConditions = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        final LockMethod method = new LockMethod();
        Thread thread = new Thread(() -> {
            method.sum(20);
        });
        thread.start();

        int result = method.getResult(); //这是得到的返回值


        System.out.println("结果："+result);

        System.out.println("时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private void sum(int num) {
        lock.lock();
        try {
            result = fibo(num);
            lockConditions.signal();
        } finally {
            lock.unlock();
        }
    }

    private int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    private int getResult() throws InterruptedException {
        lock.lock();
        try {
            while (result == null) {
                lockConditions.await();
            }
        } finally {
            lock.unlock();
        }
        return result;
    }
}
