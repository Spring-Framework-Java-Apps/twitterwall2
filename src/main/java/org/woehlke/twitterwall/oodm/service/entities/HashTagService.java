package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.HashTagEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagService extends OodmService {

    HashTag create(HashTag tag);

    HashTag update(HashTag tag);

    HashTag findByText(String text);

    List<HashTag> getAll();

    HashTag store(HashTag hashTag);

    Set<HashTag> transformTwitterEntitiesHashTags(List<HashTagEntity> hashTags);

    Set<HashTag> getHashTagsFor(User user);

    //HashTag storeHashTag(HashTag hashTag);
}
