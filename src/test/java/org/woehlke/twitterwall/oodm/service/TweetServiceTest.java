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
import org.woehlke.twitterwall.oodm.model.*;
import org.woehlke.twitterwall.oodm.model.transients.Object2Entity;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test000areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(tweetService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void test001fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        log.debug(msg+"START TEST");
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Tweet> myPage = tweetService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            for(Tweet myTweet :myPage.getContent()){
                Assert.assertNotNull(msg,myTweet);
                Assert.assertNotNull(msg,myTweet.getUniqueId());
                log.debug(msg+" found: "+myTweet.getUniqueId());
            }
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
        log.debug(msg+"FINISHED TEST");
    }

    @Commit
    @Test
    public void test020findByIdTwitter() throws Exception {
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
                    log.debug(msg + " found: " + myFoundTweet.getUniqueId());
                }
            }
        } else {
            log.error(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void test030findTweetsForHashTag() throws Exception {
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
        log.debug(msg);
    }

    @Commit
    @Test
    public void test031findTweetsForMedia() throws Exception {
        String msg = "findTweetsForMedia: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Media> mediaList = mediaService.getAll(pageRequest);
        for(Media media:mediaList.getContent()){
            log.debug(msg+" found Media: "+media.getUniqueId());
            Page<Tweet> tweets = tweetService.findTweetsForMedia(media,pageRequest);
            for(Tweet tweet: tweets.getContent()){
                Assert.assertTrue(tweet.getEntities().getMedia().contains(media));
                log.debug(msg+" found Tweet: "+tweet.getUniqueId()+" found Media: "+media.getUniqueId());
            }
        }
        log.debug(msg);
    }

    @Commit
    @Test
    public void test032findTweetsForMention() throws Exception {
        String msg = "findTweetsForMention: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Mention> mentionList = mentionService.getAll(pageRequest);
        for(Mention mention:mentionList.getContent()){
            log.debug(msg+" found Mention: "+mention.getUniqueId());
            Page<Tweet> tweets = tweetService.findTweetsForMention(mention,pageRequest);
            for(Tweet tweet: tweets.getContent()){
                Assert.assertTrue(tweet.getEntities().getMentions().contains(mention));
                log.debug(msg+" found Tweet: "+tweet.getUniqueId()+" found Mention: "+mention.getUniqueId());
            }
        }
        log.debug(msg);
    }

    @Commit
    @Test
    public void test033findTweetsForUrl() throws Exception {
        String msg = "findTweetsForUrl: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Url> urlPage = urlService.getAll(pageRequest);
        for(Url url:urlPage.getContent()){
            log.debug(msg+" found Url: "+url.getUniqueId());
            Page<Tweet> tweets = tweetService.findTweetsForUrl(url,pageRequest);
            for(Tweet tweet: tweets.getContent()){
                Assert.assertTrue(tweet.getEntities().getUrls().contains(url));
                log.debug(msg+" found Tweet: "+tweet.getUniqueId()+" found Url: "+url.getUniqueId());
            }
        }
        log.debug(msg);
    }

    @Commit
    @Test
    public void test034findTweetsForTickerSymbol() throws Exception {
        String msg = "findTweetsForTickerSymbol: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<TickerSymbol> tickerSymbolPage = tickerSymbolService.getAll(pageRequest);
        for(TickerSymbol tickerSymbol:tickerSymbolPage.getContent()){
            log.debug(msg+" found TickerSymbol: "+tickerSymbol.getUniqueId());
            Page<Tweet> tweets = tweetService.findTweetsForTickerSymbol(tickerSymbol,pageRequest);
            for(Tweet tweet: tweets.getContent()){
                Assert.assertTrue(tweet.getEntities().getTickerSymbols().contains(tickerSymbol));
                log.debug(msg+" found Tweet: "+tweet.getUniqueId()+" found TickerSymbol: "+tickerSymbol.getUniqueId());
            }
        }
        log.debug(msg);
    }

    @Commit
    @Test
    public void test035findTweetsForUser() throws Exception {
        String msg = "findTweetsForUser: ";
        int page=1;
        int size=100;
        Pageable pageRequest = new PageRequest(page,size);
        log.debug(msg + "STARTED TEST");
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
                log.debug(msg+" tweet: "+tweet.getUniqueId()+" user: "+tweet.getUser().getUniqueId());
            }
            log.debug(msg+" RUNNING TEST. Tested Users "+loopUser+" and Tweets "+loopTweet);
        }
        log.debug(msg+" FINISHED TEST. Tested Users "+loopUser+" and Tweets "+loopTweet);
    }

    @Commit
    @Test
    public void test036findAllTweet2HashTag() throws Exception {
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
                log.debug(msg+" tweet: "+foundObject.getUniqueId()+" HashTag: "+foundEntity.getUniqueId());
            }
        }
    }

    @Commit
    @Test
    public void test037findAllTweet2Media() throws Exception {
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
            log.debug(msg+" tweet: "+foundObject.getUniqueId()+" Media: "+foundEntity.getUniqueId());
        }
    }

    @Commit
    @Test
    public void test038findAllTweet2Mention() throws Exception {
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
                log.debug(msg+" tweet: "+foundObject.getUniqueId()+" Mention: "+foundEntity.getUniqueId());
            }
        }
    }

    @Commit
    @Test
    public void test039findAllTweet2Url() throws Exception {
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
            log.debug(msg+" tweet: "+foundObject.getUniqueId()+" Url: "+foundEntity.getUniqueId());
        }
    }

    @Commit
    @Test
    public void test040findAllTweet2TickerSymbol() throws Exception {
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
                log.debug(msg+" tweet: "+foundObject.getUniqueId()+" TickerSymbol: "+foundEntity.getUniqueId());
            }
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
}
