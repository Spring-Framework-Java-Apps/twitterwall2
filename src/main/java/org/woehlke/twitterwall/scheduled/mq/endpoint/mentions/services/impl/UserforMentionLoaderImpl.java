package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services.UserforMentionLoader;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

@Component("mqUserforMentionLoader")
public class UserforMentionLoaderImpl implements UserforMentionLoader {

    @Override
    public Message<MentionMessage> fetchUserforMentionfromTwitter(Message<MentionMessage> incomingMessage) {
        MentionMessage receivedMessage = incomingMessage.getPayload();
        String screenName = receivedMessage.getScreenName();
        TwitterProfile userFromTwitter = twitterApiService.getUserProfileForScreenName(screenName);
        Message<MentionMessage> outMessage = twitterwallMessageBuilder.buildMentionMessage(incomingMessage,userFromTwitter);
        return outMessage;
    }

    private final TwitterApiService twitterApiService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    @Autowired
    public UserforMentionLoaderImpl(TwitterApiService twitterApiService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }
}
