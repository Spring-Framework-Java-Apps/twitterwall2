package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface UrlRepository extends DomainRepository<Url> {

    @Query(name="Url.findByUniqueId")
    Url findByUniqueId(@Param("domainObject") Url domainObject);

    Url findByUrl(String url);
}
