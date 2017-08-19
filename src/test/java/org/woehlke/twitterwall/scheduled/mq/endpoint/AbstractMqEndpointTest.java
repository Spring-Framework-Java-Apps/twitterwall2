package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.junit.Assert;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;

public abstract class AbstractMqEndpointTest {

    protected boolean assertCountedEntities(CountedEntities beforeTest, CountedEntities afterTest) {

        boolean resultTask = afterTest.getCountTask() > beforeTest.getCountTask();
        boolean resultTaskHistory = afterTest.getCountTaskHistory() > beforeTest.getCountTaskHistory();
        boolean resultTweets = afterTest.getCountTweets()>=beforeTest.getCountTweets();
        boolean resultUser = afterTest.getCountUser()>=beforeTest.getCountUser();
        boolean resultUrl = afterTest.getCountUrl() >= beforeTest.getCountUrl();
        boolean resultHashTags = afterTest.getCountHashTags()>=beforeTest.getCountHashTags();
        boolean resultMedia =  afterTest.getCountMedia()>=beforeTest.getCountMedia();
        boolean resultMention =  afterTest.getCountMention()  >=beforeTest.getCountMention();
        boolean resultTickerSymbol =  afterTest.getCountTickerSymbol() >= beforeTest.getCountTickerSymbol();

        Assert.assertTrue(resultTask);
        Assert.assertTrue(resultTaskHistory);
        Assert.assertTrue(resultTweets);
        Assert.assertTrue(resultUser);
        Assert.assertTrue(resultUrl);
        Assert.assertTrue(resultHashTags);
        Assert.assertTrue(resultMedia);
        Assert.assertTrue(resultMention);
        Assert.assertTrue(resultTickerSymbol);

        boolean result = resultTask && resultTaskHistory && resultTweets && resultUser && resultUrl && resultHashTags && resultHashTags && resultMedia && resultMention && resultTickerSymbol;

        return result;
    }

    protected boolean assertCountedEntitiesReduced(CountedEntities beforeTest, CountedEntities afterTest) {

        boolean resultTask = afterTest.getCountTask() < beforeTest.getCountTask();
        boolean resultTaskHistory = afterTest.getCountTaskHistory() < beforeTest.getCountTaskHistory();
        boolean resultTweets = afterTest.getCountTweets()<=beforeTest.getCountTweets();
        boolean resultUser = afterTest.getCountUser()<=beforeTest.getCountUser();
        boolean resultUrl = afterTest.getCountUrl() <= beforeTest.getCountUrl();
        boolean resultHashTags = afterTest.getCountHashTags()<=beforeTest.getCountHashTags();
        boolean resultMedia =  afterTest.getCountMedia()<=beforeTest.getCountMedia();
        boolean resultMention =  afterTest.getCountMention()  <=beforeTest.getCountMention();
        boolean resultTickerSymbol =  afterTest.getCountTickerSymbol() <= beforeTest.getCountTickerSymbol();

        Assert.assertTrue(resultTask);
        Assert.assertTrue(resultTaskHistory);
        Assert.assertTrue(resultTweets);
        Assert.assertTrue(resultUser);
        Assert.assertTrue(resultUrl);
        Assert.assertTrue(resultHashTags);
        Assert.assertTrue(resultMedia);
        Assert.assertTrue(resultMention);
        Assert.assertTrue(resultTickerSymbol);

        boolean result = resultTask && resultTaskHistory && resultTweets && resultUser && resultUrl && resultHashTags && resultHashTags && resultMedia && resultMention && resultTickerSymbol;

        return result;
    }
}
