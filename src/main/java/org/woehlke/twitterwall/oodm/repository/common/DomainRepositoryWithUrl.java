package org.woehlke.twitterwall.oodm.repository.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

/**
 * Created by tw on 28.06.17.
 */
public interface DomainRepositoryWithUrl<T extends DomainObjectWithUrl> extends DomainRepository<T> {
    
    T findByUrl(String url);
}
