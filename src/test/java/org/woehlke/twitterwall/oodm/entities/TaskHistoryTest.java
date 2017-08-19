package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskType;

import java.util.Date;

public class TaskHistoryTest implements DomainObjectMinimalTest  {

    private static final Logger log = LoggerFactory.getLogger(TaskHistoryTest.class);

    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        String descriptionTask = "Make it so, Scotty";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        long taskId = 222L;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);
        task.setId(taskId);

        String description = "Beam me up, Scotty";
        TaskStatus taskStatusBefore = TaskStatus.READY;
        TaskStatus taskStatusNow = TaskStatus.RUNNING;
        Date timeEvent = new Date();

        CountedEntities countedEntities = null;

        TaskHistory taskHistory = new TaskHistory(description, taskStatusBefore, taskStatusNow, timeEvent, task, countedEntities);

        String myUniqueId = "" + task.getId().toString()  +"_"+  timeEvent.getTime();

        log.info(msg+" Expected: "+myUniqueId+" == Found: "+taskHistory.getUniqueId());

        Assert.assertEquals(msg,myUniqueId,taskHistory.getUniqueId());
    }

    @Test
    @Override
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";

        String descriptionTask = "Make it so, Scotty";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        long taskId = 222L;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);
        task.setId(taskId);

        String description = "Beam me up, Scotty";
        TaskStatus taskStatusBefore = TaskStatus.READY;
        TaskStatus taskStatusNow = TaskStatus.RUNNING;
        Date timeEvent = new Date();

        CountedEntities countedEntities = null;

        TaskHistory taskHistory1 = new TaskHistory(description, taskStatusBefore, taskStatusNow, timeEvent, task, countedEntities);
        TaskHistory taskHistory2 = new TaskHistory(description, taskStatusBefore, taskStatusNow, timeEvent, task, countedEntities);
        TaskHistory taskHistory3 = new TaskHistory(description, taskStatusBefore, taskStatusNow, timeEvent, task, countedEntities);
        TaskHistory taskHistory4 = new TaskHistory(description, taskStatusBefore, taskStatusNow, timeEvent, task, countedEntities);

        taskHistory2.setTask(null);
        taskHistory3.setIdTask(null);
        taskHistory4.setTimeEvent(null);

        Assert.assertTrue(msg,taskHistory1.isValid());
        Assert.assertFalse(msg,taskHistory2.isValid());
        Assert.assertFalse(msg,taskHistory3.isValid());
        Assert.assertFalse(msg,taskHistory4.isValid());
    }
}
