package io.chasen.zmq.core.Service;


public final class Zmq {

    public Zmq(String topic, Integer capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new ZmqMessage[capacity];
    }

    private String topic;
    private Integer capacity;
    private ZmqMessage[] queue;
    private int producerOffset = 0;
    private int consumerOffset = 0;

    /**
     * 单线程版
     * @param message
     * @return
     */
    public boolean sendMessage(ZmqMessage message) {
        //小于总容量直接放入数组队列
        if (producerOffset <= capacity) {
            this.queue[producerOffset] = message;
            producerOffset ++;
            return true;
        }
        //满了归0
        //小于消费偏移量 直接放入队列 否则队列处于饱和状态返回false
        if (producerOffset > consumerOffset) {
            producerOffset = 0;
            this.queue[producerOffset] = message;
            producerOffset ++;
            return true;
        }
        return false;
    }

    public ZmqMessage poll() {
        ZmqMessage zmqMessage = this.queue[consumerOffset];
        this.queue[consumerOffset] = null;
        return zmqMessage;
    }
}
