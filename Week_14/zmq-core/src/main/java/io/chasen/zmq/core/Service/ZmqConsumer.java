package io.chasen.zmq.core.Service;

public class ZmqConsumer<T> {

    private final ZmqBroker broker;

    private Zmq kmq;

    public ZmqConsumer(ZmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.kmq = this.broker.findKmq(topic);
        if (null == kmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

    public ZmqMessage poll() {
        return kmq.poll();
    }

}
