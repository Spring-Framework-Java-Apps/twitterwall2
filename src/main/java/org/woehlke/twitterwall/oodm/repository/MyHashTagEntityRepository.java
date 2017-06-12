package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyHashTagEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyHashTagEntityRepository {

    MyHashTagEntity persist(MyHashTagEntity myHashTagEntity);

    MyHashTagEntity update(MyHashTagEntity myHashTagEntity);
}
