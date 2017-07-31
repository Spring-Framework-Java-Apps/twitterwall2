package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreEntitiesProcess;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;

/**
 * Created by tw on 09.07.17.
 */
@Component
public class StoreUserProcessImpl implements StoreUserProcess {

    @Override
    public User storeUserProcess(User user, Task task){
        String msg = "User.storeUserProcess "+user.getUniqueId()+" : "+task.getUniqueId()+" : ";
        try {
            Entities entities = user.getEntities();
            entities = storeEntitiesProcess.storeEntitiesProcess(entities, task);
            user.setEntities(entities);
            User userPers = userService.store(user, task);
            if(userPers!=null){
                return userPers;
            }
        } catch (Exception e){
            log.error(msg+e.getMessage());
        }
        return user;
    }

    private static final Logger log = LoggerFactory.getLogger(StoreUserProcessImpl.class);

    private final UserService userService;

    private final StoreEntitiesProcess storeEntitiesProcess;

    @Autowired
    public StoreUserProcessImpl(UserService userService, StoreEntitiesProcess storeEntitiesProcess) {
        this.userService = userService;
        this.storeEntitiesProcess = storeEntitiesProcess;
    }

}
