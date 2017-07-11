package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.common.DomainService;


/**
 * Created by tw on 12.06.17.
 */
public interface UrlService extends DomainService<Url> {

    Url findByUrl(String url);

}
