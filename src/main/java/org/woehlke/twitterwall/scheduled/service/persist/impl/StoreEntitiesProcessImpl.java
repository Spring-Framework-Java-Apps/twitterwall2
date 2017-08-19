package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.*;
import org.woehlke.twitterwall.oodm.model.entities.Entities;
import org.woehlke.twitterwall.oodm.service.*;
import org.woehlke.twitterwall.scheduled.service.persist.StoreEntitiesProcess;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */

@Component
public class StoreEntitiesProcessImpl implements StoreEntitiesProcess {

    @Override
    public Entities storeEntitiesProcessForTweet(Tweet tweet, Task task) {
        Entities entities = tweet.getEntities();
        entities = storeEntitiesProcess(entities,task);
        return entities;
    }

    @Override
    public Entities storeEntitiesProcessForUser(User user, Task task) {
        Entities entities =  user.getEntities();
        entities = storeEntitiesProcess(entities,task);
        return entities;
    }

    @Override
    public Entities updateEntitiesForUserProcess(User user, Task task) {
        long userId = user.getId();
        long userIdTwitter = user.getIdTwitter();
        Set<Mention> newMentions = new HashSet<>();
        Set<Mention> mentions = user.getEntities().getMentions();
        for(Mention mention:mentions){
            mention.setIdTwitterOfUser(userIdTwitter);
            mention.setIdOfUser(userId);
            mention = mentionService.store(mention,task);
            newMentions.add(mention);
        }
        user.getEntities().removeAllMentions();
        user.getEntities().addAllMentions(newMentions);
        return user.getEntities();
    }

    @Override
    public Entities storeEntitiesProcess(Entities entities, Task task) {
        String msg = "storeEntitiesProcess "+task.getUniqueId()+" : ";
        try {
            Set<Url> urls = new LinkedHashSet<>();
            Set<HashTag> hashTags = new LinkedHashSet<HashTag>();
            Set<Mention> mentions = new LinkedHashSet<Mention>();
            Set<Media> media = new LinkedHashSet<Media>();
            Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
            for (Url myUrl : entities.getUrls()) {
                if (myUrl.isValid()) {
                    Url urlPers = urlService.store(myUrl,task);
                    if(urlPers != null) {
                        urls.add(urlPers);
                    }
                }
            }
            for (HashTag hashTag : entities.getHashTags()) {
                if (hashTag.isValid()) {
                    HashTag hashTagPers = hashTagService.store(hashTag, task);
                    if (hashTagPers != null) {
                        hashTags.add(hashTagPers);
                    }
                }
            }
            for (Mention mention : entities.getMentions()) {
                if (mention.isValid()) {
                    Mention mentionPers = mentionService.store(mention, task);
                    if(mentionPers!= null){
                        mentions.add(mentionPers);
                    }
                }
            }
            for (Media medium : entities.getMedia()) {
                if (medium.isValid()) {
                    Media mediumPers = mediaService.store(medium, task);
                    if (mediumPers != null) {
                        media.add(mediumPers);
                    }
                }
            }
            for (TickerSymbol tickerSymbol : entities.getTickerSymbols()) {
                if (tickerSymbol.isValid()) {
                    TickerSymbol tickerSymbolPers = tickerSymbolService.store(tickerSymbol, task);
                    if(tickerSymbolPers != null){
                        tickerSymbols.add(tickerSymbolPers);
                    }
                }
            }
            entities.setUrls(urls);
            entities.setHashTags(hashTags);
            entities.setMentions(mentions);
            entities.setMedia(media);
            entities.setTickerSymbols(tickerSymbols);
        } catch (Exception e){
            log.error(msg+e.getMessage());
        }
        return entities;
    }


    private static final Logger log = LoggerFactory.getLogger(StoreEntitiesProcessImpl.class);

    private final UrlService urlService;

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    @Autowired
    public StoreEntitiesProcessImpl(UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService) {
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
    }

}
