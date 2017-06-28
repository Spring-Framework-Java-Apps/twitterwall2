package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithUrl;

/**
 * Created by tw on 12.06.17.
 */
public interface UrlRepository extends DomainRepository<Url>, DomainRepositoryWithUrl<Url> {
    
    Url findByDisplayExpandedUrl(String display, String expanded, String url);
}
