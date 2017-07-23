package org.woehlke.twitterwall.schedulled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.schedulled.mq.msg.TaskMessage;

public interface FetchDataFromRemoteTwitterApi {

    void fetchTweetsFromTwitterSearch(Message<TaskMessage> mqMessage);

    void fetchUsersFromDefinedUserList(Message<TaskMessage> mqMessage);

    void createTestDataForUser(Message<TaskMessage> mqMessage);

    void createtestDataForTweets(Message<TaskMessage> mqMessage);

    void createImprintUser(Message<TaskMessage> mqMessage);
}
