package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagDao extends DomainDao<HashTag> {

    HashTag findByText(String text);

}
