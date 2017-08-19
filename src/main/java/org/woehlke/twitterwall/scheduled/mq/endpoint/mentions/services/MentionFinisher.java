package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.results.MentionResultList;

import java.util.List;

public interface MentionFinisher {

    Message<MentionResultList> finish(Message<List<MentionMessage>> incomingMessageList);

    void finishAsnyc(Message<List<MentionMessage>> incomingMessageList);
}
