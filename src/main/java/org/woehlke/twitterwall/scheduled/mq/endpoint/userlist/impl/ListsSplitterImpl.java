package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.ListsSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

import java.util.List;

@Component("mqListsSplitter")
public class ListsSplitterImpl implements ListsSplitter {
    @Override
    public List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage) {
        return null;
    }
}
