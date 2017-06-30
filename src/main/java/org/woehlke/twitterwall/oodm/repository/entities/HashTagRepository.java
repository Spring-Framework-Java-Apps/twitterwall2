package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagRepository extends DomainRepository<HashTag> {
    
    HashTag findByText(String text);
    
}
