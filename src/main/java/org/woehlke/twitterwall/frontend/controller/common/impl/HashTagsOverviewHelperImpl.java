package org.woehlke.twitterwall.frontend.controller.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.HashTagsOverviewHelper;
import org.woehlke.twitterwall.frontend.model.HashTagCounted;
import org.woehlke.twitterwall.frontend.model.HashTagOverview;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.HashTagService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component
public class HashTagsOverviewHelperImpl implements HashTagsOverviewHelper {

    @Override
    public HashTagOverview getHashTagOverview() {
        String msg = "getHashTagOverview";
        List<HashTagCounted> hashTagsTweets = new ArrayList<>();
        List<HashTagCounted> hashTagsUsers = new ArrayList<>();
        Pageable pageRequest = new PageRequest(
                FIRST_PAGE_NUMBER,
                frontendProperties.getPageSize()
        );
        boolean hasNext = true;
        while(hasNext) {
            Page<HashTag> myPage = hashTagService.getAll(pageRequest);
            for (HashTag hashTag : myPage.getContent()) {
                Pageable pageRequestTeets = new PageRequest(0, 1);
                Page<Tweet> tweets = tweetService.findTweetsForHashTag(hashTag, pageRequestTeets);
                String myMSg = msg + " tweetService.findTweetsForHashTag= " + hashTag.getHashTagText().getText();
                if (tweets == null) {
                    log.debug(myMSg + " result: null");
                } else {
                    long numberTweets = tweets.getTotalElements();
                    log.debug(myMSg + " result: numberTweets=" + numberTweets);
                    if (numberTweets > 0) {
                        HashTagCounted c = new HashTagCounted(hashTag.getId(),numberTweets, hashTag.getHashTagText().getText());
                        hashTagsTweets.add(c);
                    }
                }
                Pageable pageRequestUsers = new PageRequest(0, 1);
                Page<User> users = userService.getUsersForHashTag(hashTag, pageRequestUsers);
                myMSg = msg + " userService.getUsersForHashTag= " + hashTag.getHashTagText().getText();
                if (users == null) {
                    log.debug(myMSg + " result: null");
                } else {
                    long numberUsers = users.getTotalElements();
                    log.debug(myMSg + " result: numberUsers=" + numberUsers);
                    if (numberUsers > 0) {
                        HashTagCounted c = new HashTagCounted(hashTag.getId(), numberUsers, hashTag.getHashTagText().getText());
                        hashTagsUsers.add(c);
                    }
                }
            }
            hasNext = myPage.hasNext();
            if(hasNext){
                pageRequest=myPage.nextPageable();
            }
        }
        return new HashTagOverview(hashTagsTweets,hashTagsUsers);
    }

    private static final Logger log = LoggerFactory.getLogger(HashTagsOverviewHelperImpl.class);

    private final UserService userService;

    private final HashTagService hashTagService;

    private final TweetService tweetService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public HashTagsOverviewHelperImpl(
            UserService userService,
            HashTagService hashTagService,
            TweetService tweetService,
            FrontendProperties frontendProperties
    ) {
        this.userService = userService;
        this.hashTagService = hashTagService;
        this.tweetService = tweetService;
        this.frontendProperties = frontendProperties;
    }
}
