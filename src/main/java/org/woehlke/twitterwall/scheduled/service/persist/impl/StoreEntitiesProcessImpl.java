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
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;
import org.woehlke.twitterwall.oodm.service.entities.MediaService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;
import org.woehlke.twitterwall.oodm.service.entities.TickerSymbolService;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;
import org.woehlke.twitterwall.scheduled.service.persist.StoreEntitiesProcess;

import java.util.Set;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreEntitiesProcessImpl implements StoreEntitiesProcess {

    private static final Logger log = LoggerFactory.getLogger(StoreEntitiesProcessImpl.class);

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CreatePersistentUrl createPersistentUrl;

    @Autowired
    public StoreEntitiesProcessImpl(HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl) {
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
    }

    @Override
    public Entities storeEntitiesProcess(Entities entities, Task task, String url) {
        String msg = "storeEntitiesProcess ";

        Set<Url> urls = entities.getUrls();
        Set<HashTag> hashTags = entities.getTags();
        Set<Mention> mentions = entities.getMentions();
        Set<Media> media = entities.getMedia();
        Set<TickerSymbol> tickerSymbols = entities.getTickerSymbols();

        entities.removeAllUrls();
        entities.removeAllTags();
        entities.removeAllMentions();
        entities.removeAllMedia();
        entities.removeAllTickerSymbols();

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
        }
        if(url!=null){
            Url urlPers = createPersistentUrl.getPersistentUrlFor(url, task);
            if((urlPers != null)&&(urlPers.isValid())){
                entities.addUrl(urlPers);
            }
        }
        for (HashTag hashTag : hashTags) {
            if(hashTag.isValid()){
                HashTag hashTagPers = hashTagService.store(hashTag, task);
                entities.addTag(hashTagPers);
            }
        }
        for (Mention mention : mentions) {
            if(mention.isValid()){
                entities.addMention(mentionService.store(mention, task));
            }
        }
        for(Media medium:media){
            if(medium.isValid()){
                entities.addMedium(mediaService.store(medium,task));
            }
        }
        for(TickerSymbol tickerSymbol:tickerSymbols){
            if(tickerSymbol.isValid()){
                entities.addTickerSymbol(tickerSymbolService.store(tickerSymbol,task));
            }
        }

        return entities;
    }

    @Override
    public Entities transform(org.springframework.social.twitter.api.Entities twitterEntities) {
        return null;
    }
}
