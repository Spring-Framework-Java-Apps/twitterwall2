package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MediaRepository extends DomainRepository<Media> {

    Media findByIdTwitter(long idTwitter);

    Media findByUrl(String url);

    @Query(name="Media.findByUniqueId")
    Media findByUniqueId(@Param("domainObject") Media domainObject);
}
