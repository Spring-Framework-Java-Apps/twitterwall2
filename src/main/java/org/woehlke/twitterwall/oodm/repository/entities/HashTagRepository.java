package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagRepository {

    HashTag persist(HashTag hashTag);

    HashTag update(HashTag hashTag);

    List<HashTagCounted> getHashTags();

    HashTag findByText(String text);
}
