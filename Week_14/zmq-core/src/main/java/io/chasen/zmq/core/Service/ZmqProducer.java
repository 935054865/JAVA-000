package io.chasen.zmq.core.Service;

public class ZmqProducer {

    private ZmqBroker broker;

    public ZmqProducer(ZmqBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, ZmqMessage message) {
        Zmq zmq = this.broker.findKmq(topic);
        if (null == zmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        return zmq.sendMessage(message);
    }
}
