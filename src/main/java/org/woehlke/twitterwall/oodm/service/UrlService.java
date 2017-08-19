package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectEntityService;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithUrl;

import java.util.List;


/**
 * Created by tw on 12.06.17.
 */
public interface UrlService extends DomainObjectEntityService<Url>,DomainServiceWithUrl<Url> {

    List<Url> findRawUrlsFromDescription();

    List<Url> findUrlAndExpandedTheSame();

}
