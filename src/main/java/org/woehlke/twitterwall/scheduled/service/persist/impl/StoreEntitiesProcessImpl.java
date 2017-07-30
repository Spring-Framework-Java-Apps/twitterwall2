package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.service.*;
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
        String msg = "storeEntitiesProcess "+task.getUniqueId()+" : ";
        try {
            Set<Url> urls = new LinkedHashSet<>();
            Set<HashTag> hashTags = new LinkedHashSet<HashTag>();
            Set<Mention> mentions = new LinkedHashSet<Mention>();
            Set<Media> media = new LinkedHashSet<Media>();
            Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
            for (Url myUrl : entities.getUrls()) {
                if (myUrl.isValid()) {
                    Url urlPers = createPersistentUrl.createPersistentUrlFor(myUrl.getUrl().getUrl(), task);
                    if(urlPers!=null){
                        urls.add(urlPers);
                    }
                }
            }

/*

                        Url urlPers = urlService.findByUniqueId(myUrl);

                        if (urlPers != null) {
                            if(urlPers.isUrlAndExpandedTheSame()||urlPers.isRawUrlsFromDescription()){
                                if(myUrl.isRawUrlsFromDescription()|| myUrl.isUrlAndExpandedTheSame()){

                                } else {
                                    urlPers.setDisplay(myUrl.getDisplay());
                                    urlPers.setExpanded(myUrl.getExpanded());
                                    urlPers = urlService.store(urlPers, task);
                                }
                            }


                        } else {
                            urlPers = urlService.store(myUrl, task);
                        }
                        urls.add(urlPers);
                    } else if (myUrl.isRawUrlsFromDescription()) {
                        String urlStr = myUrl.getUrl().getUrl();
                        Url newUrlPers = createPersistentUrl.createPersistentUrlFor(urlStr, task);
                        if ((newUrlPers != null) && (newUrlPers.isValid())) {
                            urls.add(newUrlPers);
                        }
                    }
                }
                */
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
                    if(mentionPers != null){
                        mentions.add(mentionPers);
                    }
                }/* else if(mention.isRawMentionFromUserDescription()){
                Mention mentionPers = createPersistentMention.getPersistentMentionAndUserFor(mention,task);
                if((mentionPers != null) && mentionPers.isValid()){
                    mentions.add(mentionPers);
                }
            }*/
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

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CreatePersistentUrl createPersistentUrl;

    private final CreatePersistentMention createPersistentMention;

    @Autowired
    public StoreEntitiesProcessImpl(HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl, CreatePersistentMention createPersistentMention) {
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
        this.createPersistentMention = createPersistentMention;
    }

}
