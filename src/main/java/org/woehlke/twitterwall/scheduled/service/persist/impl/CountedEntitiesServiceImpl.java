package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.dao.*;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.Map;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class CountedEntitiesServiceImpl implements CountedEntitiesService {


    @Override
    public AbstractTwitterObject.CountedEntities countAll() {
        AbstractTwitterObject.CountedEntities c = new AbstractTwitterObject.CountedEntities();

        long countUser = this.userDao.count(User.class);
        long countTweets = this.tweetDao.count(Tweet.class);
        long countHashTags = this.hashTagDao.count(HashTag.class);
        long countMedia = this.mediaDao.count(Media.class);
        long countMention = this.mentionDao.count(Mention.class);
        long countTickerSymbol = this.tickerSymbolDao.count(TickerSymbol.class);
        long countUrl = this.urlDao.count(Url.class);
        long countUrlCache = this.urlCacheDao.count(UrlCache.class);
        long countTask = this.taskRepository.count(Task.class);
        long countTaskHistory = this.taskHistoryRepository.count(TaskHistory.class);

        Map<String,Integer> tableCount =  jdbcDao.getTableCount();

        c.setCountHashTags(countHashTags);
        c.setCountMedia(countMedia);
        c.setCountMention(countMention);
        c.setCountTickerSymbol(countTickerSymbol);
        c.setCountTweets(countTweets);
        c.setCountUrl(countUrl);
        c.setCountUrlCache(countUrlCache);
        c.setCountUser(countUser);
        c.setCountTask(countTask);
        c.setCountTaskHistory(countTaskHistory);

        c.setTweet2hashtag(tableCount.get("tweet_hashtag").longValue());
        c.setTweet2media(tableCount.get("tweet_media").longValue());
        c.setTweet2mention(tableCount.get("tweet_mention").longValue());
        c.setTweet2tickersymbol(tableCount.get("tweet_tickersymbol").longValue());
        c.setTweet2url(tableCount.get("tweet_url").longValue());
        c.setUserprofile2hashtag(tableCount.get("userprofile_hashtag").longValue());
        c.setUserprofile2media(tableCount.get("userprofile_media").longValue());
        c.setUserprofile2mention(tableCount.get("userprofile_mention").longValue());
        c.setUserprofile2tickersymbol(tableCount.get("userprofile_tickersymbol").longValue());
        c.setUserprofile2url(tableCount.get("userprofile_url").longValue());

        log.debug("countAll: "+c.toString());
        return c;
    }

    @Override
    public long countTweets() {
        return tweetDao.count(Tweet.class);
    }

    @Override
    public long countUsers() {
        return this.userDao.count(User.class);
    }


    private static final Logger log = LoggerFactory.getLogger(CountedEntitiesServiceImpl.class);

    private final TweetDao tweetDao;

    private final UserDao userDao;

    private final MentionDao mentionDao;

    private final MediaDao mediaDao;

    private final HashTagDao hashTagDao;

    private final UrlDao urlDao;

    private final UrlCacheDao urlCacheDao;

    private final TickerSymbolDao tickerSymbolDao;

    private final TaskDao taskRepository;

    private final TaskHistoryDao taskHistoryRepository;

    private final JdbcDao jdbcDao;


    @Autowired
    public CountedEntitiesServiceImpl(TweetDao tweetDao, UserDao userDao, MentionDao mentionDao, MediaDao mediaDao, HashTagDao hashTagDao, UrlDao urlDao, UrlCacheDao urlCacheDao, TickerSymbolDao tickerSymbolDao, TaskDao taskRepository, TaskHistoryDao taskHistoryRepository, JdbcDao jdbcDao) {
        this.tweetDao = tweetDao;
        this.userDao = userDao;
        this.mentionDao = mentionDao;
        this.mediaDao = mediaDao;
        this.hashTagDao = hashTagDao;
        this.urlDao = urlDao;
        this.urlCacheDao = urlCacheDao;
        this.tickerSymbolDao = tickerSymbolDao;
        this.taskRepository = taskRepository;
        this.taskHistoryRepository = taskHistoryRepository;
        this.jdbcDao = jdbcDao;
    }
}
