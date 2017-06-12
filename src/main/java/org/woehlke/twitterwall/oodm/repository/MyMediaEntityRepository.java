package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyMediaEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyMediaEntityRepository {

    MyMediaEntity persist(MyMediaEntity myMediaEntity);

    MyMediaEntity update(MyMediaEntity myMediaEntity);

    MyMediaEntity findByIdTwitter(long idTwitter);
}
