package org.woehlke.twitterwall.backend.mq.urls.msg.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;
import org.woehlke.twitterwall.oodm.model.Url;

@Component
public class UrlMessageBuilderImpl implements UrlMessageBuilder {

    @Override
    public Message<UrlMessage> createUrlMessage(Message<TaskMessage> incomingTaskMessage, long urlId, String urlString) {
        UrlMessage urlMessage = new UrlMessage(incomingTaskMessage.getPayload(),urlId,urlString);
        Message<UrlMessage> mqMessageOut =
                MessageBuilder.withPayload(urlMessage)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UrlMessage> createUrlMessage(Message<UrlMessage> incomingUrlMessage, Url foundUrl) {
        UrlMessage in = incomingUrlMessage.getPayload();
        UrlMessage out = new UrlMessage(in.getTaskMessage(),in.getUrlId(),in.getUrlString(),foundUrl.getExpanded(),foundUrl.getDisplay());
        Message<UrlMessage> mqMessageOut =
                MessageBuilder.withPayload(out)
                        .copyHeaders(incomingUrlMessage.getHeaders())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UrlMessage> createUrlMessage(Message<UrlMessage> incomingUrlMessage, boolean ignoreNextSteps) {
        UrlMessage in = incomingUrlMessage.getPayload();
        UrlMessage out = new UrlMessage(in.getTaskMessage(),in.getUrlId(),in.getUrlString(),ignoreNextSteps);
        Message<UrlMessage> mqMessageOut =
                MessageBuilder.withPayload(out)
                        .copyHeaders(incomingUrlMessage.getHeaders())
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UrlMessage> createUrlMessage(Message<UrlMessage> incomingUrlMessage) {
        Message<UrlMessage> mqMessageOut =
                MessageBuilder.withPayload(incomingUrlMessage.getPayload())
                        .copyHeaders(incomingUrlMessage.getHeaders())
                        .build();
        return mqMessageOut;
    }

}
