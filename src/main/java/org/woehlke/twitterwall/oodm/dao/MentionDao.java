package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithIdTwitter;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithScreenName;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionDao extends DomainDaoWithIdTwitter<Mention>,DomainDaoWithScreenName<Mention> {

    Mention findByIdTwitterAndScreenName(Long idTwitter, String screenName);

    long findLowestIdTwitter(Mention mention);

}
