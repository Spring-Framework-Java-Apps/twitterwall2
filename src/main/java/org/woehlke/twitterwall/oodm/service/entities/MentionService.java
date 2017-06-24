package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionService extends OodmService {

    Mention store(Mention mention);

    Mention findByIdTwitter(long idTwitter);

    Mention update(Mention mention);

    Mention findByScreenNameAndName(Mention mention);
}
