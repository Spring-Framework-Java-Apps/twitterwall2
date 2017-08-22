package org.woehlke.twitterwall.backend.service.transform;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UrlEntity;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.backend.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface UrlTransformService extends TransformService<Url,UrlEntity> {

    Set<Url> getUrlsForTwitterProfile(TwitterProfile userSource, Task task);

    Set<Url> getUrlsForTweet(Tweet tweetFromTwitterApi, Task task);

}
