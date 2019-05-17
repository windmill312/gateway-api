package com.github.windmill312.gateway.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.windmill312.gateway.redis.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessagePublisher.class);

    private final Jedis jedis;
    private final ObjectWriter objectWriter;

    @Autowired
    public RedisMessagePublisher(Jedis jedis) {
        this.jedis = jedis;
        objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public void publish(String topic, Task task) {
        try {
            jedis.lpush(topic, objectWriter.writeValueAsString(task));
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
        }
    }
}

