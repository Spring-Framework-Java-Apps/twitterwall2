package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.parts.Entities;

public interface DomainObjectWithEntities<T extends DomainObjectWithEntities> extends DomainObjectWithIdTwitter<T>  {

    void removeAllEntities();

    Entities getEntities();

    void setEntities(Entities entities);

}
