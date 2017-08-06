package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.transients.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.transients.HashTagOverviewPaged;
import org.woehlke.twitterwall.oodm.repositories.HashTagRepository;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.service.HashTagService;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class HashTagServiceImpl extends DomainServiceWithTaskImpl<HashTag> implements HashTagService {

    private static final Logger log = LoggerFactory.getLogger(HashTagServiceImpl.class);

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository, TaskRepository taskRepository) {
        super(hashTagRepository,taskRepository);
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public HashTag findByText(String text) {
        return hashTagRepository.findByText(text);
    }

    /**
     * @param pageRequestHashTagsTweets org.springframework.data.domain.Pageable
     * @param pageRequestHashTagsUsers org.springframework.data.domain.Pageable
     * @return org.woehlke.twitterwall.oodm.entities.transients.HashTagOverviewPaged
     *
     * @see org.woehlke.twitterwall.oodm.entities.HashTag
     * @see org.woehlke.twitterwall.oodm.entities.parts.Entities
     * @see org.woehlke.twitterwall.oodm.entities.transients.mapper.CountAllTweets2HashTagsRowMapper#SQL_COUNT_ALL_TWEET_2_HASHTAG
     * @see org.woehlke.twitterwall.oodm.entities.transients.mapper.CountAllUsers2HashTagsRowMapper#SQL_COUNT_ALL_USER_2_HASHTAG
     * @see org.woehlke.twitterwall.oodm.entities.transients.HashTagOverviewPaged
     * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllTweet2HashTag(Pageable)
     * @see org.woehlke.twitterwall.oodm.repositories.custom.impl.HashTagRepositoryImpl#countAllUser2HashTag(Pageable)
     * @see org.woehlke.twitterwall.oodm.service.impl.HashTagServiceImpl#getHashTagOverview(Pageable, Pageable)
     */
    @Override
    public HashTagOverviewPaged getHashTagOverview(Pageable pageRequestHashTagsTweets, Pageable pageRequestHashTagsUsers) {
        Page<HashTagCounted> hashTagsTweets = hashTagRepository.countAllTweet2HashTag(pageRequestHashTagsTweets);
        Page<HashTagCounted> hashTagsUsers = hashTagRepository.countAllUser2HashTag(pageRequestHashTagsUsers);
        return new HashTagOverviewPaged(hashTagsTweets,hashTagsUsers);
    }

}
