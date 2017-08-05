package org.woehlke.twitterwall.oodm.entities.common;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainObjectWithScreenName<T extends DomainObjectWithScreenName> extends DomainObject<T>{

    String getScreenName();

    void setScreenName(String screenName);

    String getScreenNameUnique();

    void setScreenNameUnique(String screenNameUnique);
}
