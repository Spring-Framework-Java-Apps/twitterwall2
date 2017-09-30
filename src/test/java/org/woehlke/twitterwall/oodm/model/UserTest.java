package org.woehlke.twitterwall.oodm.model;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.model.tasks.TaskStatus;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;

import java.util.Date;


/**
 * Created by tw on 22.06.17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest implements DomainObjectMinimalTest {

    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    @Test
    @Override
    public void test001getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";
        log.debug(msg+"------------------------------------------------");

        String screenName = "port80guru";

        String descriptionTask = "start: ";
        TaskType type = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,type,taskStatus, taskSendType, timeStarted,timeLastUpdate,timeFinished);

        User user1 = User.getDummyUserForScreenName(screenName,task);

        String mygUniqueId1 = user1.getIdTwitter().toString()+"_"+user1.getScreenNameUnique();

        log.debug(msg+" Expected: "+mygUniqueId1+" == Found: "+user1.getUniqueId());

        Assert.assertEquals(msg,mygUniqueId1,user1.getUniqueId());

        long idTwitter= new Date().getTime();
        String name="Java";
        String url="https://t.co/qQ19mq2e6G";
        String profileImageUrl="https://pbs.twimg.com/profile_images/426420605945004032/K85ZWV2F_400x400.png";
        String description="This is the official Twitter channel for Java and the source for Java news from the Java community. Managed by Yolande Poirier @ypoirier";
        String location="Worldwide";
        Date createdDate = new Date();
        User user2 = new User(task,null,idTwitter,screenName, name, url, profileImageUrl, description, location, createdDate);

        String mygUniqueId2 = Long.toString(idTwitter)+"_"+user2.getScreenNameUnique();

        log.debug(msg+" Expected: "+mygUniqueId2+" == Found: "+user2.getUniqueId());

        Assert.assertEquals(msg,mygUniqueId2,user2.getUniqueId());

        log.debug(msg+"------------------------------------------------");
    }

    @Test
    @Override
    public void test002isValidTest() throws Exception {
        String msg = "isValidTest: ";
        log.debug(msg+"------------------------------------------------");

        String invalidScreenName = "3765726öäöäß%$dsffsdf";
        Long idTwitter1 = null;
        Long idTwitter2 = 5327635273276L;

        String screenName = "port80guru";

        String descriptionTask = "start: ";
        TaskType type = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;
        Task task = new Task(descriptionTask,type,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);

        Assert.assertFalse(msg,User.isValidScreenName(invalidScreenName));

        User user = User.getDummyUserForScreenName(screenName,task);
        Assert.assertTrue(msg,user.isValid());

        user.setIdTwitter(idTwitter1);
        Assert.assertFalse(msg,user.isValid());

        user.setIdTwitter(idTwitter2);
        Assert.assertTrue(msg,user.isValid());

        user.setScreenName(invalidScreenName);
        Assert.assertFalse(msg,user.isValid());

        log.debug(msg+"------------------------------------------------");
    }
}
