package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface HashTagRepository extends DomainRepository<HashTag> {

    HashTag findByText(String text);

    @Query(name="HashTag.findByUniqueId")
    HashTag findByUniqueId(@Param("domainObject") HashTag domainObject);
}
