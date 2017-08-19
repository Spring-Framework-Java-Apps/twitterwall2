package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithEntities;

public interface DomainObjectWithEntitiesService<T extends DomainObjectWithEntities> extends DomainServiceWithIdTwitter<T>,DomainServiceWithTask<T> {
}
