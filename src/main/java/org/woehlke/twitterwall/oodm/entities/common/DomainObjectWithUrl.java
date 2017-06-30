package org.woehlke.twitterwall.oodm.entities.common;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainObjectWithUrl <T extends DomainObjectWithUrl> extends DomainObject<T> {

    String getUrl();

    void setUrl(String url);
}
