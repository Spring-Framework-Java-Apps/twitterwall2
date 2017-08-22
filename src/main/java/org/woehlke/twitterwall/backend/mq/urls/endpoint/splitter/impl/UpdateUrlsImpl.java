package org.woehlke.twitterwall.backend.mq.urls.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.splitter.UpdateUrls;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UrlService;

import java.util.ArrayList;
import java.util.List;

@Component("mqUpdateUrls")
public class UpdateUrlsImpl implements UpdateUrls {

    @Override
    public List<Message<UrlMessage>> splitUrlMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UrlMessage>> messageListOut = new ArrayList<>();
        TaskMessage taskMessage = incomingTaskMessage.getPayload();
        long id = taskMessage.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);

        List<Url> foundRawUrlsFromDescription  = urlService.findRawUrlsFromDescription();
        for(Url url :foundRawUrlsFromDescription){
            long urlId = url.getId();
            String urlString = url.getUrl();
            Message<UrlMessage> msg = urlMessageBuilder.createUrlMessage(incomingTaskMessage,urlId,urlString);
            messageListOut.add(msg);
        }

        List<Url> foundUrlAndExpandedTheSame = urlService.findUrlAndExpandedTheSame();
        for(Url url :foundUrlAndExpandedTheSame){
            long urlId = url.getId();
            String urlString = url.getUrl();
            Message<UrlMessage> msg = urlMessageBuilder.createUrlMessage(incomingTaskMessage,urlId,urlString);
            messageListOut.add(msg);
        }

        return null;
    }



    private final CountedEntitiesService countedEntitiesService;

    private final UrlMessageBuilder urlMessageBuilder;

    private final UrlService urlService;

    private final TaskService taskService;

    @Autowired
    public UpdateUrlsImpl(CountedEntitiesService countedEntitiesService, UrlMessageBuilder urlMessageBuilder, UrlService urlService, TaskService taskService) {
        this.countedEntitiesService = countedEntitiesService;
        this.urlMessageBuilder = urlMessageBuilder;
        this.urlService = urlService;
        this.taskService = taskService;
    }
}
