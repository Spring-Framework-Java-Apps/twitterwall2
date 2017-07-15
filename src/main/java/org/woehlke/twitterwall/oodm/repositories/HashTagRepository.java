package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.HashTag;

/**
 * Created by tw on 15.07.17.
 */
public interface HashTagRepository extends PagingAndSortingRepository<HashTag,Long> {
}
