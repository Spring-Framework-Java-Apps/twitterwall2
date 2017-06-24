package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.common.OodmRepository;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagRepository extends OodmRepository {

    HashTag persist(HashTag hashTag);

    HashTag update(HashTag hashTag);

    HashTag findByText(String text);

    List<HashTag> getAll();
}
