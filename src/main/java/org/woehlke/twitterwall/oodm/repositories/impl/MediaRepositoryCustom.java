package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.Media;

public interface MediaRepositoryCustom {

    Media findByUniqueId(Media domainObject);
}
