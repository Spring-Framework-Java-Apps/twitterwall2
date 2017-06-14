package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Mention;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionService {

    Mention store(Mention mention);

    Mention findByIdTwitter(long idTwitter);

    Mention update(Mention mention);
}
