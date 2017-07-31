package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.repositories.*;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;


/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class CountedEntitiesServiceImpl implements CountedEntitiesService {


    @Override
    public CountedEntities countAll() {
        String msg = "countAll: ";
        CountedEntities c = new CountedEntities();

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

        c.setTweet2hashtag(tweetRepository.countAllUser2HashTag());
        c.setTweet2media(tweetRepository.countAllUser2Media());
        c.setTweet2mention(tweetRepository.countAllUser2Mention());
        c.setTweet2tickersymbol(tweetRepository.countAllUser2TickerSymbol());
        c.setTweet2url(tweetRepository.countAllUser2Url());

        c.setUserprofile2hashtag(userRepository.countAllUser2HashTag());
        c.setUserprofile2media(userRepository.countAllUser2Media());
        c.setUserprofile2mention(userRepository.countAllUser2Mention());
        c.setUserprofile2tickersymbol(userRepository.countAllUser2TickerSymbol());
        c.setUserprofile2url(userRepository.countAllUser2Url());

        log.debug(msg+c.toString());
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

    private final TweetRepository tweetRepository;

    private final UserRepository userRepository;

    private final MentionRepository mentionRepository;

    private final MediaRepository mediaRepository;

    private final HashTagRepository hashTagRepository;

    private final UrlRepository urlRepository;

    private final UrlCacheRepository urlCacheRepository;

    private final TickerSymbolRepository tickerSymbolRepository;

    private final TaskRepository taskRepository;

    private final TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public CountedEntitiesServiceImpl(TweetRepository tweetRepository, UserRepository userRepository, MentionRepository mentionRepository, MediaRepository mediaRepository, HashTagRepository hashTagRepository, UrlRepository urlRepository, UrlCacheRepository urlCacheRepository, TickerSymbolRepository tickerSymbolRepository, TaskRepository taskRepository, TaskHistoryRepository taskHistoryRepository) {
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
    }

}
