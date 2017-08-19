package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.entities.Entities;

public interface DomainObjectWithEntities<T extends DomainObjectWithEntities> extends DomainObjectWithIdTwitter<T>,DomainObjectWithTask<T>  {

    void removeAllEntities();

    Entities getEntities();

    void setEntities(Entities entities);

}
