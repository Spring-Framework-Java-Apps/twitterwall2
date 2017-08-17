package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectEntityRepository;

public interface MentionRepositoryCustom extends DomainObjectEntityRepository<Mention>  {

    Mention findByUniqueId(Mention domainObject);
}
