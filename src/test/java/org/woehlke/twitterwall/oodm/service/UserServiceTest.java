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
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class UserServiceTest {

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

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    @Autowired
    private TestdataProperties testdataProperties;

    //@Commit
    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(userService);
        Assert.assertNotNull(testdataProperties);
        Assert.assertNotNull(twitterProperties);
    }

    //@Commit
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

    //@Commit
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

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    /*
    private static String descriptions[] = {
            "Webentwickler @cron_eu, Stuttgart #T3Rookies #TYP",
            "Neos, Flow and TYPO3 development @portachtzig_ Berlin",
            "16.–18. Juni 2017 | DAS TYPO3-Community-Event in Berlin | Bleibt auf dem Laufenden und folgt uns!",
            "Agentur für effiziente Kommunikation und E-Business",
            "Webentwickler",
            "Freelance Frontend developer and TYPO3 integrator. Passionate about punk and ska music. SEGA fanboy.",
            "Webentwickler. Interessiert an Musik, Filmen und Technik",
            "Davitec ist Dienstleister für individuelle Softwareentwicklung und ermöglicht Unternehmen eine erfolgreiche digitale Transformation.",
            "HSV, Musik, TYPO3",
            "Netzwerk von TYPO3-Anwendern in der Ruhrregion und darüber hinaus - monatliche Treffen, gegenseitige Unterstützung und Freude an der Arbeit mit dem CMS TYPO3",
            "#TYPO3 und #Magento Agentur aus Jena • TYPO3 Certified Developer • TYPO3 Silver Member • TYPO3 Specialist • Magento Certified Developer",
            "Age: 43; married; 1 son (Florian) and 1 daughter (Vanessa); Work @Mittwald CM Service",
            "Coding/consulting for @TYPO3, in PHP and Scala, Alumnus of @KITKarlsruhe, Linux user, occasionally doing non-IT stuff.",
            "arndtteunissen ist eine strategische Marken- und Designagentur. Unsere wichtigste Kompetenz besteht in der Entwicklung von Corporate Identity Strategien.",
            "Entwickler @slubdresden. Full-Stack bei @literaturport & @dichterlesen. #AngularJS & #TYPO3. #AvGeek! #hahohe",
            "TYPO3 Developer",
            "TYPO3 Dev, nerds host ;-) and technology addicted from Dresden Germany",
            "yow? (=",
            "Father of two sons, Backend and mobile developer and loving  products...",
            "Webdeveloper bei https://t.co/1KJ6Sdx0jZ #TYPO3 / Youtube: https://t.co/rdYKUVG73s / Videotraining TYPO3 8 LTS: https://t.co/6EBbUNsV75",
            "Beratung | Design | Entwicklung | Redaktion | Schulungen | Betrieb",
            "Mama vom Großen und Kleinen | TYPO3 Active Contributer | Glücklich",
            "Online-Marketing-Berater und Google Partner",
            "Inhaber und Geschäftsführer bei sgalinski Internet Services (Impressum: https://t.co/Hy494B8JlP)",
            "Internet, Intranet, Extranet",
            "TYPO3 Entwickler, Rollenspieler und Mittelalter-Freak",
            "Wer nicht lebt, hat nichts zu befürchten.",
            "TYPO3 Addict, Web-Developer @in2code_de, Münchner, My Blogs: bbq-jungle.de",
            "CEO TYPO3 GmbH",
            "Wir glauben an die Stärke von Bildern. Die Kraft eines Wortes. Und an Fortschritt durch Technologie.",
            "Webdeveloper, UX and UI Expert, TYPO3-Developer",
            "Zu allem 'ne Meinung! Statements & Kommentare. Papa. Info- & Medienjunkie. fotobegeistert & reisefreudig & ein @schnittsteller",
            "Webentwicklung, TYPO3, Online-Kommunikation und was mein Leben sonst noch so hergibt....",
            "Member of TYPO3 Expert Advisory Board, TYPO3 Marketing Team, Magento | web design | content management | secure hosting",
            "#TYPO3 #SCRUM #RE #OKR; Independent Consultant, Trainer, Agile Coach; TYPO3 Expert Advisory Board & Head of TYPO3 Education; https://t.co/E6qwHNXcAh",
    };
    */

    //@Commit
    @Test
    public void getTweetingUsers() throws Exception {
        String msg = "getTweetingUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getTweetingUsers(pageRequest);
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //@Commit
    @Test
    public void getAllDescriptions() throws Exception {
        String msg = "getTweetingUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<String> foundDescriptions = userService.getAllDescriptions(pageRequest);
        log.debug(msg+" foundUser: "+foundDescriptions.getTotalElements());
    }

    //TODO: #160 https://github.com/phasenraum2010/twitterwall2/issues/160
    //@Commit
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

    //@Commit
    @Test
    public void getFriends() throws Exception {
        String msg = "getFriends: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getFriends(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertTrue(user.getFriend());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //@Commit
    @Test
    public void getNotYetFriendUsers() throws Exception {
        String msg = "getNotYetFriendUsers: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getNotYetFriendUsers(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertTrue(!user.getFriend());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //@Commit
    @Test
    public void getFollower() throws Exception {
        String msg = "getFollower: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getFollower(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertTrue(user.getFollower());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //@Commit
    @Test
    public void getNotYetFollower() throws Exception {
        String msg = "getNotYetFollower: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getNotYetFollower(pageRequest);
        for(User user : foundUser.getContent()){
            Assert.assertTrue(!user.getFollower());
        }
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //TODO: #219 https://github.com/phasenraum2010/twitterwall2/issues/219
    //@Commit
    @Test
    public void getOnList() throws Exception {
        String msg = "getOnList: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getOnList(pageRequest);
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //TODO: #219 https://github.com/phasenraum2010/twitterwall2/issues/219
    //@Commit
    @Test
    public void getNotYetOnList() throws Exception {
        String msg = "getNotYetOnList: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<User> foundUser = userService.getNotYetOnList(pageRequest);
        log.debug(msg+" foundUser: "+foundUser.getTotalElements());
    }


    //@Commit
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
            }
        }
    }


    //@Commit
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
            }
        }
    }


    //@Commit
    @Ignore
    @Test
    public void findAllUser2Mentiong() throws Exception {
        String msg = "findAllUser2Mentiong: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2Mentiong(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                User userPers = userService.findById(objectId);
                Mention mentionPers = mentionService.findById(entityId);
                Assert.assertNotNull(msg,userPers);
                Assert.assertNotNull(msg,mentionPers);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                boolean ok = userPers.getEntities().getMentions().contains(mentionPers);
                Assert.assertTrue(msg,ok);
            }
        }
    }


    //@Commit
    @Ignore
    @Test
    public void findAllUser2Url() throws Exception {
        String msg = "findAllUser2Url: ";
        int page=1;
        int size=10;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Object2Entity> foundPage = userService.findAllUser2Url(pageRequest);
        if(foundPage.getTotalElements()>0){
            for(Object2Entity object2Entity:foundPage.getContent()){
                long objectId = object2Entity.getObjectId();
                String objectInfo = object2Entity.getObjectInfo();
                long entityId = object2Entity.getEntityId();
                String entityInfo = object2Entity.getObjectInfo();
                User foundObject = userService.findById(objectId);
                Url foundEntity = urlService.findById(entityId);
                Assert.assertNotNull(msg,foundObject);
                Assert.assertNotNull(msg,foundEntity);
                Assert.assertNull(objectInfo);
                Assert.assertNull(entityInfo);
                boolean ok = foundObject.getEntities().getUrls().contains(foundEntity);
                Assert.assertTrue(msg,ok);
            }
        }
    }


    //@Commit
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
            }
        }
    }
}
