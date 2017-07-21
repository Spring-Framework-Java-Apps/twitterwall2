package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.HashTagRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.HashTagService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class HashTagServiceImpl implements HashTagService {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceImpl.class);

    private final HashTagRepository hashTagRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository, TaskRepository taskRepository) {
        this.hashTagRepository = hashTagRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public HashTag create(HashTag hashTag, Task task) {
        hashTag.setCreatedBy(task);
        return hashTagRepository.save(hashTag);
    }

    @Override
    public HashTag update(HashTag hashTag, Task task) {
        hashTag.setUpdatedBy(task);
        return hashTagRepository.save(hashTag);
    }

    @Override
    public HashTag findByText(String text) {
        return hashTagRepository.findByText(text);
    }

    @Override
    public Page<HashTag> getAll(Pageable pageRequest) {
        return hashTagRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return hashTagRepository.count();
    }

    @Override
    public HashTag store(HashTag hashTag, Task task) {
        task.setTimeLastUpdate();
        task = this.taskRepository.save(task);
        HashTag tagPers = hashTagRepository.findByText(hashTag.getText());
        if(tagPers!=null){
            hashTag.setId(tagPers.getId());
            hashTag.setTaskInfo(tagPers.getTaskInfo());
            hashTag.setCreatedBy(tagPers.getCreatedBy());
            hashTag.setUpdatedBy(task);
            hashTag = hashTagRepository.save(hashTag);
            log.debug("found: "+hashTag.toString());
            return hashTag;
        } else {
            hashTag.setCreatedBy(task);
            hashTag.setUpdatedBy(task);
            log.debug("try to persist: "+hashTag.toString());
            HashTag tagPers2 = hashTagRepository.save(hashTag);
            log.debug("persisted: "+tagPers2.toString());
            return tagPers2;
        }
    }

}
