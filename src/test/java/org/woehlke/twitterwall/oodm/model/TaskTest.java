package org.woehlke.twitterwall.oodm.model;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskStatus;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

import java.util.Date;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskTest implements DomainObjectMinimalTest  {

    private static final Logger log = LoggerFactory.getLogger(TweetTest.class);

    @Test
    @Override
    public void test001getUniqueIdTest() throws Exception {
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

        String myUniqueId = "" + taskType.name() +"_"+ timeStarted.getTime();

        log.debug(msg+" Expected: "+myUniqueId+" == Found: "+task.getUniqueId());

        Assert.assertEquals(msg,myUniqueId,task.getUniqueId());
    }

    @Test
    @Override
    public void test002isValidTest() throws Exception {
        String msg = "isValidTest: ";

        String descriptionTask = "Make it so, Scotty";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;

        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;

        Task task1 = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);
        Task task2 = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);
        Task task3 = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);
        Task task4 = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);

        task2.setTaskType(null);
        task3.setTimeStarted(null);
        task4.setTaskType(TaskType.NULL);

        Assert.assertTrue(msg,task1.isValid());
        Assert.assertFalse(msg,task2.isValid());
        Assert.assertFalse(msg,task3.isValid());
        Assert.assertFalse(msg,task4.isValid());
    }
}
