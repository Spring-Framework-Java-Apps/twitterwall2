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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.transients.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.transients.HashTagOverviewPaged;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class HashTagServiceTest {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceTest.class);

    @Autowired
    private HashTagService hashTagService;

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    @Autowired
    private TestdataProperties testdataProperties;


    //@Commit
    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(hashTagService);
        Assert.assertNotNull(testdataProperties);
    }

    //@Commit
    @Test
    public void fetchTestData() throws Exception {
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

    //@Commit
    @Test
    public void findByText() throws Exception {
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
     *
     * @throws Exception
     *
     * @see org.woehlke.twitterwall.oodm.entities.HashTag
     * @see org.woehlke.twitterwall.oodm.entities.parts.Entities
     * @see org.woehlke.twitterwall.oodm.entities.transients.mapper.CountAllTweets2HashTagsRowMapper#SQL_COUNT_ALL_TWEET_2_HASHTAG
     * @see org.woehlke.twitterwall.oodm.entities.transients.mapper.CountAllUsers2HashTagsRowMapper#SQL_COUNT_ALL_USER_2_HASHTAG
     * @see org.woehlke.twitterwall.oodm.entities.transients.HashTagOverviewPaged
     * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllTweet2HashTag(Pageable)
     * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllUser2HashTag(Pageable)
     * @see org.woehlke.twitterwall.oodm.service.impl.HashTagServiceImpl#getHashTagOverview(Pageable, Pageable)
     */
    @Test
    public void getHashTagOverview() throws Exception {
        String msg = "getHashTagOverview: ";
        int page=1;
        int size=30;
        Pageable pageRequestTweets = new PageRequest(page,size);
        Pageable pageRequestUsers = new PageRequest(page,size);
        HashTagOverviewPaged overview = hashTagService.getHashTagOverview(pageRequestTweets,pageRequestUsers);
        Page<HashTagCounted> hashTagsTweets = overview.getHashTagsTweets();
        Page<HashTagCounted> hashTagsUsers = overview.getHashTagsUsers();
        for(HashTagCounted counted:hashTagsTweets){
            log.info(msg+" hashTagsTweets: "+counted.getText());
        }
        for(HashTagCounted counted:hashTagsUsers){
            log.info(msg+" hashTagsUsers: "+counted.getText());
        }
    }
}
