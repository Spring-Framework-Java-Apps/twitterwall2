package org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter;


import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;

import java.util.List;

public interface FetchUserListsForUsers {

    List<Message<UserListMessage>> splitUserListMessage(Message<UserMessage> incomingTaskMessage);

}
