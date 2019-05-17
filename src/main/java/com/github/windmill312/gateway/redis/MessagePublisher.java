package com.github.windmill312.gateway.redis;

import com.github.windmill312.gateway.redis.model.Task;

public interface MessagePublisher {

    void publish(String topic, Task task);
}
