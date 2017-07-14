package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitter;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithScreenName;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionRepository extends DomainRepositoryWithIdTwitter<Mention>,DomainRepositoryWithScreenName<Mention> {

    Mention findByIdTwitterAndScreenName(Long idTwitter, String screenName);

    long findLowestIdTwitter(Mention mention);

}
