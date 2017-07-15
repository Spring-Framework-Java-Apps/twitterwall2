package org.woehlke.twitterwall.oodm.dao.parts;

import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainDaoWithIdTwitter<T extends DomainObjectWithIdTwitter> extends DomainDao<T> {

    T findByIdTwitter(long idTwitter,Class<T> myClass);
}
