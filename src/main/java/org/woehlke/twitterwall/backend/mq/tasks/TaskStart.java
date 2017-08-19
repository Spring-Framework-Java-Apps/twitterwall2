package org.woehlke.twitterwall.backend.mq.tasks;

import org.woehlke.twitterwall.oodm.model.User;

public interface TaskStart extends TaskStartFireAndForget {

    User createImprintUser();

}
