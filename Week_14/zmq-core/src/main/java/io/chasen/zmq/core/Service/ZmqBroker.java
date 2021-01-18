package io.chasen.zmq.core.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ZmqBroker { // Broker+Connection

    public static final int CAPACITY = 100;

    private final Map<String, Zmq> zmqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name){
        zmqMap.putIfAbsent(name, new Zmq(name,CAPACITY));
    }

    public Zmq findKmq(String topic) {
        return this.zmqMap.get(topic);
    }

    public ZmqProducer createProducer() {
        return new ZmqProducer(this);
    }

    public ZmqConsumer createConsumer() {
        return new ZmqConsumer(this);
    }

}
