package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.MentionEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionService extends DomainService<Mention>  {

    Mention findByIdTwitter(long idTwitter);
}
