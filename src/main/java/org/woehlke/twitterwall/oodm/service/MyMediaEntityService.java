package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.MyMediaEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyMediaEntityService {

    MyMediaEntity store(MyMediaEntity media);

    MyMediaEntity findByIdTwitter(long idTwitter);

    MyMediaEntity update(MyMediaEntity media);
}
