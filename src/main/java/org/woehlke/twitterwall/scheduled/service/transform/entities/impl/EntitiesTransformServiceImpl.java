package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.scheduled.service.transform.entities.*;

import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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
    public Entities getEntitiesForUser(User user) {
        String msg = "getEntitiesForUser: "+user.getScreenName()+" : ";
        String description = user.getDescription();
        Set<Mention> mentions = mentionTransformService.findByUser(user);
        Set<Url> urls = urlTransformService.getUrlsFor(user);
        Set<HashTag> hashTags = hashTagTransformService.getHashTagsFor(user);
        Set<Media> media = mediaTransformService.getMediaFor(user);
        Set<TickerSymbol> tickerSymbols = tickerSymbolTransformService.getTickerSymbolsFor(user);
        Entities entities = new Entities();
        entities.setMentions(mentions);
        entities.addAllUrls(urls);
        entities.setMedia(media);
        entities.setTags(hashTags);
        entities.setTickerSymbols(tickerSymbols);
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+"description " + description);
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+entities.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return entities;
    }

    @Override
    public Entities transform(org.springframework.social.twitter.api.Entities entities) {
        String msg = "transform ";
        Entities myNewEntities = new Entities();
        for(UrlEntity urlEntity:entities.getUrls()){
            Url url = urlTransformService.transform(urlEntity);
            myNewEntities.addUrl(url);
        }
        for(HashTagEntity hashTagEntity:entities.getHashTags()){
            HashTag tag = hashTagTransformService.transform(hashTagEntity);
            myNewEntities.addTag(tag);
        }
        for(MentionEntity mentionEntity:entities.getMentions()){
            Mention mention = mentionTransformService.transform(mentionEntity);
            myNewEntities.addMention(mention);
        }
        for(MediaEntity medium :entities.getMedia()){
            Media media = mediaTransformService.transform(medium);
            myNewEntities.addMedium(media);
        }
        for(TickerSymbolEntity tickerSymbolEntity:entities.getTickerSymbols()) {
            TickerSymbol tickerSymbol = tickerSymbolTransformService.transform(tickerSymbolEntity);
            myNewEntities.addTickerSymbol(tickerSymbol);
        }
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+entities.toString());
        log.debug(msg+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return myNewEntities;
    }
}
