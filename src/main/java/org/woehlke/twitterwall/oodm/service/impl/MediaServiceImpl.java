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
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.repositories.MediaRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.MediaService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MediaServiceImpl implements MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, TaskRepository taskRepository) {
        this.mediaRepository = mediaRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Media create(Media media,Task task) {
        media.setUpdatedBy(task);
        return mediaRepository.save(media);
    }

    @Override
    public Media findByIdTwitter(long idTwitter) {
        return mediaRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Media update(Media media,Task task) {
        media.setUpdatedBy(task);
        return mediaRepository.save(media);
    }

    @Override
    public Page<Media> getAll(Pageable pageRequest) {
        return mediaRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return mediaRepository.count();
    }

    @Override
    public Media store(Media media, Task task) {
        task.setTimeLastUpdate();
        task = this.taskRepository.save(task);
        String msg = "Media.store: ";
        log.debug(msg+"try to find: "+media.toString());
        Media mediaPers = mediaRepository.findByIdTwitter(media.getIdTwitter()); //this.mediaDao.findByIdTwitter(media.getIdTwitter(),Media.class);
        if(mediaPers!=null){
            log.debug(msg+"found: "+mediaPers.toString());
            media.setId(mediaPers.getId());
            media.setMediaType(mediaPers.getMediaType());
            media.setCreatedBy(mediaPers.getCreatedBy());
            media.setUpdatedBy(task);
            log.debug(msg+"found and try to update: "+media.toString());
            return mediaRepository.save(media);
        } else {
            media.setCreatedBy(task);
            media.setUpdatedBy(task);
            log.debug(msg+"not found and try to persist: "+media.toString());
            return mediaRepository.save(media);
        }
    }

    @Override
    public Media findByUrl(String url) {
        return mediaRepository.findByUrl(url);
    }
}
