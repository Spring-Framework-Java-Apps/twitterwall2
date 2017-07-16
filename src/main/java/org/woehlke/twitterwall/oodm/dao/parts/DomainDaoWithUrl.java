package org.woehlke.twitterwall.oodm.dao.parts;

import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainDaoWithUrl<T extends DomainObjectWithUrl> extends DomainDao<T> {

    T findByUrl(String url);
}
