package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.UserCheckStorage;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

@Component("mqUserCheckStorage")
public class UserCheckStorageImpl implements UserCheckStorage {

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    @Autowired
    public UserCheckStorageImpl(UserService userService, TwitterApiService twitterApiService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    @Override
    public Message<UserMessage> checkIfUserIsInStorage(Message<UserMessage> incomingMessage) {
        UserMessage receivedMessage = incomingMessage.getPayload();
        long userIdTwitter = receivedMessage.getTwitterProfileId();
        boolean isInStorage = userService.isByIdTwitter(userIdTwitter);
        if(isInStorage){
            Message<UserMessage> mqMessageOut = twitterwallMessageBuilder.buildUserMessage(incomingMessage,isInStorage);
            return mqMessageOut;
        } else {
            boolean ignoreTransformation = false;
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(userIdTwitter);
            Message<UserMessage> mqMessageOut = twitterwallMessageBuilder.buildUserMessage(incomingMessage, twitterProfile, ignoreTransformation);
            return mqMessageOut;
        }
    }
}
