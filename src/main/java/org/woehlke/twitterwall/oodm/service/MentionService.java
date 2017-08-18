package org.woehlke.twitterwall.oodm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectEntityService;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithScreenName;


/**
 * Created by tw on 12.06.17.
 */
public interface MentionService extends DomainServiceWithScreenName<Mention>,DomainServiceWithIdTwitter<Mention>,DomainObjectEntityService<Mention> {

    Mention createProxyMention(Mention mention, Task task);

    Page<Mention> getAllWithoutUser(Pageable pageRequest);

    Mention findByScreenNameAndIdTwitter(String screenName, Long idTwitter);

    Page<Mention> findByUserId(long idOfUser, Pageable pageRequest);

    Page<Mention> findAllByScreenName(String screenName, Pageable pageRequest);

    Page<Mention> findByIdTwitterOfUser(long idOfUser, Pageable pageRequest);
}
