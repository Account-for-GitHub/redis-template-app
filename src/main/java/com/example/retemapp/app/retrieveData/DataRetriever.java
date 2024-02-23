package com.example.retemapp.app.retrieveData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataRetriever {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final RedisOperations<String, Message> redisOperations;

    public DataRetriever(RedisOperations<String, Message> redisOperations) {
        this.redisOperations = redisOperations;
    }

    public void retrieveAndShowData() {
        try {
            Objects.requireNonNull(redisOperations.opsForValue()
                    .getOperations()
                    .keys("*")).forEach(key ->
                    System.out.println(
                            redisOperations.opsForValue().get(key)
                    )
            );
        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
