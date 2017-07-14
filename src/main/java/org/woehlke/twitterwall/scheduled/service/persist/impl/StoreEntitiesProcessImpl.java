package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.service.entities.*;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentMention;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;
import org.woehlke.twitterwall.scheduled.service.persist.StoreEntitiesProcess;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreEntitiesProcessImpl implements StoreEntitiesProcess {


    @Override
    public Entities storeEntitiesProcess(Entities entities, Task task) {
        String msg = "storeEntitiesProcess ";
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> hashTags = new LinkedHashSet<>();
        Set<Mention> mentions = new LinkedHashSet<>();
        Set<Media> media = new LinkedHashSet<>();
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<>();
        for (Url myUrl : entities.getUrls()) {
            if((myUrl != null)&&(myUrl.isValid())){
                Url urlPers = urlService.store(myUrl, task);
                urls.add(urlPers);
            } else if((myUrl != null)&&(myUrl.isRawUrlsFromDescription())){
                String urlStr = myUrl.getUrl();
                Url urlObj = createPersistentUrl.getPersistentUrlFor(urlStr,task);
                if((urlObj != null)&&(urlObj.isValid())){
                    urls.add(urlObj);
                }
            }
        }
        for (HashTag hashTag : entities.getTags()) {
            if(hashTag.isValid()){
                HashTag hashTagPers = hashTagService.store(hashTag, task);
                hashTags.add(hashTagPers);
            }
        }
        for (Mention mention : entities.getMentions()) {
            if(mention.isValid()){
                Mention mentionPers =mentionService.store(mention, task);
                mentions.add(mentionPers);
            } else if(mention.isRawMentionFromUserDescription()){
                Mention mentionPers = createPersistentMention.getPersistentMentionAndUserFor(mention,task);
                if((mention != null) && mentionPers.isValid()){
                    mentions.add(mentionPers);
                }
            }
        }
        for(Media medium:entities.getMedia()){
            if(medium.isValid()) {
                Media mediumPers = mediaService.store(medium, task);
                media.add(mediumPers);
            }
        }
        for(TickerSymbol tickerSymbol:entities.getTickerSymbols()){
            if(tickerSymbol.isValid()){
                TickerSymbol tickerSymbolPers = tickerSymbolService.store(tickerSymbol,task);
                tickerSymbols.add(tickerSymbolPers);
            }
        }
        entities.setUrls(urls);
        entities.setTags(hashTags);
        entities.setMentions(mentions);
        entities.setMedia(media);
        entities.setTickerSymbols(tickerSymbols);
        return entities;
    }


    private static final Logger log = LoggerFactory.getLogger(StoreEntitiesProcessImpl.class);

    private final UrlService urlService;

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CreatePersistentUrl createPersistentUrl;

    private final CreatePersistentMention createPersistentMention;

    @Autowired
    public StoreEntitiesProcessImpl(UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl, CreatePersistentMention createPersistentMention) {
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
        this.createPersistentMention = createPersistentMention;
    }

}
