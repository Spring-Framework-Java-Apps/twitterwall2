package org.woehlke.twitterwall.scheduled.mq.endpoint.tasks;

import org.woehlke.twitterwall.oodm.model.User;

public interface StartTask extends AsyncStartTask {

    User createImprintUser();

}
