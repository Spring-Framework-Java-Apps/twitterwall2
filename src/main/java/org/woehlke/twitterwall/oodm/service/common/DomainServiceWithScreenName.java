package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainServiceWithScreenName<T extends DomainObjectWithScreenName> extends DomainService<T> {

    T findByScreenName(String screenName);
}
