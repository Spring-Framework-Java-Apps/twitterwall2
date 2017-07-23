package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;

public interface FetchDataFromRemoteTwitterApi {

    void fetchTweetsFromTwitterSearch(Message<TaskMessage> mqMessage);

    void fetchUsersFromDefinedUserList(Message<TaskMessage> mqMessage);

    void createTestDataForUser(Message<TaskMessage> mqMessage);
}
