package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithScreenName;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;


/**
 * Created by tw on 12.06.17.
 */
public interface MentionService extends DomainServiceWithScreenName<Mention>,DomainServiceWithIdTwitter<Mention>,DomainServiceWithTask<Mention> {

    Mention createProxyMention(Mention mention, Task task);
}
