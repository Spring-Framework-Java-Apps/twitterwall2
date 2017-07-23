package org.woehlke.twitterwall.schedulled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.schedulled.mq.msg.TaskMessage;

public interface FetchDataFromDatabase {

    void updateTweets(Message<TaskMessage> mqMessage);

    void updateUserProfiles(Message<TaskMessage> mqMessage);

    void updateUserProfilesFromMentions(Message<TaskMessage> mqMessage);
}
