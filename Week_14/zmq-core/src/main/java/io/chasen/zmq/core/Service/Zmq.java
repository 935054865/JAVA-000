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
    private final int capacity;
    private final ZmqMessage[] queue;
    private int producerOffset = 0;
    private int consumerOffset = 0;
    private int producerDepth = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition producerFull = lock.newCondition();
    private final Condition consumerEmpty = lock.newCondition();



    public boolean sendMessage(ZmqMessage message) {
        lock.lock();
        try {
            while (producerDepth >= capacity) {
                producerFull.await();
            }
            if (producerOffset >= capacity) {
                producerOffset = 0;
                this.queue[producerOffset] = message;
            } else {
                this.queue[producerOffset] = message;
            }
            producerOffset++;
            producerDepth++;
            consumerEmpty.signal();
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
                consumerEmpty.await();
            }
            if (consumerOffset >= capacity) {
                consumerOffset = 0;
            }
            ZmqMessage zmqMessage = this.queue[consumerOffset];
            this.queue[consumerOffset] = null;
            consumerOffset ++;
            producerDepth --;
            producerFull.signal();
            return zmqMessage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
