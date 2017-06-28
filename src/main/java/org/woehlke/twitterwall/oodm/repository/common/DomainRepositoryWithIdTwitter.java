package org.woehlke.twitterwall.oodm.repository.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainRepositoryWithIdTwitter<T extends DomainObjectWithIdTwitter> extends DomainRepository<T> {

    T findByIdTwitter(long idTwitter,Class<T> myClass);
}
