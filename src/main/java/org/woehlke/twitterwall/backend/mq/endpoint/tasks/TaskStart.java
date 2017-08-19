package org.woehlke.twitterwall.backend.mq.endpoint.tasks;

import org.woehlke.twitterwall.oodm.model.User;

public interface TaskStart extends TaskStartFireAndForget {

    User createImprintUser();

}
