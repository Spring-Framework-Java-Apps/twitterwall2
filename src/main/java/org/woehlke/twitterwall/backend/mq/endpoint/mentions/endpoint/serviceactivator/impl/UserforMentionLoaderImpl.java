package org.woehlke.twitterwall.backend.mq.endpoint.mentions.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.endpoint.mentions.endpoint.serviceactivator.UserforMentionLoader;
import org.woehlke.twitterwall.backend.mq.endpoint.mentions.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.mentions.msg.MentionMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;

@Component("mqUserforMentionLoader")
public class UserforMentionLoaderImpl implements UserforMentionLoader {

    @Override
    public Message<MentionMessage> fetchUserforMentionfromTwitter(Message<MentionMessage> incomingMessage) {
        MentionMessage receivedMessage = incomingMessage.getPayload();
        String screenName = receivedMessage.getScreenName();
        TwitterProfile userFromTwitter = twitterApiService.getUserProfileForScreenName(screenName);
        Message<MentionMessage> outMessage = mentionMessageBuilder.buildMentionMessage(incomingMessage,userFromTwitter);
        return outMessage;
    }

    private final TwitterApiService twitterApiService;

    private final MentionMessageBuilder mentionMessageBuilder;

    @Autowired
    public UserforMentionLoaderImpl(TwitterApiService twitterApiService, MentionMessageBuilder mentionMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.mentionMessageBuilder = mentionMessageBuilder;
    }
}
