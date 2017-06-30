package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;


/**
 * Created by tw on 25.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Mention Not Found") //404
public class FindMentionByScreenNameException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = FindMentionByScreenNameException.class.getCanonicalName()+" Mention not found for screenName=";

    public FindMentionByScreenNameException(EmptyResultDataAccessException e, String screenName) {
        super(MSG + screenName + " " + e.getMessage(), e);
    }
}
