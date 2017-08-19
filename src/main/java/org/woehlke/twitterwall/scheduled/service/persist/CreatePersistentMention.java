package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Mention;

/**
 * Created by tw on 14.07.17.
 */
public interface CreatePersistentMention {

    Mention getPersistentMentionAndUserFor(Mention mention, Task task);

}
