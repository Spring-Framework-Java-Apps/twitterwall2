package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithIdTwitter;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithUrl;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaDao extends DomainDaoWithIdTwitter<Media>,DomainDaoWithUrl<Media> {
}
