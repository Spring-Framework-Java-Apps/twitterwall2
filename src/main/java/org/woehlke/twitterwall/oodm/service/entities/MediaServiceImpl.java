package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMediaByFieldsExceptionException;
import org.woehlke.twitterwall.oodm.repository.entities.MediaRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Media create(Media media) {
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Media store(Media media) {
        try {
            Media mediaPers = this.mediaRepository.findByFields(media.getMediaHttp(), media.getMediaHttps(), media.getUrl(), media.getDisplay(), media.getExpanded(), media.getMediaType());
            mediaPers.setDisplay(media.getDisplay());
            mediaPers.setExpanded(media.getExpanded());
            mediaPers.setIdTwitter(media.getIdTwitter());
            mediaPers.setIndices(media.getIndices());
            mediaPers.setMediaHttp(media.getMediaHttp());
            mediaPers.setMediaHttps(media.getMediaHttps());
            mediaPers.setMediaType(media.getMediaType());
            mediaPers.setUrl(media.getUrl());
            return this.mediaRepository.update(mediaPers);
        } catch (FindMediaByFieldsExceptionException e) {
            return this.mediaRepository.persist(media);
        }
    }

    @Override
    public Set<Media> transformTwitterEntitiesMedia(List<MediaEntity> media) {
        Set<Media> myMediaEntities = new LinkedHashSet<Media>();
        for (MediaEntity medium : media) {
            long idTwitter = medium.getId();
            String mediaHttp = medium.getMediaUrl();
            String mediaHttps = medium.getMediaSecureUrl();
            String url = medium.getUrl();
            String display = medium.getDisplayUrl();
            String expanded = medium.getExpandedUrl();
            String type = medium.getType();
            int[] indices = medium.getIndices();
            Media myMediaEntity = new Media(idTwitter, mediaHttp, mediaHttps, url, display, expanded, type, indices);
            myMediaEntities.add(myMediaEntity);
        }
        return myMediaEntities;
    }
}
