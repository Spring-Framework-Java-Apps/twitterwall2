package org.woehlke.twitterwall.oodm.dao.parts;

import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainDaoWithScreenName<T extends DomainObjectWithScreenName> extends DomainDao<T> {

    T findByScreenName(String screenName);
}
