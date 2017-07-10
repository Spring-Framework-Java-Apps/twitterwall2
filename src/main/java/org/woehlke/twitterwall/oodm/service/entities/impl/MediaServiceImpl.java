package org.woehlke.twitterwall.oodm.service.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.repository.entities.MediaRepository;
import org.woehlke.twitterwall.oodm.service.entities.MediaService;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MediaServiceImpl implements MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media create(Media media) {
        return this.mediaRepository.persist(media);
    }

    @Override
    public Media findByIdTwitter(long idTwitter) {
        return this.mediaRepository.findByIdTwitter(idTwitter,Media.class);
    }

    @Override
    public Media update(Media media) {
        return this.mediaRepository.update(media);
    }

    @Override
    public List<Media> getAll() {
        return this.mediaRepository.getAll(Media.class);
    }

    @Override
    public long count() {
        return this.mediaRepository.count(Media.class);
    }

    @Override
    public Media store(Media media, Task task) {
        String msg = "Media.store: ";
        try {
            log.debug(msg+"try to find: "+media.toString());
            Media mediaPers = this.mediaRepository.findByIdTwitter(media.getIdTwitter(),Media.class);
            log.debug(msg+"found: "+media.toString());
            mediaPers.setDisplay(media.getDisplay());
            mediaPers.setExpanded(media.getExpanded());
            mediaPers.setIdTwitter(media.getIdTwitter());
            //mediaPers.setIndices(media.getIndices());
            mediaPers.setMediaHttp(media.getMediaHttp());
            mediaPers.setMediaHttps(media.getMediaHttps());
            mediaPers.setMediaType(media.getMediaType());
            mediaPers.setUrl(media.getUrl());
            mediaPers.setUpdatedBy(task);
            log.debug(msg+"found and try to update: "+media.toString());
            return this.mediaRepository.update(mediaPers);
        } catch (EmptyResultDataAccessException e) {
            media.setCreatedBy(task);
            log.debug(msg+"not found and try to persist: "+media.toString());
            return this.mediaRepository.persist(media);
        }
    }

}
