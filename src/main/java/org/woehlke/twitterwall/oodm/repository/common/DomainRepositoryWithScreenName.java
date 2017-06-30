package org.woehlke.twitterwall.oodm.repository.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainRepositoryWithScreenName<T extends DomainObjectWithScreenName> extends DomainRepository<T>  {

    T findByScreenName(String screenName);
}
