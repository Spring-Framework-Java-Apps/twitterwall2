package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithIdTwitter;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainServiceWithIdTwitter<T extends DomainObjectWithIdTwitter> extends DomainService<T> {

    T findByIdTwitter(long idTwitter);
}
