package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.parts.UrlField;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainObjectWithUrl <T extends DomainObjectWithUrl> extends DomainObject<T> {

    UrlField getUrl();

    void setUrl(UrlField url);
}
