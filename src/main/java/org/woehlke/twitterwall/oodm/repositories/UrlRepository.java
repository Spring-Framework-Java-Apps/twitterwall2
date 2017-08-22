package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlRepositoryCustom;

import java.util.List;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface UrlRepository extends DomainRepository<Url>,UrlRepositoryCustom {

    Url findByUrl(String url);

    @Query(name = "Url.findRawUrlsFromDescription")
    List<Url> findRawUrlsFromDescription();

    @Query(name = "Url.findUrlAndExpandedTheSame")
    List<Url> findUrlAndExpandedTheSame();

}
