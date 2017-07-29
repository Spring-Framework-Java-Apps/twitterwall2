package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithEntities;

public interface DomainObjectWithEntitiesService<T extends DomainObjectWithEntities> extends DomainServiceWithIdTwitter<T>,DomainServiceWithTask<T> {
}
