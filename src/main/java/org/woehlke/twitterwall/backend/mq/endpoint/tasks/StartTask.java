package org.woehlke.twitterwall.backend.mq.endpoint.tasks;

import org.woehlke.twitterwall.oodm.model.User;

public interface StartTask extends AsyncStartTask {

    User createImprintUser();

}
