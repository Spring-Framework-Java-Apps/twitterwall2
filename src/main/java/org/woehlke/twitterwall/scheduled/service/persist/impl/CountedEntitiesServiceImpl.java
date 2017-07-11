package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.entities.application.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.repository.TweetRepository;
import org.woehlke.twitterwall.oodm.repository.UserRepository;
import org.woehlke.twitterwall.oodm.repository.application.TaskHistoryRepository;
import org.woehlke.twitterwall.oodm.repository.application.TaskRepository;
import org.woehlke.twitterwall.oodm.repository.entities.*;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class CountedEntitiesServiceImpl implements CountedEntitiesService {

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

    @Override
    public CountedEntities countAll() {
        CountedEntities c = new CountedEntities();

        long countUser = this.userRepository.count(User.class);
        long countTweets = this.tweetRepository.count(Tweet.class);
        long countHashTags = this.hashTagRepository.count(HashTag.class);
        long countMedia = this.mediaRepository.count(Media.class);
        long countMention = this.mentionRepository.count(Mention.class);
        long countTickerSymbol = this.tickerSymbolRepository.count(TickerSymbol.class);
        long countUrl = this.urlRepository.count(Url.class);
        long countUrlCache = this.urlCacheRepository.count(UrlCache.class);
        long countTask = this.taskRepository.count(Task.class);
        long countTaskHistory = this.taskHistoryRepository.count(TaskHistory.class);

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
        log.debug("countAll: "+c.toString());
        return c;
    }

    @Override
    public long countTweets() {
        return tweetRepository.count(Tweet.class);
    }

    @Override
    public long countUsers() {
        return this.userRepository.count(User.class);
    }
}