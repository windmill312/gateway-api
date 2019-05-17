package com.github.windmill312.gateway.redis;

import com.github.windmill312.gateway.redis.model.Task;
import com.github.windmill312.gateway.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final MessagePublisher messagePublisher;
    private final AuthenticationService authService;

    @Autowired
    public RetryService(
            MessagePublisher messagePublisher,
            AuthenticationService authService) {
        this.messagePublisher = messagePublisher;
        this.authService = authService;
    }

    void retry(Task task, String nextTopicIfFailed) {
        try {
            UUID principalUid = UUID.fromString(task.getContent());

            authService.removePrincipal(principalUid);
        } catch (Exception e) {
            logger.warn("Failed to execute task: " + task.getId());

            messagePublisher.publish(nextTopicIfFailed, task);
        }
    }
}