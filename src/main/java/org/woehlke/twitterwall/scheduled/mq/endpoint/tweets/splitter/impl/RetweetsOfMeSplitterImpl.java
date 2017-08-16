package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.splitter.impl;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.splitter.RetweetsOfMeSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

import java.util.List;

@Component("mqRetweetsOfMeSplitter")
public class RetweetsOfMeSplitterImpl implements RetweetsOfMeSplitter {

    @Override
    public List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message) {
        return null;
    }
}
