package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;

public interface FetchDataFromDatabase {

    void updateTweets(Message<TaskMessage> mqMessage);

    void updateUserProfiles(Message<TaskMessage> mqMessage);

    void updateUserProfilesFromMentions(Message<TaskMessage> mqMessage);
}
