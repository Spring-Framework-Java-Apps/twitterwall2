package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlCacheRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UrlCacheRepositoryImpl extends AbstractDomainRepositoryImpl<UrlCache> implements UrlCacheRepositoryCustom {

    @Autowired
    public UrlCacheRepositoryImpl(EntityManager entityManager) {
        super( entityManager );
    }

}
