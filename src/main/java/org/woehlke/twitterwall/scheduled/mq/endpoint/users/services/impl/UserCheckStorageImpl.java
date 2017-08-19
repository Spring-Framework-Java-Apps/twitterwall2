package org.woehlke.twitterwall.scheduled.mq.endpoint.users.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.users.services.UserCheckStorage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.UserMessageBuilder;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

@Component("mqUserCheckStorage")
public class UserCheckStorageImpl implements UserCheckStorage {

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final UserMessageBuilder userMessageBuilder;

    @Autowired
    public UserCheckStorageImpl(UserService userService, TwitterApiService twitterApiService, UserMessageBuilder userMessageBuilder) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.userMessageBuilder = userMessageBuilder;
    }

    @Override
    public Message<UserMessage> checkIfUserIsInStorage(Message<UserMessage> incomingMessage) {
        UserMessage receivedMessage = incomingMessage.getPayload();
        long userIdTwitter = receivedMessage.getTwitterProfileId();
        boolean isInStorage = userService.isByIdTwitter(userIdTwitter);
        if(isInStorage){
            Message<UserMessage> mqMessageOut = userMessageBuilder.buildUserMessage(incomingMessage,isInStorage);
            return mqMessageOut;
        } else {
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(userIdTwitter);
            if(twitterProfile == null){
                Message<UserMessage> mqMessageOut = userMessageBuilder.buildUserMessage(incomingMessage,isInStorage);
                return mqMessageOut;
            } else {
                boolean ignoreTransformation = false;
                Message<UserMessage> mqMessageOut = userMessageBuilder.buildUserMessage(incomingMessage, twitterProfile, ignoreTransformation);
                return mqMessageOut;
            }
        }
    }
}
