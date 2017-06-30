package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.HashTagEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.common.DomainService;


import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagService extends DomainService<HashTag> {

    HashTag findByText(String text);
    
}
