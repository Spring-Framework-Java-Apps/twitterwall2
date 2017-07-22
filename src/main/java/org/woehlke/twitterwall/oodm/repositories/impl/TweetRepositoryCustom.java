package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface TweetRepositoryCustom extends DomainObjectMinimalRepository<Tweet> {

    Tweet findByUniqueId(Tweet domainObject);
}
