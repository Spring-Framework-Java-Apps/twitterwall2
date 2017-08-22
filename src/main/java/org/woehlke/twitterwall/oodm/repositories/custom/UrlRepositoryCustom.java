package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectEntityRepository;

public interface UrlRepositoryCustom extends DomainObjectEntityRepository<Url> {

    Url findByUniqueId(Url domainObject);
}
