package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.Mention;

public interface MentionRepositoryCustom {

    Mention findByUniqueId(Mention domainObject);
}
