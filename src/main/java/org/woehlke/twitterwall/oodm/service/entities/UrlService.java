package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.UrlEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface UrlService extends DomainService {

    Url create(Url url);

    Url update(Url url);

    Url findByDisplayExpandedUrl(String display, String expanded, String url);

    Url findByUrl(String url);

    Url store(Url url);

    Set<Url> transformUrls(List<UrlEntity> urls);

    Url fetchTransientUrl(String urlSrc);

    Url getPersistentUrlFor(String url);

    List<Url> getTestData();

    Set<Url> getUrlsFor(User user);

}
