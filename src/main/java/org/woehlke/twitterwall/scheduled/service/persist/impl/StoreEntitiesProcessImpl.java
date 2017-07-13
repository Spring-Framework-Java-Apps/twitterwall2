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

    private static final Logger log = LoggerFactory.getLogger(StoreEntitiesProcessImpl.class);

    private final UrlService urlService;

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CreatePersistentUrl createPersistentUrl;

    @Autowired
    public StoreEntitiesProcessImpl(UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl) {
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
    }

    @Override
    public Entities storeEntitiesProcess(Entities entities, Task task, String url) {
        String msg = "storeEntitiesProcess ";

        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> hashTags = new LinkedHashSet<>();
        Set<Mention> mentions = new LinkedHashSet<>();
        Set<Media> media = new LinkedHashSet<>();
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<>();

        /*
        entities.removeAllUrls();
        entities.removeAllTags();
        entities.removeAllMentions();
        entities.removeAllMedia();
        entities.removeAllTickerSymbols();
        */

        /*
        for (Url myUrl : urls) {
            if(url == null) {
                log.debug(msg+"tweet.getUrls() -> url==null");
            } else {
                String urlStr = myUrl.getUrl();
                if(urlStr == null){
                    log.debug(msg+"tweet.getUrls() -> url.getUrl() == null");
                } else {
                    Url urlObj = createPersistentUrl.getPersistentUrlFor(urlStr,task);
                    if((urlObj != null)&&(urlObj.isValid())){
                        entities.addUrl(urlObj);
                    } else {
                        log.debug(msg+"urlService.getPersistentUrlFor("+urlStr+") == null");
                    }
                }
            }
        }*/
        for (Url myUrl : entities.getUrls()) {
            if((myUrl != null)&&(myUrl.isValid())){
                Url urlPers = urlService.store(myUrl, task);
                urls.add(urlPers);
            }
        }
        /*
        if(url!=null){
            Url urlPers = createPersistentUrl.getPersistentUrlFor(url, task);
            if((urlPers != null)&&(urlPers.isValid())){
                entities.addUrl(urlPers);
            }
        }*/
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
        return entities;
    }


}
