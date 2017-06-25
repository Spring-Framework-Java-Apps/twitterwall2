package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.repository.common.OodmRepository;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionRepository extends OodmRepository {

    Mention persist(Mention myMentionEntity);

    Mention update(Mention myMentionEntity);

    Mention findByIdTwitter(long idTwitter);

    Mention findByScreenName(String screenName);

    Mention findByIdTwitterAndScreenName(Long idTwitter, String screenName);
}
