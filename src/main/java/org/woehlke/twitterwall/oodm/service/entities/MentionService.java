package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.common.DomainService;


/**
 * Created by tw on 12.06.17.
 */
public interface MentionService extends DomainService<Mention>  {

    Mention findByIdTwitter(long idTwitter);
}
