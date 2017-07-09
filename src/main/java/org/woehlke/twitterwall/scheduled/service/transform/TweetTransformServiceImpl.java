package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.scheduled.service.transform.entities.*;

import java.util.Date;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TweetTransformServiceImpl implements TweetTransformService {

    private final UserTransformService userTransformService;

    private final UrlTransformService urlTransformService;

    private final HashTagTransformService hashTagTransformService;

    private final MentionTransformService mentionTransformService;

    private final MediaTransformService mediaTransformService;

    private final TickerSymbolTransformService tickerSymbolTransformService;

    @Autowired
    public TweetTransformServiceImpl(UserTransformService userTransformService, UrlTransformService urlTransformService, HashTagTransformService hashTagTransformService, MentionTransformService mentionTransformService, MediaTransformService mediaTransformService, TickerSymbolTransformService tickerSymbolTransformService) {
        this.userTransformService = userTransformService;
        this.urlTransformService = urlTransformService;
        this.hashTagTransformService = hashTagTransformService;
        this.mentionTransformService = mentionTransformService;
        this.mediaTransformService = mediaTransformService;
        this.tickerSymbolTransformService = tickerSymbolTransformService;
    }

    @Override
    public org.woehlke.twitterwall.oodm.entities.Tweet transform(org.springframework.social.twitter.api.Tweet tweet) {
        if (tweet == null) { return null; } else {
            org.woehlke.twitterwall.oodm.entities.Tweet retweetedStatus = transform(tweet.getRetweetedStatus());
            long idTwitter = tweet.getId();
            String idStr = tweet.getIdStr();
            String text = tweet.getText();
            Date createdAt = tweet.getCreatedAt();
            String fromUser = tweet.getFromUser();
            String profileImageUrl = tweet.getProfileImageUrl();
            Long toUserId = tweet.getToUserId();
            long fromUserId = tweet.getFromUserId();
            String languageCode = tweet.getLanguageCode();
            String source = tweet.getSource();
            org.woehlke.twitterwall.oodm.entities.Tweet myTweet = new org.woehlke.twitterwall.oodm.entities.Tweet(idTwitter, idStr, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
            myTweet.setFavoriteCount(tweet.getFavoriteCount());
            myTweet.setFavorited(tweet.isFavorited());
            myTweet.setInReplyToScreenName(tweet.getInReplyToScreenName());
            myTweet.setInReplyToUserId(tweet.getInReplyToUserId());
            myTweet.setLanguageCode(tweet.getLanguageCode());
            myTweet.setRetweetCount(tweet.getRetweetCount());
            myTweet.setRetweeted(tweet.isRetweeted());
            myTweet.setSource(tweet.getSource());
            myTweet.setFromUser(tweet.getFromUser());
            myTweet.setFavorited(tweet.isFavorited());
            myTweet.setInReplyToStatusId(tweet.getInReplyToStatusId());
            myTweet.setRetweetedStatus(retweetedStatus);
            TwitterProfile twitterProfile = tweet.getUser();
            /* transform user */
            User user = userTransformService.transform(twitterProfile);
            myTweet.setUser(user);
            /* transformTwitterEntities */
            myTweet.removeAllUrls();
            for(UrlEntity urlEntity:tweet.getEntities().getUrls()){
                Url url = urlTransformService.transform(urlEntity);
                myTweet.addUrl(url);
            }
            myTweet.removeAllTags();
            for(HashTagEntity hashTagEntity:tweet.getEntities().getHashTags()){
                HashTag tag = hashTagTransformService.transform(hashTagEntity);
                myTweet.addTag(tag);
            }
            myTweet.removeAllMentions();
            for(MentionEntity mentionEntity:tweet.getEntities().getMentions()){
                Mention mention = mentionTransformService.transform(mentionEntity);
                myTweet.addMention(mention);
            }
            myTweet.removeAllMedia();
            for(MediaEntity medium :tweet.getEntities().getMedia()){
                Media media = mediaTransformService.transform(medium);
                myTweet.addMedium(media);
            }
            myTweet.removeAllTickerSymbols();
            for(TickerSymbolEntity tickerSymbolEntity:tweet.getEntities().getTickerSymbols()) {
                TickerSymbol tickerSymbol = tickerSymbolTransformService.transform(tickerSymbolEntity);
                myTweet.addTickerSymbol(tickerSymbol);
            }
            return myTweet;
        }
    }
}
