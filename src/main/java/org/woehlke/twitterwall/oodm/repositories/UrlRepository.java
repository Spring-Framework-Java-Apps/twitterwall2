package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.Url;

/**
 * Created by tw on 15.07.17.
 */
public interface UrlRepository extends PagingAndSortingRepository<Url,Long> {

    Url findByUrl(String url);
}
