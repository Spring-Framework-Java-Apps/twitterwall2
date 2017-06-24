package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.repository.entities.MediaRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Media store(Media media) {
        return this.mediaRepository.persist(media);
    }

    @Override
    public Media findByIdTwitter(long idTwitter) {
        return this.mediaRepository.findByIdTwitter(idTwitter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Media update(Media media) {
        return this.mediaRepository.update(media);
    }

    @Override
    public Media findByFields(Media media) {
        return this.mediaRepository.findByFields(media.getMediaHttp(), media.getMediaHttps(), media.getUrl(), media.getDisplay(), media.getExpanded(), media.getMediaType());
    }
}
