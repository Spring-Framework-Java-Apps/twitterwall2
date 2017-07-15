package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.Media;

/**
 * Created by tw on 15.07.17.
 */
public interface MediaRepository extends PagingAndSortingRepository<Media,Long> {

    Media findByIdTwitter(long idTwitter);

    Media findByUrl(String url);
}
