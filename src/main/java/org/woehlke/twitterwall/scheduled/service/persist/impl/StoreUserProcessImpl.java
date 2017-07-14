package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreEntitiesProcess;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProcessImpl implements StoreUserProcess {

    private static final Logger log = LoggerFactory.getLogger(StoreUserProcessImpl.class);

    private final UserService userService;

    private final StoreEntitiesProcess storeEntitiesProcess;

    @Autowired
    public StoreUserProcessImpl(UserService userService, StoreEntitiesProcess storeEntitiesProcess) {
        this.userService = userService;
        this.storeEntitiesProcess = storeEntitiesProcess;
    }

    @Override
    public User storeUserProcess(User user, Task task){
        String msg = "User.storeUserProcess ";
        Entities entities = user.getEntities();
        entities = storeEntitiesProcess.storeEntitiesProcess(entities,task);
        user.setEntities(entities);
        user = userService.store(user,task);
        return user;
    }
}
