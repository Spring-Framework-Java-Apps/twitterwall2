package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.Tweet;

public interface TweetRepositoryCustom {

    Tweet findByUniqueId(Tweet domainObject);
}
