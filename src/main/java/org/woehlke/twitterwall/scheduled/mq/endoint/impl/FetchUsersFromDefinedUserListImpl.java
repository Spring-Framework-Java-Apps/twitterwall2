package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.FetchUsersFromDefinedUserList;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchUsersFromDefinedUserList")
public class FetchUsersFromDefinedUserListImpl implements FetchUsersFromDefinedUserList {

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    public FetchUsersFromDefinedUserListImpl(TwitterwallSchedulerProperties twitterwallSchedulerProperties, TwitterwallFrontendProperties twitterwallFrontendProperties, TwitterApiService twitterApiService, TaskService taskService) {
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
    }

    @Override
    public List<TwitterProfileMessage> splitMessage(Message<TaskMessage> message) {
        List<TwitterProfileMessage> userProfileList = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task);
        String imprintScreenName = twitterwallFrontendProperties.getImprintScreenName();
        String fetchUsersList = twitterwallSchedulerProperties.getFetchUserList().getName();
        List<TwitterProfile> foundTwitterProfiles = twitterApiService.findUsersFromDefinedList(imprintScreenName,fetchUsersList);
        for (TwitterProfile twitterProfile : foundTwitterProfiles) {
            TwitterProfileMessage userMsg = new TwitterProfileMessage(msgIn,twitterProfile);
            userProfileList.add(userMsg);
        }
        return userProfileList;
    }
}
