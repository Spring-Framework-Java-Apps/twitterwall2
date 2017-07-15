package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.dao.MediaDao;
import org.woehlke.twitterwall.oodm.repositories.MediaRepository;
import org.woehlke.twitterwall.oodm.service.MediaService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MediaServiceImpl implements MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaDao mediaDao;

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(MediaDao mediaDao, MediaRepository mediaRepository) {
        this.mediaDao = mediaDao;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media create(Media media,Task task) {
        media.setUpdatedBy(task);
        return this.mediaDao.persist(media);
    }

    @Override
    public Media findByIdTwitter(long idTwitter) {
        return this.mediaDao.findByIdTwitter(idTwitter,Media.class);
    }

    @Override
    public Media update(Media media,Task task) {
        media.setUpdatedBy(task);
        return this.mediaDao.update(media);
    }

    @Override
    public Page<Media> getAll(Pageable pageRequest) {
        return this.mediaDao.getAll(Media.class,pageRequest);
    }

    @Override
    public long count() {
        return this.mediaDao.count(Media.class);
    }

    @Override
    public Media store(Media media, Task task) {
        String msg = "Media.store: ";
        try {
            log.debug(msg+"try to find: "+media.toString());
            Media mediaPers = this.mediaDao.findByIdTwitter(media.getIdTwitter(),Media.class);
            log.debug(msg+"found: "+mediaPers.toString());
            media.setId(mediaPers.getId());
            media.setCreatedBy(mediaPers.getCreatedBy());
            media.setUpdatedBy(task);
            log.debug(msg+"found and try to update: "+media.toString());
            return this.mediaDao.update(media);
        } catch (EmptyResultDataAccessException e) {
            media.setCreatedBy(task);
            log.debug(msg+"not found and try to persist: "+media.toString());
            return this.mediaDao.persist(media);
        }
    }

    @Override
    public Media findByUrl(String url) {
        return this.mediaDao.findByUrl(url);
    }
}
