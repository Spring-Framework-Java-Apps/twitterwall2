package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyUrlEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyUrlEntityRepository {

    MyUrlEntity persist(MyUrlEntity myUrlEntity);

    MyUrlEntity update(MyUrlEntity myUrlEntity);
}
