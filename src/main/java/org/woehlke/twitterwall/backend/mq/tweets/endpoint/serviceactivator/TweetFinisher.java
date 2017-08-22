package org.woehlke.twitterwall.backend.mq.tweets.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetResultList;

import java.util.List;

public interface TweetFinisher {

    Message<TweetResultList> finish(Message<List<TweetMessage>> incomingMessageList);

    void finishAsnyc(Message<List<TweetMessage>> incomingMessageList);
}
