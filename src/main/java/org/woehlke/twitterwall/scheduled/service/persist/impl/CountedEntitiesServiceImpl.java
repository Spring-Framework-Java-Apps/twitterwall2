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
import org.woehlke.twitterwall.oodm.repositories.*;
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

        /*
        long countUser = this.userDao.count(User.class);
        long countTweets = this.tweetDao.count(Tweet.class);
        long countHashTags = this.hashTagDao.count(HashTag.class);
        long countMedia = this.mediaDao.count(Media.class);
        long countMention = this.mentionDao.count(Mention.class);
        long countTickerSymbol = this.tickerSymbolDao.count(TickerSymbol.class);
        long countUrl = this.urlDao.count(Url.class);
        long countUrlCache = this.urlCacheDao.count(UrlCache.class);
        long countTask = this.taskDao.count(Task.class);
        long countTaskHistory = this.taskHistoryDao.count(TaskHistory.class);
        */

        long countUser = userRepository.count();
        long countTweets = tweetRepository.count();
        long countHashTags = hashTagRepository.count();
        long countMedia = mediaRepository.count();
        long countMention = mentionRepository.count();
        long countTickerSymbol = tickerSymbolRepository.count();
        long countUrl = urlRepository.count();
        long countUrlCache = urlCacheRepository.count();
        long countTask = taskRepository.count();
        long countTaskHistory = taskHistoryRepository.count();

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
        return tweetRepository.count();
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    private static final Logger log = LoggerFactory.getLogger(CountedEntitiesServiceImpl.class);

    //private final TweetDao tweetDao;

    private final TweetRepository tweetRepository;

    //private final UserDao userDao;

    private final UserRepository userRepository;

    //private final MentionDao mentionDao;

    private final MentionRepository mentionRepository;

    //private final MediaDao mediaDao;

    private final MediaRepository mediaRepository;

    //private final HashTagDao hashTagDao;

    private final HashTagRepository hashTagRepository;

    //private final UrlDao urlDao;

    private final UrlRepository urlRepository;

    //private final UrlCacheDao urlCacheDao;

    private final UrlCacheRepository urlCacheRepository;

    //private final TickerSymbolDao tickerSymbolDao;

    private final TickerSymbolRepository tickerSymbolRepository;

    //private final TaskDao taskDao;

    private final TaskRepository taskRepository;

    //private final TaskHistoryDao taskHistoryDao;

    private final TaskHistoryRepository taskHistoryRepository;

    private final JdbcDao jdbcDao;

    @Autowired
    public CountedEntitiesServiceImpl(TweetRepository tweetRepository, UserRepository userRepository, MentionRepository mentionRepository, MediaRepository mediaRepository, HashTagRepository hashTagRepository, UrlRepository urlRepository, UrlCacheRepository urlCacheRepository, TickerSymbolRepository tickerSymbolRepository, TaskRepository taskRepository, TaskHistoryRepository taskHistoryRepository, JdbcDao jdbcDao) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.mentionRepository = mentionRepository;
        this.mediaRepository = mediaRepository;
        this.hashTagRepository = hashTagRepository;
        this.urlRepository = urlRepository;
        this.urlCacheRepository = urlCacheRepository;
        this.tickerSymbolRepository = tickerSymbolRepository;
        this.taskRepository = taskRepository;
        this.taskHistoryRepository = taskHistoryRepository;
        this.jdbcDao = jdbcDao;
    }

    /*
    @Autowired
    public CountedEntitiesServiceImpl(TweetDao tweetDao, TweetRepository tweetRepository, UserDao userDao, UserRepository userRepository, MentionDao mentionDao, MentionRepository mentionRepository, MediaDao mediaDao, MediaRepository mediaRepository, HashTagDao hashTagDao, HashTagRepository hashTagRepository, UrlDao urlDao, UrlRepository urlRepository, UrlCacheDao urlCacheDao, UrlCacheRepository urlCacheRepository, TickerSymbolDao tickerSymbolDao, TickerSymbolRepository tickerSymbolRepository, TaskDao taskDao, TaskRepository taskRepository, TaskHistoryDao taskHistoryDao, TaskHistoryRepository taskHistoryRepository, JdbcDao jdbcDao) {
        this.tweetDao = tweetDao;
        this.tweetRepository = tweetRepository;
        this.userDao = userDao;
        this.userRepository = userRepository;
        this.mentionDao = mentionDao;
        this.mentionRepository = mentionRepository;
        this.mediaDao = mediaDao;
        this.mediaRepository = mediaRepository;
        this.hashTagDao = hashTagDao;
        this.hashTagRepository = hashTagRepository;
        this.urlDao = urlDao;
        this.urlRepository = urlRepository;
        this.urlCacheDao = urlCacheDao;
        this.urlCacheRepository = urlCacheRepository;
        this.tickerSymbolDao = tickerSymbolDao;
        this.tickerSymbolRepository = tickerSymbolRepository;
        this.taskDao = taskDao;
        this.taskRepository = taskRepository;
        this.taskHistoryDao = taskHistoryDao;
        this.taskHistoryRepository = taskHistoryRepository;
        this.jdbcDao = jdbcDao;
    }
    */
}
