package org.woehlke.twitterwall.oodm.service;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.configuration.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MentionServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest,DomainServiceWithScreenNameTest,DomainServiceWithIdTwitterTest {

    private static final Logger log = LoggerFactory.getLogger(MentionServiceTest.class);

    @Autowired
    private MentionService mentionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Commit
    @Test
    public void test000areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(mentionService);
        Assert.assertNotNull(testdataProperties);
        Assert.assertNotNull(countedEntitiesService);
    }

    @Commit
    @Test
    public void test000createTestData() throws Exception {
        String msg = "createTestData: ";
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task createdBy= taskService.create(msg, TaskType.NULL, TaskSendType.NO_MQ,countedEntities);
        Task updatedBy=null;

        String screenName[] = {
                "port80guru",
                "ThomasWoehlke",
                "OracleDevs",
                "java"
        };
        long idTwitter[] = {242L, 1242L, 3242L, 4242L};
        String name[] = {
                "Natural Born Coder",
                "Thomas Woehlke",
                "Oracle Developer",
                "Java"
        };

        int pageNr=1;
        int pageSize=1;
        Pageable pageRequest = new PageRequest(pageNr,pageSize);

        for(int i=0;i<4;i++){
            Page<Mention> foundMentionsPage = mentionService.findAllByScreenName(screenName[i],pageRequest);
            if(foundMentionsPage.getTotalElements() == 0){
                waitFor500ms();
                Date now = new Date();
                long myIdTwitter = idTwitter[i] + now.getTime();
                Mention mentionTest = new Mention(createdBy,updatedBy,myIdTwitter, screenName[i], name[i]);
                mentionTest = mentionService.store(mentionTest,createdBy);
                Assert.assertNotNull(mentionTest);
                Assert.assertNotNull(mentionTest.getId());
                Assert.assertTrue(mentionTest.isValid());
            }
        }
    }

    @Commit
    @Test
    public void test001fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> myPage = mentionService.getAll(pageRequest);
        Assert.assertTrue(msg,myPage.getTotalElements()>0);
        if(myPage.getTotalElements()>0){
            Mention myMedia = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myMedia);
            Assert.assertNotNull(msg,myMedia.getUniqueId());
            log.debug(msg+" found: "+myMedia.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void test010createProxyMention() throws Exception {
        String msg = "createProxyMention: ";
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskType type = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        Task task = taskService.create("MentionServiceTest."+msg,type, taskSendType,countedEntities);
        String mentionString = "ddhgcvdghvsdhg";
        Mention mention = new Mention(task,task, mentionString);
        Mention createdMention = mentionService.createProxyMention(mention,task);
        Assert.assertEquals(mentionString,createdMention.getScreenName());
        Assert.assertTrue(createdMention.isProxy());
    }

    @Commit
    @Test
    public void test011getAllWithoutPersistentUser() throws Exception {
        String msg = "getAllWithoutUser: ";
        int page=1;
        int size=100;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> pageMention =  mentionService.getAllWithoutUser(pageRequest);
        Assert.assertTrue(msg,pageMention.getTotalElements()>0);
        for(Mention mention: pageMention.getContent()){
            Assert.assertTrue(msg,mention.getIdTwitterOfUser()==0L);
            Assert.assertFalse(msg,mention.hasUser());
        }
    }

    @Commit
    @Test
    public void test020findByIdTwitter() throws Exception {
        String msg = "findByIdTwitter: ";
        test000createTestData();
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> myPage = mentionService.getAll(pageRequest);
        Assert.assertTrue(msg,myPage.getTotalElements()>0);
        for(Mention myMention:myPage.getContent()){
            long myIdTwitter = myMention.getIdTwitter();
            if(myIdTwitter > 0L) {
                Mention myFoundMention = mentionService.findByIdTwitter(myIdTwitter);
                Assert.assertNotNull(myFoundMention);
                Assert.assertEquals(msg, myIdTwitter, myFoundMention.getIdTwitter().longValue());
            }
        }
    }

    @Commit
    @Test
    @Override
    public void test030findByScreenName() throws Exception {
        String msg = "findByScreenName: ";
        test000createTestData();
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> myPage = mentionService.getAll(pageRequest);
        Assert.assertTrue(msg,myPage.getTotalElements()>0);
        if(myPage.getTotalElements()>0) {
            Mention myMention = myPage.getContent().iterator().next();
            String expectedScreenName = myMention.getScreenName();
            Mention myFoundMention = mentionService.findByScreenName(expectedScreenName);
            Assert.assertNotNull(myFoundMention);
            String foundScreenName =myFoundMention.getScreenName();
            Assert.assertEquals(msg,expectedScreenName,foundScreenName);
        }
    }


    @Commit
    @Test
    @Override
    public void test050findById() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test051getAll() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test052count() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test053findByUniqueId() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test100store() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test101create() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test102update() throws Exception {

    }

    @Commit
    @Test
    public void findAllByScreenName() throws Exception {

    }

    @Commit
    @Test
    public void findByIdTwitterOfUser() throws Exception {

    }

    private void waitFor500ms(){
        int millisToWait = 500;
        log.debug("### waiting now for (ms): "+millisToWait);
        try {
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
        }
    }

}
