package org.woehlke.twitterwall.oodm.model;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskStatus;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlTest implements DomainObjectMinimalTest  {

    @Test
    @Override
    public void test001getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        String descriptionTask = "start: ";
        TaskType type = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,type,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);

        Task createdBy=task;
        Task updatedBy=null;
        String urlUrl = "https://t.co/qCd5aTUWEr";
        String urlExpanded = "https://thomas-woehlke.blogspot.de/";
        String urlDisplay = "thomas-woehlke.blogspot.de";

        Url url = new Url(createdBy,updatedBy,urlUrl);

        Assert.assertEquals(msg,urlUrl,url.getUniqueId());
    }

    @Test
    @Override
    public void test002isValidTest() throws Exception {
        String msg = "isValidTest: ";

        String descriptionTask = "start: ";
        TaskType type = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,type,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);

        Task createdBy=task;
        Task updatedBy=null;
        String urlUrl = "https://t.co/qCd5aTUWEr";
        String urlExpanded = "https://thomas-woehlke.blogspot.de/";
        String urlDisplay = "thomas-woehlke.blogspot.de";

        Url url = new Url(createdBy,updatedBy,urlUrl);

        Assert.assertTrue(msg,url.isValid());
        Assert.assertTrue(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());

        url.setExpanded(urlExpanded);
        url.setDisplay(urlDisplay);
        Assert.assertTrue(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());

        url.setExpanded(urlUrl);
        Assert.assertTrue(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertTrue(msg,url.isUrlAndExpandedTheSame());

        url.setUrl(null);
        Assert.assertFalse(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());

        url.setUrl("bdfjhbvjhbve837783786hbcd");
        Assert.assertFalse(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());
    }
}
