package org.woehlke.twitterwall.oodm.entities.common;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainObjectWithIdTwitter<T extends DomainObjectWithIdTwitter> extends DomainObject<T> {

    long getIdTwitter();

    void setIdTwitter(long idTwitter);
    
}
