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
import org.woehlke.twitterwall.oodm.model.HashTag;
import org.woehlke.twitterwall.oodm.model.entities.Entities;
import org.woehlke.twitterwall.oodm.model.transients.HashTagCounted;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HashTagServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceTest.class);

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void test000areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(hashTagService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void test001fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<HashTag> myPage = hashTagService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            HashTag myHashTag = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myHashTag);
            Assert.assertNotNull(msg,myHashTag.getText());
            log.debug(msg+" found: "+myHashTag.getText());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void test002findByText() throws Exception {
        String msg = "findByText: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<HashTag> myPage = hashTagService.getAll(pageRequest);
        HashTag myHashTag = myPage.getContent().iterator().next();
        String myHashTagText = myHashTag.getText();
        HashTag myHashTagResult = hashTagService.findByText(myHashTagText);
        Assert.assertEquals(myHashTag.getId(),myHashTagResult.getId());
        Assert.assertEquals(myHashTag.getUniqueId(),myHashTagResult.getUniqueId());
        Assert.assertEquals(myHashTag.getUniqueId(),myHashTagResult.getUniqueId());
        log.debug(msg+" found: "+myHashTagResult.getText());
    }

    /**
     * @throws Exception
     *
     * @see org.woehlke.twitterwall.oodm.model.HashTag
     * @see Entities
     * @see org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllTweets2HashTagsRowMapper#SQL_COUNT_ALL_TWEET_2_HASHTAG
     * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllTweet2HashTag(Pageable)
     * @see org.woehlke.twitterwall.oodm.service.impl.HashTagServiceImpl#getHashTagsTweets(Pageable)
     */
    @Commit
    @Test
    public void test003getHashTagsTweets() throws Exception {
        String msg = "getHashTagsTweets: ";
        int page=1;
        int size=30;
        Pageable pageRequestTweets = new PageRequest(page,size);
        Page<HashTagCounted> hashTagsTweets = hashTagService.getHashTagsTweets(pageRequestTweets);
        for(HashTagCounted counted:hashTagsTweets){
            log.debug(msg+" hashTagsTweets: "+counted.getText());
        }
    }

    /**
     * @throws Exception
     *
     * @see org.woehlke.twitterwall.oodm.model.HashTag
     * @see Entities
     * @see org.woehlke.twitterwall.oodm.model.transients.mapper.CountAllUsers2HashTagsRowMapper#SQL_COUNT_ALL_USER_2_HASHTAG
     * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllUser2HashTag(Pageable)
     * @see org.woehlke.twitterwall.oodm.service.impl.HashTagServiceImpl#getHashTagsUsers(Pageable)
     */
    @Commit
    @Test
    public void test004getHashTagsUsers() throws Exception {
        String msg = "getHashTagsUsers: ";
        int page=1;
        int size=30;
        Pageable pageRequestUsers = new PageRequest(page,size);
        Page<HashTagCounted> hashTagsUsers = hashTagService.getHashTagsUsers(pageRequestUsers);
        for(HashTagCounted counted:hashTagsUsers){
            log.debug(msg+" hashTagsUsers: "+counted.getText());
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
