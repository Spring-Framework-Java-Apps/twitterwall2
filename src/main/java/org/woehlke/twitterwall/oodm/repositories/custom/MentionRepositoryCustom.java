package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface MentionRepositoryCustom extends DomainObjectMinimalRepository<Mention> {

    Mention findByUniqueId(Mention domainObject);
}
