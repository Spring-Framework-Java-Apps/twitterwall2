package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.Url;

public interface UrlRepositoryCustom {

    Url findByUniqueId(Url domainObject);
}
