package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectEntityService;
import org.woehlke.twitterwall.oodm.service.common.DomainService;


/**
 * Created by tw on 12.06.17.
 */
public interface HashTagService extends DomainService<HashTag>,DomainObjectEntityService<HashTag> {

    HashTag findByText(String text);

}
