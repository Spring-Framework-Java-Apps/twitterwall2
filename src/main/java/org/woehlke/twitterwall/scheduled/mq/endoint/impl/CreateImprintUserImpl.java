package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.scheduled.mq.endoint.CreateImprintUser;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;

@Component("mqCreateImprintUser")
public class CreateImprintUserImpl implements CreateImprintUser {

    private final TwitterApiService twitterApiService;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    @Autowired
    public CreateImprintUserImpl(TwitterApiService twitterApiService, TwitterwallFrontendProperties twitterwallFrontendProperties) {
        this.twitterApiService = twitterApiService;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
    }

    @Override
    public Message<TwitterProfileMessage> createImprintUser(Message<TaskMessage> mqMessageIn) {
        String logMsg = "createImprintUser: ";
        TaskMessage receivedMessage = mqMessageIn.getPayload();
        String screenName = twitterwallFrontendProperties.getImprintScreenName();
        TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
        TwitterProfileMessage outMsg = new TwitterProfileMessage(receivedMessage,screenName,twitterProfile);
        Message<TwitterProfileMessage> mqMessageOut = MessageBuilder.withPayload(outMsg).copyHeaders(mqMessageIn.getHeaders())
            .setHeader("twitter_profile_id", twitterProfile.getId())
            .build();
        return mqMessageOut;
    }
}


