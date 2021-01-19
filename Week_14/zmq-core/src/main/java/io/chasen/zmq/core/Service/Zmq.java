package io.chasen.zmq.core.Service;


import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Zmq {

    public Zmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new ZmqMessage[capacity];
    }

    private String topic;
    private int capacity;
    private ZmqMessage[] queue;
    private int producerOffset = 0;
    private int consumerOffset = 0;
    private int producerDepth = 0;
    private int consumerDepth = capacity;
    /**
     * 锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 基于lock的 队列没有满 的条件。
     * 用于：队列满时 阻塞（await()）,不满时唤醒（signal()）
     */
    private Condition notFull = lock.newCondition();

    /**
     * 基于lock的 队列没有空 的条件。
     * 用于：队列空时 阻塞（await()）,不空时唤醒（signal()）
     */
    private Condition notEmpty = lock.newCondition();


    /**
     * 单线程版
     * @param message
     * @return
     */

    public boolean sendMessage(ZmqMessage message) {
        lock.lock();
        try {
            while (producerDepth >= capacity) {
                notFull.await();
            }
            if (producerOffset >= capacity) {
                producerOffset = 0;
                this.queue[producerOffset] = message;
            } else {
                this.queue[producerOffset] = message;
            }
            producerOffset++;
            producerDepth++;
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return false;
    }


    @SneakyThrows
    public ZmqMessage poll() {
        lock.lock();
        try {
            while (producerDepth <= 0) {
                notFull.await();
            }
            if (consumerOffset >= capacity) {
                consumerOffset = 0;
            }
            ZmqMessage zmqMessage = this.queue[consumerOffset];
            this.queue[consumerOffset] = null;
            consumerOffset ++;
            producerDepth --;
            notFull.signal();
            return zmqMessage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
