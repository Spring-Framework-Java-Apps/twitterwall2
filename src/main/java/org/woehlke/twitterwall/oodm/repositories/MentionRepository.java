package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MentionRepository extends PagingAndSortingRepository<Mention,Long> {

    Mention findByIdTwitter(long idTwitter);

    Mention findByIdTwitterAndScreenName(long idTwitter, String screenName);

    Mention findByScreenName(String screenName);

    //long findLowestIdTwitter(Mention mention);
}
