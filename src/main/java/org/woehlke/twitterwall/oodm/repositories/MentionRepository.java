package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.MentionRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MentionRepository extends DomainRepository<Mention>,MentionRepositoryCustom {

    Mention findByIdTwitter(long idTwitter);

    Mention findByScreenNameUnique(String screenNameUnique);

    Page<Mention> findAllByUserNull(Pageable pageRequest);

    Mention findByScreenNameUniqueAndIdTwitter(String screenNameUnique,Long idTwitter);

}
