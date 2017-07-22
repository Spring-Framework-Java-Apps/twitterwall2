package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface UrlRepositoryCustom extends DomainObjectMinimalRepository<Url> {

    Url findByUniqueId(Url domainObject);
}
