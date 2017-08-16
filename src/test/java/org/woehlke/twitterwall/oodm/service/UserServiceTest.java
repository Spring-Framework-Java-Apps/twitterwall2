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
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;

import java.util.Set;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

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
    private TwitterProperties twitterProperties;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void areDependenciesLoaded() throws Exception {
        String msg = "areDependenciesLoaded: ";
        Assert.assertNotNull(userService);
        Assert.assertNotNull(testdataProperties);
        Assert.assertNotNull(twitterProperties);
        log.debug(msg+" YES ");
    }

    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> myPage = userService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            User myUser = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myUser);
            Assert.assertNotNull(msg,myUser.getUniqueId());
            log.debug(msg+" found: "+myUser.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    public void getAllDescriptionsTest() {
        String msg = "getAllDescriptionsTest";
        log.info(msg+"------------------------------------------------");
        boolean hasNext;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        do {
            Page<String> descriptions = userService.getAllDescriptions(pageRequest);
            hasNext = descriptions.hasNext();
            long totalNumber = descriptions.getTotalElements();
            int number = descriptions.getNumber();
            log.info(msg+"found "+number+" descriptions (total: "+totalNumber+")");
            for(String description:descriptions){
                log.info(msg+"description: "+description);
            }
            pageRequest = pageRequest.next();
        } while (hasNext);
        String message = "userService.findAllDescriptions(); ";
        Assert.assertTrue(message,true);
        log.info(msg+"------------------------------------------------");
    }

    @Test
    public void getTweetingUsers() throws Exception {
        String msg = "getTweetingUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getTweetingUsers(pageRequest);
        for(User user:foundUser.getContent()){
            Assert.assertTrue(msg,user.getTaskInfo().getFetchTweetsFromSearch());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void getAllDescriptions() throws Exception {
        String msg = "getTweetingUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<String> foundDescriptions = userService.getAllDescriptions(pageRequest);
        for(String description:foundDescriptions){
            Assert.assertNotNull(description);
            log.debug(msg+" description: "+description);
        }
        log.debug(msg+" foundUser: "+foundDescriptions.getTotalElements());
    }

    @Test
    public void getUsersForHashTag() throws Exception {
        String msg = "getTweetingUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<HashTag> hashTags = hashTagService.getAll(pageRequest);
        for(HashTag hashTag:hashTags.getContent()){
            log.debug(msg+" found HashTag: "+hashTag.getUniqueId());
            Page<User> users = userService.getUsersForHashTag(hashTag,pageRequest);
            for(User user: users.getContent()){
                log.debug(msg+" found User: "+user.getUniqueId());
                Assert.assertTrue(user.getEntities().getHashTags().contains(hashTag));
            }
        }
    }

    @Test
    public void getFriends() throws Exception {
        String msg = "getFriends: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getFriends(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertTrue(user.getFriend());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void getNotYetFriendUsers() throws Exception {
        String msg = "getNotYetFriendUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getNotYetFriendUsers(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertFalse(user.getFriend());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void getFollower() throws Exception {
        String msg = "getFollower: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getFollower(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertTrue(user.getTaskInfo().getFetchFollower());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void getNotYetFollower() throws Exception {
        String msg = "getNotYetFollower: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getNotYetFollower(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertFalse(user.getTaskInfo().getFetchFollower());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void getOnList() throws Exception {
        String msg = "getOnList: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getOnList(pageRequest);
        for(User user:foundUser.getContent()){
            Assert.assertTrue(msg,user.getTaskInfo().getFetchUsersFromList());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void getNotYetOnList() throws Exception {
        String msg = "getNotYetOnList: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getNotYetOnList(pageRequest);
        for(User user:foundUser.getContent()){
            Assert.assertTrue(msg,user.getTaskInfo().getFetchTweetsFromSearch());
            Assert.assertFalse(msg,user.getTaskInfo().getFetchUsersFromList());
            log.debug(msg+" foundUser: "+user.getUniqueId());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }

    @Test
    public void findAllUser2HashTag() throws Exception {
        String msg = "findAllUser2HashTag: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2HashTag(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                User foundObject = userService.findById(objectId);
                HashTag foundEntity = hashTagService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getHashTags().contains(foundEntity));
                log.debug(msg+" found User: "+foundObject.getUniqueId()+" found HashTag: "+foundEntity.getUniqueId());
            }
        }
    }

    @Test
    public void findAllUser2Media() throws Exception {
        String msg = "findAllUser2Media: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2Media(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                User foundObject = userService.findById(objectId);
                Media foundEntity = mediaService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getMedia().contains(foundEntity));
                log.debug(msg+" found User: "+foundObject.getUniqueId()+" found Media: "+foundEntity.getUniqueId());
            }
        }
    }

    @Test
    public void findAllUser2Mentiong() throws Exception {
        String msg = "findAllUser2Mentiong: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2Mentiong(pageRequest);
        for(Object2Entity object2Entity:foundPage.getContent()){
            long objectId = object2Entity.getObjectId();
            log.info(msg+" objectId: "+objectId);
            String objectInfo = object2Entity.getObjectInfo();
            log.info(msg+" objectInfo: "+objectInfo);
            long entityId = object2Entity.getEntityId();
            log.info(msg+" entityId: "+entityId);
            String entityInfo = object2Entity.getObjectInfo();
            log.info(msg+" entityInfo: "+entityInfo);
            User userPers = userService.findById(objectId);
            log.info(msg+" userPers: "+userPers);
            Mention mentionPers = mentionService.findById(entityId);
            log.info(msg+" mentionPers: "+mentionPers);
            Assert.assertNotNull(msg+" userPers: ",userPers);
            Assert.assertNotNull(msg+" mentionPers: ",mentionPers);
            Assert.assertNull(msg+" objectInfo: " ,objectInfo);
            Assert.assertNull(msg+" entityInfo: ",entityInfo);
            Set<Mention> mentions = userPers.getEntities().getMentions();
            Assert.assertTrue(msg,mentions.size()>0);
            boolean ok = mentions.contains(mentionPers);
            Assert.assertTrue(msg,ok);
            log.debug(msg+" found User: "+userPers.getUniqueId()+" found Mention: "+mentionPers.getUniqueId());
        }
    }

    @Test
    public void findAllUser2Url() throws Exception {
        String msg = "findAllUser2Url: ";
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2Url(pageRequest);
        for(Object2Entity object2Entity:foundPage.getContent()){
            long objectId = object2Entity.getObjectId();
            String objectInfo = object2Entity.getObjectInfo();
            long entityId = object2Entity.getEntityId();
            String entityInfo = object2Entity.getObjectInfo();
            User foundObject = userService.findById(objectId);
            Url foundEntity = urlService.findById(entityId);
            Assert.assertNotNull(msg,foundObject);
            Assert.assertNotNull(msg,foundEntity);
            Assert.assertNull(msg,objectInfo);
            Assert.assertNull(msg,entityInfo);
            Set<Url> urls = foundObject.getEntities().getUrls();
            Assert.assertTrue(msg,urls.size()>0);
            boolean ok = urls.contains(foundEntity);
            Assert.assertTrue(msg,ok);
            log.debug(msg+" found User: "+foundObject.getUniqueId()+" found Url: "+foundEntity.getUniqueId());
        }
    }

    @Test
    public void findAllUser2TickerSymbol() throws Exception {
        String msg = "findAllUser2TickerSymbol: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2TickerSymbol(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                User foundObject = userService.findById(objectId);
                TickerSymbol foundEntity = tickerSymbolService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                Assert.assertTrue(msg,foundObject.getEntities().getTickerSymbols().contains(foundEntity));
                log.debug(msg+" found User: "+foundObject.getUniqueId()+" found TickerSymbol: "+foundEntity.getUniqueId());
            }
        }
    }

    @Test
    public void findByidTwitterAndScreenNameUnique() throws Exception {
        String msg = "findByidTwitterAndScreenNameUnique: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> allUsersPage = userService.getAll(pageRequest);
        for(User expectedUser:allUsersPage.getContent()){
            long idTwitter = expectedUser.getIdTwitter();
            String screenNameUnique = expectedUser.getScreenNameUnique();
            User foundUser = userService.findByidTwitterAndScreenNameUnique(idTwitter,screenNameUnique);
            Assert.assertEquals(msg,expectedUser.getUniqueId(),foundUser.getUniqueId());
            Assert.assertEquals(msg,idTwitter,foundUser.getIdTwitter().longValue());
            Assert.assertEquals(msg,screenNameUnique,foundUser.getScreenNameUnique());
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
