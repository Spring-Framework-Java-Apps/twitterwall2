package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.MentionEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface MentionService extends OodmService {

    Mention create(Mention mention);

    Mention findByIdTwitter(long idTwitter);

    Mention update(Mention mention);

    Mention findByScreenNameAndName(Mention mention);

    Mention store(Mention mention);

    Set<Mention> getMentions(User user);

    Set<Mention> transformTwitterEntitiesMentions(List<MentionEntity> mentions);
}
