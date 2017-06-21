package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Url;

/**
 * Created by tw on 12.06.17.
 */
public interface UrlRepository {

    Url persist(Url url);

    Url update(Url url);

    Url findByDisplayExpandedUrl(String display, String expanded, String url);

    Url findByUrl(String url);
}
