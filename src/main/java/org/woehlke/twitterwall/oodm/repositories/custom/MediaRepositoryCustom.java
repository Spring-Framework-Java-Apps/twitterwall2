package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.model.Media;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectEntityRepository;

public interface MediaRepositoryCustom extends DomainObjectEntityRepository<Media> {

    Media findByUniqueId(Media domainObject);
}
