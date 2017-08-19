package org.woehlke.twitterwall.oodm.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 22.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserDescriptionTest {

    @Autowired
    private TestdataProperties testdataProperties;

    private static final Logger log = LoggerFactory.getLogger(UserDescriptionTest.class);

    @Test
    public void printDescriptionsTest(){

        String descriptionTask = "Just another Task";
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = new Date();
        Date timeFinished = null;

        Task task = new Task(descriptionTask,taskType,taskStatus, taskSendType,timeStarted,timeLastUpdate,timeFinished);
        int lfdNr = 0;

        List<String> descriptions = testdataProperties.getOodm().getEntities().getUser().getDescriptions();

        log.info("printDescriptionsTest");
        log.info("++++++++++++++++++++");
        log.info("found "+descriptions.size()+" descriptions");
        for(String description:descriptions){
            log.info("--------------------");
            lfdNr++;
            log.info("description "+lfdNr+": "+description);
            for(HashTag hashTag:this.getHashTags(description,task)){
                log.info("found hashTag: "+hashTag.getUniqueId());
            }
            for(Url url:this.getUrls(description,task)){
                log.info("found url: "+ url.getUniqueId());
            }
            for(Mention mention:this.getMentions(description,task)){
                log.info("found mention: "+mention.getUniqueId());
            }
        }
        log.info("++++++++++++++++++++");
    }

    private List<HashTag> getHashTags(String description,Task task){
        List<HashTag> hashTags = new ArrayList<>();
        Pattern hashTagPattern = Pattern.compile("#(\\w*)("+stopChar+")");
        Matcher m3 = hashTagPattern.matcher(description);
        while (m3.find()) {
            hashTags.add(new HashTag(task,null,m3.group(1)));
        }
        Pattern hashTagPattern2 = Pattern.compile("#(\\w*)$");
        Matcher m4 = hashTagPattern2.matcher(description);
        while (m4.find()) {
            hashTags.add(new HashTag(task,null,m4.group(1)));
        }
        return hashTags;
    }

    private List<Url> getUrls(String description,Task task){
        List<Url> urls = new ArrayList<>();
        Pattern hashTagPattern = Pattern.compile("(https://t\\.co/\\w*)("+stopChar+")");
        Matcher m3 = hashTagPattern.matcher(description);
        while (m3.find()) {
            urls.add(getUrl(m3.group(1),task));
        }
        Pattern hashTagPattern2 = Pattern.compile("(https://t\\.co/\\w*)$");
        Matcher m4 = hashTagPattern2.matcher(description);
        while (m4.find()) {
            urls.add(getUrl(m4.group(1),task));
        }
        return urls;
    }

    private Url getUrl(String urlString,Task task){
        String display="";
        String expanded="";
        Url newUrl = new Url(task,null,display,expanded,urlString);
        return newUrl;
    }

    private List<Mention> getMentions(String description,Task task){
        List<Mention> mentions = new ArrayList<>();
        Pattern mentionPattern1 = Pattern.compile("@(\\w*)("+stopChar+")");
        Matcher m3 = mentionPattern1.matcher(description);
        while (m3.find()) {
            mentions.add(getMention(m3.group(1),task));
        }
        Pattern mentionPattern2 = Pattern.compile("@(\\w*)$");
        Matcher m4 = mentionPattern2.matcher(description);
        while (m4.find()) {
            mentions.add(getMention(m4.group(1),task));
        }
        return mentions;
    }

    private Mention getMention(String mentionString, Task task) {
        long idTwitter = 10000000L;
        String screenName=mentionString;
        String name=mentionString;
        return new Mention(task,null,idTwitter,screenName,name);
    }

    static private String stopChar = Entities.stopChar;

}
