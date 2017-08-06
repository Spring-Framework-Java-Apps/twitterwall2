package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.scheduled.service.transform.*;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */
@Component
//@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
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
    public Entities transformEntitiesForUser(TwitterProfile userSource, Task task) {
        String msg = "transformEntitiesForUser: "+userSource.getScreenName()+" : ";
        String description = userSource.getDescription();
        Entities entitiesTarget = new Entities();
        Set<Url> urls = urlTransformService.getUrlsFor(userSource,task);
        Set<HashTag> hashTags = hashTagTransformService.getHashTagsFor(userSource,task);
        Set<Mention> mentions = mentionTransformService.findByUser(userSource,task);
        Set<Media> media = mediaTransformService.getMediaFor(userSource,task);
        Set<TickerSymbol> tickerSymbols = tickerSymbolTransformService.getTickerSymbolsFor(userSource,task);
        entitiesTarget.setMentions(mentions);
        entitiesTarget.addAllUrls(urls);
        entitiesTarget.setMedia(media);
        entitiesTarget.setHashTags(hashTags);
        entitiesTarget.setTickerSymbols(tickerSymbols);
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"description " + description);
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+entitiesTarget.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return entitiesTarget;
    }

    @Override
    public Entities transform(org.springframework.social.twitter.api.Entities entitiesSource, Task task) {
        String msg = "transform ";
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        List<UrlEntity> listUrlEntity =  entitiesSource.getUrls();
        List<HashTagEntity> listHashTagEntity = entitiesSource.getHashTags();
        List<MentionEntity>  listMentionEntity = entitiesSource.getMentions();
        List<MediaEntity> listMediaEntity = entitiesSource.getMedia();
        List<TickerSymbolEntity> listTickerSymbolEntity =  entitiesSource.getTickerSymbols();
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
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"entitiesSource: "+entitiesSource.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"entitiesTarget: "+entitiesTarget.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return entitiesTarget;
    }
}
