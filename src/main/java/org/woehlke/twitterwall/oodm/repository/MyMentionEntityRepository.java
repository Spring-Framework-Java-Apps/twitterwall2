package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyMentionEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyMentionEntityRepository {

    MyMentionEntity persist(MyMentionEntity myMentionEntity);

    MyMentionEntity update(MyMentionEntity myMentionEntity);

    MyMentionEntity findByIdTwitter(long idTwitter);
}
