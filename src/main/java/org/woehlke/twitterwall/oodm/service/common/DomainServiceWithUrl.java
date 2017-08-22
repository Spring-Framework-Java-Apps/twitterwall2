package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithUrl;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainServiceWithUrl<T extends DomainObjectWithUrl> extends DomainService<T>  {

    T findByUrl(String url);
}
