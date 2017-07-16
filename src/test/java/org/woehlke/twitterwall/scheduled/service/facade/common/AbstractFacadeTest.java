package org.woehlke.twitterwall.scheduled.service.facade.common;

import org.junit.Assert;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;

/**
 * Created by tw on 11.07.17.
 */
public abstract class AbstractFacadeTest {

    protected boolean assertCountedEntities(CountedEntities beforeTest, CountedEntities afterTest) {
        boolean result = true;

        boolean resultTask = afterTest.getCountTask() > beforeTest.getCountTask();
        boolean resultTaskHistory = afterTest.getCountTaskHistory() > beforeTest.getCountTaskHistory();
        boolean resultTweets = afterTest.getCountTweets()>=beforeTest.getCountTweets();
        boolean resultUser = afterTest.getCountUser()>=beforeTest.getCountUser();
        boolean resultUrl = afterTest.getCountUrl() >= beforeTest.getCountUrl();
        boolean resultUrlCache = afterTest.getCountUrlCache()>= beforeTest.getCountUrlCache();
        boolean resultHashTags = afterTest.getCountHashTags()>=beforeTest.getCountHashTags();
        boolean resultMedia =  afterTest.getCountMedia()>=beforeTest.getCountMedia();
        boolean resultMention =  afterTest.getCountMention()  >=beforeTest.getCountMention();
        boolean resultTickerSymbol =  afterTest.getCountTickerSymbol() >= beforeTest.getCountTickerSymbol();

        Assert.assertTrue(resultTask);
        Assert.assertTrue(resultTaskHistory);
        Assert.assertTrue(resultTweets);
        Assert.assertTrue(resultUser);
        Assert.assertTrue(resultUrl);
        Assert.assertTrue(resultUrlCache);
        Assert.assertTrue(resultHashTags);
        Assert.assertTrue(resultMedia);
        Assert.assertTrue(resultMention);
        Assert.assertTrue(resultTickerSymbol);

        result = resultTask && resultTaskHistory && resultTweets && resultUser && resultUrl && resultUrlCache && resultHashTags && resultHashTags && resultMedia && resultMention && resultTickerSymbol;

        return result;
    }
}
