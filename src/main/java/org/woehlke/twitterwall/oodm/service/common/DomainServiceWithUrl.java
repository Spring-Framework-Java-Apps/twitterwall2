package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainServiceWithUrl<T extends DomainObjectWithUrl> extends DomainService<T>  {

    T findByUrl(String url);
}
