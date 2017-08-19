package org.woehlke.twitterwall.backend.mq.endpoint.tweets.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.msg.results.TweetResultList;

import java.util.List;

public interface TweetFinisher {

    Message<TweetResultList> finish(Message<List<TweetMessage>> incomingMessageList);

    void finishAsnyc(Message<List<TweetMessage>> incomingMessageList);
}
