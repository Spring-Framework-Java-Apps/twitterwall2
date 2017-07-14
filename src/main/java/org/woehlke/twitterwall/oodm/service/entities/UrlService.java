package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithUrl;


/**
 * Created by tw on 12.06.17.
 */
public interface UrlService extends DomainServiceWithUrl<Url>,DomainServiceWithTask<Url> {

}
