package org.woehlke.twitterwall.backend.mq.tweets.msg.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessageBuilder;

@Component
public class TweetMessageBuilderImpl implements TweetMessageBuilder {

    @Override
    public Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.model.Tweet tweet, int loopId, int loopAll){
        TweetMessage outputPayload = new TweetMessage(incomingTaskMessage.getPayload(),tweet);
        Message<TweetMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll){
        TweetMessage outputPayload = new TweetMessage(incomingTaskMessage.getPayload(),tweet);
        Message<TweetMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

}
