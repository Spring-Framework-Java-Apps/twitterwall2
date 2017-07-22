package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithUrl;

/**
 * Created by tw on 23.06.17.
 */
public interface UrlCacheService extends DomainServiceWithUrl<UrlCache>,DomainServiceWithTask<UrlCache> {

}
