package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.CreateImprintUser;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

import static org.woehlke.twitterwall.ScheduledTasks.TWELVE_HOURS;

@Component("mqCreateImprintUser")
public class CreateImprintUserImpl implements CreateImprintUser {

    private final TwitterApiService twitterApiService;

    private final FrontendProperties frontendProperties;

    private final UserService userService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    @Autowired
    public CreateImprintUserImpl(TwitterApiService twitterApiService, FrontendProperties frontendProperties, UserService userService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.frontendProperties = frontendProperties;
        this.userService = userService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    @Override
    public Message<UserMessage> createImprintUser(Message<TaskMessage> mqMessageIn) {
        String logMsg = "createImprintUser: ";
        TaskMessage receivedMessage = mqMessageIn.getPayload();
        String screenName = frontendProperties.getImprintScreenName();
        User imprintUser = userService.findByScreenName(screenName);
        if(imprintUser==null){
            return this.getMessageOut(mqMessageIn);
        } else {
            if (imprintUser.getTaskBasedCaching().isCached(receivedMessage.getTaskType(), TWELVE_HOURS)) {
                Message<UserMessage> mqMessageOut = twitterwallMessageBuilder.buildUserMessage(mqMessageIn,imprintUser);
                return mqMessageOut;
            } else {
                return this.getMessageOut(mqMessageIn);
            }
        }
    }

    private Message<UserMessage> getMessageOut(Message<TaskMessage> mqMessageIn){
        String screenName = frontendProperties.getImprintScreenName();
        TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
        Message<UserMessage> mqMessageOut = twitterwallMessageBuilder.buildUserMessage(mqMessageIn,twitterProfile);
        twitterwallMessageBuilder.waitForApi();
        return mqMessageOut;
    }
}


