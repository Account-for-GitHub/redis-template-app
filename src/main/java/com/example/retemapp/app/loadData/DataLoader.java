package com.example.retemapp.app.loadData;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {
    public DataLoader(RedisConnectionFactory connectionFactory,
                      RedisOperations<String, Message> redisOperations) {
        this.connectionFactory = connectionFactory;
        this.redisOperations = redisOperations;
    }
    private final RedisConnectionFactory connectionFactory;
    private final RedisOperations<String, Message> redisOperations;
    private final List<Message> messages = List.of(
            new Message("1", 1.1, "Message 1"),
            new Message("2", 2.2, "Message 2"),
            new Message("3", 3.3, "Message 3")
    );
    private Integer currentMessageNumber = 0;

    public void loadData() {
        Message message = messages.get(currentMessageNumber);

        connectionFactory.getConnection().serverCommands().flushDb();
        redisOperations.opsForValue().set(message.id, message);

        increaseCurrentMessageNumber();
    }

    private void increaseCurrentMessageNumber() {
        currentMessageNumber++;
        if (currentMessageNumber >= messages.size()) {
            currentMessageNumber = 0;
        }
    }
}
