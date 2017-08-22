package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.model.Media;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.MediaRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MediaRepository extends DomainRepository<Media>,MediaRepositoryCustom {

    Media findByIdTwitter(long idTwitter);

    Media findByUrl(String url);

}
