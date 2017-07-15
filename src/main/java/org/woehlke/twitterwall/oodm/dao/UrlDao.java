package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithUrl;

/**
 * Created by tw on 12.06.17.
 */
public interface UrlDao extends DomainDao<Url>, DomainDaoWithUrl<Url> {

}
