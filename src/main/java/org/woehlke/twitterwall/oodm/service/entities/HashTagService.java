package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
public interface HashTagService extends OodmService {

    HashTag store(HashTag tag);

    HashTag update(HashTag tag);

    HashTag findByText(String text);

    List<HashTag> getAll();
}
