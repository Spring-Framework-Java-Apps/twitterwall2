package org.woehlke.twitterwall.oodm.model.common;

import org.woehlke.twitterwall.oodm.model.entities.Entities;

public interface DomainObjectWithEntities<T extends DomainObjectWithEntities> extends DomainObjectWithIdTwitter<T>,DomainObjectWithTask<T>  {

    void removeAllEntities();

    Entities getEntities();

    void setEntities(Entities entities);

}
