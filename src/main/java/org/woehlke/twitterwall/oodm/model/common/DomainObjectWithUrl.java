package org.woehlke.twitterwall.oodm.model.common;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainObjectWithUrl <T extends DomainObjectWithUrl> extends DomainObject<T> {

    String getUrl();

    void setUrl(String url);
}
