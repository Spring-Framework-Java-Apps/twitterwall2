package org.woehlke.twitterwall.scheduled.service.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.*;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class CountedEntitiesServiceImpl implements CountedEntitiesService {

    private static final Logger log = LoggerFactory.getLogger(CountedEntitiesServiceImpl.class);


    private final TweetService tweetService;

    private final UserService userService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final HashTagService hashTagService;

    private final UrlService urlService;

    private final UrlCacheService urlCacheService;

    private final TickerSymbolService tickerSymbolService;


    public CountedEntitiesServiceImpl(TweetService tweetService, UserService userService, MentionService mentionService, MediaService mediaService, HashTagService hashTagService, UrlService urlService, UrlCacheService urlCacheService, TickerSymbolService tickerSymbolService) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.hashTagService = hashTagService;
        this.urlService = urlService;
        this.urlCacheService = urlCacheService;
        this.tickerSymbolService = tickerSymbolService;
    }


    @Override
    public CountedEntities countAll() {
        CountedEntities c = new CountedEntities();
        c.setCountHashTags(this.hashTagService.count());
        c.setCountMedia(this.mediaService.count());
        c.setCountMention(this.mentionService.count());
        c.setCountTickerSymbol(this.tickerSymbolService.count());
        c.setCountTweets(this.tweetService.count());
        c.setCountUrl(this.urlService.count());
        c.setCountUrlCache(this.urlCacheService.count());
        c.setCountUser(this.userService.count());
        log.debug("countAll: "+c.toString());
        return c;
    }

    @Override
    public long countTweets() {
        return tweetService.count();
    }

    @Override
    public long countUsers() {
        return this.userService.count();
    }
}
