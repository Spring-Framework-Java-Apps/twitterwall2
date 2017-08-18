package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.MentionRepositoryCustom;

import java.util.List;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface MentionRepository extends DomainRepository<Mention>,MentionRepositoryCustom {

    List<Mention> findByIdTwitter(long idTwitter);

    Mention findByScreenNameUnique(String screenNameUnique);


    @Query(
        name = "Mention.findAllWithoutUser",
        countName = "Mention.countAllWithoutUser"
    )
    Page<Mention> findAllWithoutUser(Pageable pageRequest);


    @Query(
        name = "Mention.findByUserId",
        countName = "Mention.countByUserId"
    )
    Page<Mention> findByUserId(@Param("idOfUser") long idOfUser, Pageable pageRequest);


    @Query(
        name = "Mention.findByScreenNameUnique",
        countName = "Mention.countByScreenNameUnique"
    )
    Page<Mention> findAllByScreenNameUnique(@Param("screenNameUnique") String screenNameUnique, Pageable pageRequest);


    @Query(
        name = "Mention.findByIdTwitterOfUser",
        countName = "Mention.countByIdTwitterOfUser"
    )
    Page<Mention> findByIdTwitterOfUser(@Param("idOfUser") long idOfUser, Pageable pageRequest);


    Mention findByScreenNameUniqueAndIdTwitter(String screenNameUnique,Long idTwitter);

}
