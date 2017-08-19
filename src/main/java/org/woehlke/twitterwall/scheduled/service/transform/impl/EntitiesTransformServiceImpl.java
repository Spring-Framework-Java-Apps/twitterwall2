package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.entities.Entities;
import org.woehlke.twitterwall.scheduled.service.transform.*;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */
@Component
public class EntitiesTransformServiceImpl implements EntitiesTransformService {

    private static final Logger log = LoggerFactory.getLogger(EntitiesTransformServiceImpl.class);

    private final UrlTransformService urlTransformService;

    private final HashTagTransformService hashTagTransformService;

    private final MentionTransformService mentionTransformService;

    private final MediaTransformService mediaTransformService;

    private final TickerSymbolTransformService tickerSymbolTransformService;

    @Autowired
    public EntitiesTransformServiceImpl(UrlTransformService urlTransformService, HashTagTransformService hashTagTransformService, MentionTransformService mentionTransformService, MediaTransformService mediaTransformService, TickerSymbolTransformService tickerSymbolTransformService) {
        this.urlTransformService = urlTransformService;
        this.hashTagTransformService = hashTagTransformService;
        this.mentionTransformService = mentionTransformService;
        this.mediaTransformService = mediaTransformService;
        this.tickerSymbolTransformService = tickerSymbolTransformService;
    }

    @Override
    public Entities transformEntitiesForUser(TwitterProfile userFromTwitterApi, Task task) {
        String msg = "transformEntitiesForUser: "+userFromTwitterApi.getScreenName()+" : ";
        String description = userFromTwitterApi.getDescription();
        Entities entitiesTarget = new Entities();
        Set<Url> urls = urlTransformService.getUrlsForTwitterProfile(userFromTwitterApi,task);
        Set<HashTag> hashTags = hashTagTransformService.getHashTagsFor(userFromTwitterApi,task);
        Set<Mention> mentions = mentionTransformService.findByUser(userFromTwitterApi,task);
        Set<Media> media = mediaTransformService.getMediaFor(userFromTwitterApi,task);
        Set<TickerSymbol> tickerSymbols = tickerSymbolTransformService.getTickerSymbolsFor(userFromTwitterApi,task);
        entitiesTarget.setMentions(mentions);
        entitiesTarget.addAllUrls(urls);
        entitiesTarget.setMedia(media);
        entitiesTarget.setHashTags(hashTags);
        entitiesTarget.setTickerSymbols(tickerSymbols);
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"description " + description);
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+entitiesTarget.getUniqueId());
        log.trace(msg+entitiesTarget.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return entitiesTarget;
    }

    @Override
    public Entities transformEntitiesForTweet(Tweet tweetFromTwitterApi, Task task) {
        String msg = "transformEntitiesForTweet ";
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        List<UrlEntity> listUrlEntity =  tweetFromTwitterApi.getEntities().getUrls();
        List<HashTagEntity> listHashTagEntity = tweetFromTwitterApi.getEntities().getHashTags();
        List<MentionEntity>  listMentionEntity = tweetFromTwitterApi.getEntities().getMentions();
        List<MediaEntity> listMediaEntity = tweetFromTwitterApi.getEntities().getMedia();
        List<TickerSymbolEntity> listTickerSymbolEntity =  tweetFromTwitterApi.getEntities().getTickerSymbols();
        log.debug(msg+"listUrlEntity = "+listUrlEntity.size());
        log.debug(msg+"listHashTagEntity = "+listHashTagEntity.size());
        log.debug(msg+"listMentionEntity = "+listMentionEntity.size());
        log.debug(msg+"listMediaEntity = "+listMediaEntity.size());
        log.debug(msg+"listTickerSymbolEntity = "+listTickerSymbolEntity.size());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Entities entitiesTarget = new Entities();
        for(UrlEntity urlEntity: listUrlEntity){
            Url url = urlTransformService.transform(urlEntity,task);
            log.debug(msg+"transformed Url = "+url.getUniqueId());
            entitiesTarget.addUrl(url);
        }
        for(HashTagEntity hashTagEntity:listHashTagEntity){
            HashTag hashTag = hashTagTransformService.transform(hashTagEntity,task);
            log.debug(msg+"transformed HashTag = "+hashTag.getUniqueId());
            entitiesTarget.addHashTag(hashTag);
        }
        for(MentionEntity mentionEntity:listMentionEntity){
            Mention mention = mentionTransformService.transform(mentionEntity,task);
            log.debug(msg+"transformed Mention = "+mention.getUniqueId());
            entitiesTarget.addMention(mention);
        }
        for(MediaEntity medium :listMediaEntity){
            Media media = mediaTransformService.transform(medium,task);
            log.debug(msg+"transformed Media = "+media.getUniqueId());
            entitiesTarget.addMedium(media);
        }
        for(TickerSymbolEntity tickerSymbolEntity:listTickerSymbolEntity) {
            TickerSymbol tickerSymbol = tickerSymbolTransformService.transform(tickerSymbolEntity,task);
            log.debug(msg+"transformed TickerSymbol = "+tickerSymbol.getUniqueId());
            entitiesTarget.addTickerSymbol(tickerSymbol);
        }
        Set<Mention> transformedMentions = entitiesTarget.getMentions();
        Set<Mention> createdMentions = mentionTransformService.findByTweet(tweetFromTwitterApi,task);
        for(Mention createdMention:createdMentions){
            boolean insert = true;
            for(Mention transformedMention:transformedMentions){
                if(createdMention.getScreenNameUnique().compareTo(transformedMention.getScreenNameUnique())==0){
                    insert = false;
                }
            }
            if(insert){
                log.debug(msg+"created Mention = "+createdMention.getUniqueId());
                entitiesTarget.addMention(createdMention);
            }
        }
        Set<Url> transformedUrls = entitiesTarget.getUrls();
        Set<Url> createdUrls = urlTransformService.getUrlsForTweet(tweetFromTwitterApi,task);
        for(Url createdUrl:createdUrls){
            boolean insert = true;
            for(Url transformedUrl:transformedUrls){
                if(createdUrl.getUrl().compareTo(transformedUrl.getUrl())==0){
                    insert = false;
                }
            }
            if(insert){
                log.debug(msg+"created Url = "+createdUrl.getUniqueId());
                entitiesTarget.addUrl(createdUrl);
            }
        }
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"TweetId:        "+tweetFromTwitterApi.getId());
        log.debug(msg+"entitiesSource: "+tweetFromTwitterApi.getEntities().toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"entitiesTarget: "+entitiesTarget.getUniqueId());
        log.trace(msg+"entitiesTarget: "+entitiesTarget.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return entitiesTarget;
    }
}
