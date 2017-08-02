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
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.entities.Tweet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class TweetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceTest.class);

    @Autowired
    private TweetService tweetService;

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
            log.debug(msg+" found: "+myTweet.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void findByIdTwitter() throws Exception {

    }

    @Commit
    @Test
    public void findTweetsForHashTag() throws Exception {

    }

    @Commit
    @Test
    public void findTweetsForUser() throws Exception {

    }

    @Commit
    @Test
    public void findAllTweet2HashTag() throws Exception {

    }

    @Commit
    @Test
    public void findAllTweet2Media() throws Exception {

    }

    @Commit
    @Test
    public void findAllTweet2Mention() throws Exception {

    }

    @Commit
    @Test
    public void findAllTweet2Url() throws Exception {

    }

    @Commit
    @Test
    public void findAllTweet2TickerSymbol() throws Exception {

    }

}
