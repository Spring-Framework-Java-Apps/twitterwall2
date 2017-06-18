package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.oodm.service.TweetService;

import java.util.Date;


/**
 * Created by tw on 18.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class TweetTest {

    private static final Logger log = LoggerFactory.getLogger(TweetTest.class);

    @Autowired
    private TweetService tweetService;


    @Test
    public void exampleTest() {
        log.info("Hello, Testing-World.");
        Assert.assertTrue(true);
        log.info("------------------------------------------------");
    }

    @Test
    public void testText1() {
        long idTwitter = 876329508009279488L;
        String output = "Neu in <a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/TYPO3\">#TYPO3</a> 8 LTS: <a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/Doctrine\">#Doctrine</a> ein <a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/ORM\">#ORM</a> für PHP nach dem Vorbild  <a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/Hibernate\">#Hibernate</a> in <a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/Java\">#Java</a>  <a href=\"http://www.<a href=\"http://www.doctrine-project.org/\" target=\"_blank\">doctrine-project.org</a> \" target=\"_blank\">doctrine-project.org</a> Kenn… <a href=\"https://twitter.com/i/web/status/876329508009279488\" target=\"_blank\">twitter.com/i/web/status/8…</a> ";
        log.info("idTwitter: "+idTwitter);
        Tweet tweet = tweetService.findByIdTwitter(idTwitter);
        log.info("text:          "+tweet.getText());
        log.info("FormattedText: "+tweet.getFormattedText());
        log.info("output:        "+output);
        String formattedText = tweet.getFormattedText();
        Assert.assertEquals(output,formattedText);
        log.info("------------------------------------------------");
    }

    @Test
    public void testText2() {
        long idTwitter = 876356335784394752L;
        String output = "Twitterwall zum <a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/t3cb\">#t3cb</a> <a href=\"http://<a href=\"http://natural-born-coder.de/\" target=\"_blank\">natural-born-coder.de</a> \" target=\"_blank\">natural-born-coder.de</a> <br/><br/><a class=\"tweet-photo\" href=\"https://twitter.com/port80guru/status/876356335784394752/photo/1\" target=\"_blank\"><img class=\"tweet-photo\" src=\"https://pbs.twimg.com/media/DClxcLmXsAAW1t6.jpg\" /></a> ";
        log.info("idTwitter: "+idTwitter);
        Tweet tweet = tweetService.findByIdTwitter(idTwitter);
        log.info("text:          "+tweet.getText());
        log.info("FormattedText: "+tweet.getFormattedText());
        log.info("output:        "+output);
        String formattedText = tweet.getFormattedText();
        Assert.assertEquals(output,formattedText);
        log.info("------------------------------------------------");
    }
}
