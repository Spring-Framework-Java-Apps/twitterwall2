package org.woehlke.twitterwall.backend.mq.urls.msg;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.oodm.model.Url;

public interface UrlMessageBuilder {

    Message<UrlMessage> createUrlMessage(Message<TaskMessage> incomingTaskMessage, long urlId, String urlString);

    Message<UrlMessage> createUrlMessage(Message<UrlMessage> incomingUserMessage, Url foundUrl);

    Message<UrlMessage> createUrlMessage(Message<UrlMessage> incomingUserMessage, boolean ignoreNextSteps);

    Message<UrlMessage> createUrlMessage(Message<UrlMessage> incomingUserMessage);
}
