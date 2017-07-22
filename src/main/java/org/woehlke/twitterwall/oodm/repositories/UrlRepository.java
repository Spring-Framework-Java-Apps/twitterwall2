package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface UrlRepository extends DomainRepository<Url>,UrlRepositoryCustom {

    Url findByUrl(String url);
}
