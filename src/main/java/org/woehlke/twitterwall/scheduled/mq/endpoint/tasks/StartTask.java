package org.woehlke.twitterwall.scheduled.mq.endpoint.tasks;

import org.woehlke.twitterwall.oodm.entities.User;

public interface StartTask extends AsyncStartTask {

    User createImprintUser();

}
