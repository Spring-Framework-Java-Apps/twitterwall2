package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MentionRepository extends DomainRepository<Mention> {

    Mention findByIdTwitter(long idTwitter);

    Mention findByIdTwitterAndScreenName(long idTwitter, String screenName);

    Mention findByScreenName(String screenName);

    @Query(name="Mention.findByUniqueId")
    Mention findByUniqueId(@Param("domainObject") Mention domainObject);
}
