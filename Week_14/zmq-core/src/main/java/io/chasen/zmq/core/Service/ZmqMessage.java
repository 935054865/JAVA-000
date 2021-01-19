package io.chasen.zmq.core.Service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class ZmqMessage<T> {
    private HashMap<String,Object> headers;

    private T body;
}
