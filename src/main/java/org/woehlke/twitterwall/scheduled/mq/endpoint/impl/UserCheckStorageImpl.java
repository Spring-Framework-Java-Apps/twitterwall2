package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.UserCheckStorage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

@Component("mqUserCheckStorage")
public class UserCheckStorageImpl implements UserCheckStorage {

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    @Autowired
    public UserCheckStorageImpl(UserService userService, TwitterApiService twitterApiService) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
    }

    @Override
    public Message<UserMessage> checkIfUserIsInStorage(Message<UserMessage> incomingMessage) {
        UserMessage receivedMessage = incomingMessage.getPayload();
        long userIdTwitter = receivedMessage.getTwitterProfileId();
        boolean isInStorage = userService.isByIdTwitter(userIdTwitter);
        if(isInStorage){
            UserMessage outMessage = new UserMessage(receivedMessage.getTaskMessage(),receivedMessage.getTwitterProfileId(),isInStorage);
            Message<UserMessage> mqMessageOut = MessageBuilder.withPayload(outMessage)
                    .copyHeaders(incomingMessage.getHeaders())
                    .setHeader("checked_storage",Boolean.TRUE)
                    .build();
            return mqMessageOut;
        } else {
            boolean ignoreTransformation = false;
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(userIdTwitter);
            UserMessage outMessage = new UserMessage(receivedMessage.getTaskMessage(),receivedMessage.getTwitterProfileId(),ignoreTransformation,twitterProfile);
            Message<UserMessage> mqMessageOut = MessageBuilder.withPayload(outMessage)
                    .copyHeaders(incomingMessage.getHeaders())
                    .setHeader("checked_storage",Boolean.TRUE)
                    .build();
            return mqMessageOut;
        }
    }
}
