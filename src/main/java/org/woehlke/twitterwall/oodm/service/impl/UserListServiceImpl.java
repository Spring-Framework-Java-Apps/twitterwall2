package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.UserList;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.UserListRepository;
import org.woehlke.twitterwall.oodm.service.UserListService;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserListServiceImpl extends DomainServiceWithTaskImpl<UserList> implements UserListService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserListRepository domainRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public UserListServiceImpl(UserListRepository domainRepository, TaskRepository taskRepository) {
        super(domainRepository, taskRepository);
        this.domainRepository=domainRepository;
        this.taskRepository=taskRepository;
    }

    @Override
    public UserList findByIdTwitter(long idTwitter) {
        return domainRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public UserList findByUniqueId(UserList domainExampleObject) {
        return domainRepository.findByUniqueId(domainExampleObject);
    }
}
