package org.woehlke.twitterwall.oodm.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class TweetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceTest.class);

    @Autowired
    private TweetService tweetService;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private MentionService mentionService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private TickerSymbolService tickerSymbolService;

    @Autowired
    private UserService userService;

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    @Autowired
    private TestdataProperties testdataProperties;

    @Commit
    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(tweetService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Tweet> myPage = tweetService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Tweet myTweet = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myTweet);
            Assert.assertNotNull(msg,myTweet.getUniqueId());
            log.info(msg+" found: "+myTweet.getUniqueId());
        } else {
            log.info(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void findByIdTwitter() throws Exception {
        String msg = "findByIdTwitter: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Tweet> myPage = tweetService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Tweet myMedia = myPage.getContent().iterator().next();
            long expectedIdTwitter = myMedia.getIdTwitter();
            Tweet myFoundMedia = tweetService.findByIdTwitter(expectedIdTwitter);
            long foundIdTwitter = myFoundMedia.getIdTwitter();
            Assert.assertEquals(msg, expectedIdTwitter,foundIdTwitter);
            log.info(msg+" found: "+myMedia.getUniqueId());
        } else {
            log.error(msg+" found: myPage.getTotalElements() == 0");
        }
    }


    //TODO: #160 https://github.com/phasenraum2010/twitterwall2/issues/160
    @Commit
    @Test
    public void findTweetsForHashTag() throws Exception {
        String msg = "findTweetsForHashTag: ";
        log.info(msg);
    }

    //TODO: #216 https://github.com/phasenraum2010/twitterwall2/issues/216
    //@Ignore
    @Commit
    @Test
    public void findTweetsForUser() throws Exception {
        String msg = "findTweetsForUser: ";
        int page=1;
        int size=100;
        Pageable pageRequest = new PageRequest(page,size);
        log.info(msg + "STARTED TEST");
        Page<User> foundTweetingUsers = userService.getTweetingUsers(pageRequest);
        long loopUser = 0L;
        long loopTweet = 0L;
        for(User user : foundTweetingUsers.getContent()){
            loopUser++;
            Assert.assertTrue(msg,user.getTweeting());
            Page<Tweet> foundTweets = tweetService.findTweetsForUser(user,pageRequest);
            Assert.assertNotNull(msg,foundTweets);
            //Assert.assertTrue(foundTweets.getTotalElements()>0);
            for(Tweet tweet : foundTweets.getContent()) {
                loopTweet++;
                Assert.assertNotNull(msg,tweet.getUser());
                Assert.assertEquals(msg,tweet.getUser().getUniqueId(), user.getUniqueId());
                log.info(msg);
            }
            log.info(msg+" RUNNING TEST. Tested Users "+loopUser+" and Tweets "+loopTweet);
        }
        log.info(msg+" FINISHED TEST. Tested Users "+loopUser+" and Tweets "+loopTweet);
    }

    @Commit
    @Test
    public void findAllTweet2HashTag() throws Exception {
        String msg = "findAllTweet2HashTag: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2HashTag(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                Tweet foundObject = tweetService.findById(objectId);
                HashTag foundEntity = hashTagService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getHashTags().contains(foundEntity));
            }
        }
    }

    @Commit
    @Test
    public void findAllTweet2Media() throws Exception {
        String msg = "findAllTweet2Media: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2Media(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                Tweet foundObject = tweetService.findById(objectId);
                Media foundEntity = mediaService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getMedia().contains(foundEntity));
            }
        }
    }

    @Commit
    @Test
    public void findAllTweet2Mention() throws Exception {
        String msg = "findAllTweet2Mention: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2Mention(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                Tweet foundObject = tweetService.findById(objectId);
                Mention foundEntity = mentionService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getMentions().contains(foundEntity));
            }
        }
    }

    @Commit
    @Test
    public void findAllTweet2Url() throws Exception {
        String msg = "findAllTweet2Url: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2Url(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                Tweet foundObject = tweetService.findById(objectId);
                Url foundEntity = urlService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getUrls().contains(foundEntity));
            }
        }
    }

    @Commit
    @Test
    public void findAllTweet2TickerSymbol() throws Exception {
        String msg = "findAllTweet2TickerSymbol: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2TickerSymbol(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                Tweet foundObject = tweetService.findById(objectId);
                TickerSymbol foundEntity = tickerSymbolService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getTickerSymbols().contains(foundEntity));
            }
        }
    }

}
