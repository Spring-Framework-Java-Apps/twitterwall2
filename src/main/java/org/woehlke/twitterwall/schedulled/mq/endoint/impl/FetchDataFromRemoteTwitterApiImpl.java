package org.woehlke.twitterwall.schedulled.mq.endoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.schedulled.mq.endoint.FetchDataFromRemoteTwitterApi;
import org.woehlke.twitterwall.schedulled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.schedulled.mq.msg.TaskMessage;

@Component("mqFetchDataFromRemoteTwitterApi")
public class FetchDataFromRemoteTwitterApiImpl implements FetchDataFromRemoteTwitterApi {

    private static final Logger log = LoggerFactory.getLogger(FetchDataFromRemoteTwitterApiImpl.class);

    private final TaskService taskService;

    private final TwitterApiService twitterApiService;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    @Autowired
    public FetchDataFromRemoteTwitterApiImpl(TaskService taskService, TwitterApiService twitterApiService, TwitterwallFrontendProperties twitterwallFrontendProperties) {
        this.taskService = taskService;
        this.twitterApiService = twitterApiService;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
    }

    private void react(String logMsg,Message<TaskMessage> mqMessage){
        TaskMessage receivedMessage = mqMessage.getPayload();
        Task task = taskService.findById(receivedMessage.getTaskId());
        taskService.start(task);
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"received: "+mqMessage.getPayload().toString());
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
    }

    @Override
    public void fetchTweetsFromTwitterSearch(Message<TaskMessage> mqMessage) {
        String logMsg = "fetchTweetsFromTwitterSearch: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void fetchUsersFromDefinedUserList(Message<TaskMessage> mqMessage) {
        String logMsg = "fetchUsersFromDefinedUserList: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void createTestDataForUser(Message<TaskMessage> mqMessage) {
        String logMsg = "createTestDataForUser: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void createtestDataForTweets(Message<TaskMessage> mqMessage) {
        String logMsg = "createtestDataForTweets: ";
        this.react(logMsg, mqMessage);
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
