package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.common.DomainService;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;


/**
 * Created by tw on 12.06.17.
 */
public interface HashTagService extends DomainService<HashTag>,DomainServiceWithTask<HashTag> {

    HashTag findByText(String text);

}
