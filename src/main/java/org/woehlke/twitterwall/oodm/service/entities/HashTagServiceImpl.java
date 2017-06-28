package org.woehlke.twitterwall.oodm.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.common.AbstractFormattedText;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindHashTagByTextException;
import org.woehlke.twitterwall.oodm.repository.entities.HashTagRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.HashTag.HASHTAG_TEXT_PATTERN;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class HashTagServiceImpl implements HashTagService {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceImpl.class);

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public HashTag create(HashTag tag) {
        return this.hashTagRepository.persist(tag);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public HashTag update(HashTag tag) {
        return this.hashTagRepository.update(tag);
    }

    @Override
    public HashTag findByText(String text) {
        return this.hashTagRepository.findByText(text);
    }

    @Override
    public List<HashTag> getAll() {
        return this.hashTagRepository.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public HashTag store(HashTag hashTag) {
        try {
            HashTag tagPers = this.hashTagRepository.findByText(hashTag.getText());
            log.info("found: "+tagPers.toString());
            return tagPers;
            /*
            tagPers.setText(hashTag.getText());
            tagPers.setIndices(hashTag.getIndices());
            return this.hashTagRepository.update(tagPers);
            */
        } catch (FindHashTagByTextException e) {
            log.info("try to persist: "+hashTag.toString());
            HashTag tagPers = this.hashTagRepository.persist(hashTag);
            log.info("persisted: "+tagPers.toString());
            return tagPers;
        }
    }

    @Override
    public Set<HashTag> transformTwitterEntitiesHashTags(List<HashTagEntity> hashTags) {
        Set<HashTag> myHashTagEntities = new LinkedHashSet<>();
        for (HashTagEntity hashTag : hashTags) {
            String text = hashTag.getText();
            int[] indices = hashTag.getIndices();
            HashTag myHashTagEntity = new HashTag(text, indices);
            myHashTagEntities.add(myHashTagEntity);
        }
        return myHashTagEntities;
    }

    @Override
    public Set<HashTag> getHashTagsFor(User user) {
        String description = user.getDescription();
        int[] indices = {};
        Set<HashTag> hashTags = new LinkedHashSet<>();
        if (description != null) {
            Pattern hashTagPattern = Pattern.compile("#("+HASHTAG_TEXT_PATTERN+")(" + AbstractFormattedText.stopChar + ")");
            Matcher m3 = hashTagPattern.matcher(description);
            while (m3.find()) {
                hashTags.add(new HashTag(m3.group(1), indices));
            }
            Pattern hashTagPattern2 = Pattern.compile("#(\\w*)$");
            Matcher m4 = hashTagPattern2.matcher(description);
            while (m4.find()) {
                hashTags.add(new HashTag(m4.group(1), indices));
            }
        }
        return hashTags;
    }
}
