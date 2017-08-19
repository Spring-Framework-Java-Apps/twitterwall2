package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.splitter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.MentionService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.splitter.UpdateUsersFromMentionsSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.MentionMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.frontend.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component("mqUpdateUserFromMentionsSplitter")
public class UpdateUsersFromMentionsSplitterImpl implements UpdateUsersFromMentionsSplitter {

    private static final Logger log = LoggerFactory.getLogger(UpdateUsersFromMentionsSplitterImpl.class);

    private final TwitterProperties twitterProperties;

    private final TaskService taskService;

    private final MentionService mentionService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    private final MentionMessageBuilder mentionMessageBuilder;

    public UpdateUsersFromMentionsSplitterImpl(TwitterProperties twitterProperties, TaskService taskService, MentionService mentionService, UserService userService, CountedEntitiesService countedEntitiesService, MentionMessageBuilder mentionMessageBuilder) {
        this.twitterProperties = twitterProperties;
        this.taskService = taskService;
        this.mentionService = mentionService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
        this.mentionMessageBuilder = mentionMessageBuilder;
    }

    @Override
    public List<Message<MentionMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        String msg ="splitTweetMessage: ";
        log.debug(msg+ " START");
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<MentionMessage>>  resultList = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<String> screenNames = new ArrayList<>();
        long loopId = 0L;
        long loopAll;
        boolean hasNext=true;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<Mention> allPersMentions = mentionService.getAllWithoutUser(pageRequest);
            loopAll =  allPersMentions.getTotalElements();
            hasNext = allPersMentions.hasNext();
            for (Mention onePersMention : allPersMentions) {
                if (!onePersMention.hasUser()) {
                    String screenName = onePersMention.getScreenName();
                    log.debug("### mentionService.getAllWithoutUser from DB (" + loopId + " of "+loopAll+"): " + screenName);
                    User foundUser = userService.findByScreenName(screenName);
                    if(foundUser == null) {
                        loopId++;
                        screenNames.add(screenName);
                        Message<MentionMessage> mqMessageOut = mentionMessageBuilder.buildMentionMessageForTask(incomingTaskMessage,onePersMention);
                        resultList.add(mqMessageOut);
                    } else {
                        foundUser = userService.store(foundUser,task);
                        onePersMention.setIdTwitterOfUser(foundUser.getIdTwitter());
                        onePersMention.setIdOfUser(foundUser.getId());
                        onePersMention = mentionService.update(onePersMention,task);
                        log.debug("### updated Mention with screenName = " + onePersMention.getUniqueId());
                    }
                }
            }
            pageRequest = pageRequest.next();
        }
        return resultList;
    }
}
