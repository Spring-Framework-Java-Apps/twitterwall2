package org.woehlke.twitterwall.oodm.service.common;

import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.TwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainService<T extends DomainObject,SRC extends TwitterObject> {

    T store(T domainObject);
    
    T create(T domainObject);

    T update(T domainObject);

    List<T> getAll();

    long count();

    Set<T> transform(List<SRC> twitterObject);
}
