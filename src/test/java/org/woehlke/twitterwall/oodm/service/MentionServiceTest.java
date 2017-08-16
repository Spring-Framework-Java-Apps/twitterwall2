package org.woehlke.twitterwall.oodm.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.scheduled.mq.msg.SendType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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

    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(mentionService);
        Assert.assertNotNull(testdataProperties);
        Assert.assertNotNull(countedEntitiesService);
    }

    @Test
    public void fetchTestData() throws Exception {
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

    @Test
    public void createProxyMention() throws Exception {
        String msg = "createProxyMention: ";
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskType type = TaskType.FETCH_TWEETS_FROM_SEARCH;
        SendType sendType = SendType.NO_MQ;
        Task task = taskService.create("MentionServiceTest."+msg,type,sendType,countedEntities);
        String mentionString = "ddhgcvdghvsdhg";
        Mention mention = new Mention(task,task, mentionString);
        Mention createdMention = mentionService.createProxyMention(mention,task);
        Assert.assertEquals(mentionString,createdMention.getScreenName());
        Assert.assertTrue(createdMention.isProxy());
    }

    @Test
    public void getAllWithoutPersistentUser() throws Exception {
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

    @Test
    public void findByIdTwitter() throws Exception {
        String msg = "findByIdTwitter: ";
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> myPage = mentionService.getAll(pageRequest);
        Assert.assertTrue(msg,myPage.getTotalElements()>0);
        for(Mention myMention:myPage.getContent()){
            long idTwitter = myMention.getIdTwitter();
            if(idTwitter > 0L) {
                Mention myFoundMention = mentionService.findByIdTwitter(idTwitter);
                Assert.assertNotNull(myFoundMention);
                Assert.assertEquals(msg, idTwitter, myFoundMention.getIdTwitter().longValue());
            }
        }
    }

    @Test
    public void findByScreenName() throws Exception {
        String msg = "findByScreenName: ";
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


    @Test
    @Override
    public void findById() throws Exception {

    }

    @Test
    @Override
    public void getAll() throws Exception {

    }

    @Test
    @Override
    public void count() throws Exception {

    }

    @Test
    @Override
    public void findByUniqueId() throws Exception {

    }

    @Test
    @Override
    public void store() throws Exception {

    }

    @Test
    @Override
    public void create() throws Exception {

    }

    @Test
    @Override
    public void update() throws Exception {

    }
}
