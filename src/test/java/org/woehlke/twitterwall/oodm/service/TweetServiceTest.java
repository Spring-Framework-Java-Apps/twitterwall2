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
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TweetServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest ,DomainServiceWithIdTwitterTest{

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

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(tweetService);
        Assert.assertNotNull(testdataProperties);
    }

    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        log.info(msg+"START TEST");
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Tweet> myPage = tweetService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            for(Tweet myTweet :myPage.getContent()){
                Assert.assertNotNull(msg,myTweet);
                Assert.assertNotNull(msg,myTweet.getUniqueId());
                log.info(msg+" found: "+myTweet.getUniqueId());
            }
        } else {
            log.info(msg+" found: myPage.getTotalElements() == 0");
        }
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void findByIdTwitter() throws Exception {
        String msg = "findByIdTwitter: ";
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Tweet> myPage = tweetService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            for(Tweet tweet: myPage.getContent()){
                long expectedIdTwitter = tweet.getIdTwitter();
                Tweet myFoundTweet = tweetService.findByIdTwitter(expectedIdTwitter);
                if(myFoundTweet != null) {
                    long foundIdTwitter = myFoundTweet.getIdTwitter();
                    Assert.assertEquals(msg, expectedIdTwitter, foundIdTwitter);
                    log.info(msg + " found: " + myFoundTweet.getUniqueId());
                }
            }
        } else {
            log.error(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    public void findTweetsForHashTag() throws Exception {
        String msg = "findTweetsForHashTag: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<HashTag> hashTags = hashTagService.getAll(pageRequest);
        for(HashTag hashTag:hashTags.getContent()){
            log.debug(msg+" found HashTag: "+hashTag.getUniqueId());
            Page<Tweet> tweets = tweetService.findTweetsForHashTag(hashTag,pageRequest);
            for(Tweet tweet: tweets.getContent()){
                Assert.assertTrue(tweet.getEntities().getHashTags().contains(hashTag));
                log.debug(msg+" found Tweet: "+tweet.getUniqueId()+" found HashTag: "+hashTag.getUniqueId());
            }
        }
        log.info(msg);
    }

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
            Assert.assertTrue(msg,user.getTaskInfo().getFetchTweetsFromSearch());
            Page<Tweet> foundTweets = tweetService.findTweetsForUser(user,pageRequest);
            Assert.assertNotNull(msg,foundTweets);
            for(Tweet tweet : foundTweets.getContent()) {
                loopTweet++;
                Assert.assertNotNull(msg,tweet.getUser());
                Assert.assertEquals(msg,tweet.getUser().getUniqueId(), user.getUniqueId());
                log.info(msg+" tweet: "+tweet.getUniqueId()+" user: "+tweet.getUser().getUniqueId());
            }
            log.info(msg+" RUNNING TEST. Tested Users "+loopUser+" and Tweets "+loopTweet);
        }
        log.info(msg+" FINISHED TEST. Tested Users "+loopUser+" and Tweets "+loopTweet);
    }

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
                log.info(msg+" tweet: "+foundObject.getUniqueId()+" HashTag: "+foundEntity.getUniqueId());
            }
        }
    }

    @Test
    public void findAllTweet2Media() throws Exception {
        String msg = "findAllTweet2Media: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2Media(pageRequest);
        for(Object2Entity object2Entity:foundPage.getContent()){
            long objectId = object2Entity.getObjectId();
            String objectInfo = object2Entity.getObjectInfo();
            long entityId = object2Entity.getEntityId();
            String entityInfo = object2Entity.getObjectInfo();
            Tweet foundObject = tweetService.findById(objectId);
            Media foundEntity = mediaService.findById(entityId);
            Assert.assertNotNull(msg,foundObject);
            Assert.assertNotNull(msg,foundEntity);
            Assert.assertNull(msg,objectInfo);
            Assert.assertNull(msg,entityInfo);
            Set<Media> media = foundObject.getEntities().getMedia();
            Assert.assertTrue(msg,media.size()>0);
            Assert.assertTrue(msg,media.contains(foundEntity));
            log.info(msg+" tweet: "+foundObject.getUniqueId()+" Media: "+foundEntity.getUniqueId());
        }
    }

    @Test
    public void findAllTweet2Mention() throws Exception {
        String msg = "findAllTweet2Mention: ";
        int page=1;
        int size=20;
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
                Assert.assertNull(msg,objectInfo);
                Assert.assertNull(msg,entityInfo);
                Set<Mention> mentions = foundObject.getEntities().getMentions();
                Assert.assertTrue(msg,mentions.size() >0);
                Assert.assertTrue(msg,mentions.contains(foundEntity));
                log.info(msg+" tweet: "+foundObject.getUniqueId()+" Mention: "+foundEntity.getUniqueId());
            }
        }
    }

    @Test
    public void findAllTweet2Url() throws Exception {
        String msg = "findAllTweet2Url: ";
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = tweetService.findAllTweet2Url(pageRequest);
        for(Object2Entity object2Entity:foundPage.getContent()){
            long objectId = object2Entity.getObjectId();
            String objectInfo = object2Entity.getObjectInfo();
            long entityId = object2Entity.getEntityId();
            String entityInfo = object2Entity.getObjectInfo();
            Tweet foundObject = tweetService.findById(objectId);
            Url foundEntity = urlService.findById(entityId);
            Assert.assertNotNull(msg,foundObject);
            Assert.assertNotNull(msg,foundEntity);
            Assert.assertNull(msg,objectInfo);
            Assert.assertNull(msg,entityInfo);
            Set<Url> urls = foundObject.getEntities().getUrls();
            Assert.assertTrue(msg,urls.size()>0);
            Assert.assertTrue(msg,urls.contains(foundEntity));
            log.info(msg+" tweet: "+foundObject.getUniqueId()+" Url: "+foundEntity.getUniqueId());
        }
    }

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
                log.info(msg+" tweet: "+foundObject.getUniqueId()+" TickerSymbol: "+foundEntity.getUniqueId());
            }
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
