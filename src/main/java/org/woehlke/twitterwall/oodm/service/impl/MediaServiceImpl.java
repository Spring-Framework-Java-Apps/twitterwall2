package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.repositories.MediaRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.MediaService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MediaServiceImpl extends DomainServiceWithTaskImpl<Media> implements MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, TaskRepository taskRepository) {
        super(mediaRepository,taskRepository);
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media findByIdTwitter(long idTwitter) {
        return mediaRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Media findByUrl(String url) {
        return mediaRepository.findByUrl(url);
    }

}
