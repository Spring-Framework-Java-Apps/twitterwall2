package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectEntityService;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithUrl;


/**
 * Created by tw on 12.06.17.
 */
public interface MediaService extends DomainServiceWithIdTwitter<Media>,DomainServiceWithUrl<Media>,DomainObjectEntityService<Media> {

}
