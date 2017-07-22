package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.impl.MentionRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MentionRepository extends DomainRepository<Mention>,MentionRepositoryCustom {

    Mention findByIdTwitter(long idTwitter);

    Mention findByIdTwitterAndScreenName(long idTwitter, String screenName);

    Mention findByScreenName(String screenName);

}
