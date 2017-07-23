package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.endoint.UserFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;

import java.util.List;

@Component("mqUserFinisher")
public class UserFinisherImpl implements UserFinisher {


    @Override
    public UserResultList finish(Message<List<User>> incomingMessageList) {
        long taskId = 0L;
        List<User> userList = incomingMessageList.getPayload();
        UserResultList userResultList = new UserResultList(taskId,userList);
        return userResultList;
    }
}
