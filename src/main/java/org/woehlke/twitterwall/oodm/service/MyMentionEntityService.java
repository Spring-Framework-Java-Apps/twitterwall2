package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.MyMentionEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyMentionEntityService {

    MyMentionEntity store(MyMentionEntity mention);

    MyMentionEntity findByIdTwitter(long idTwitter);

    MyMentionEntity update(MyMentionEntity mention);
}
