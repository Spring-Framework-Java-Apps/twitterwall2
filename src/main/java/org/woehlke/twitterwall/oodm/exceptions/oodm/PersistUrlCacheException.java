package org.woehlke.twitterwall.oodm.exceptions.oodm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;

/**
 * Created by tw on 25.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = " UrlCache persist raised Exception ") //404
public class PersistUrlCacheException extends OodmException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = PersistUrlCacheException.class.getCanonicalName()+" UrlCache persist raised Exception ";

    public PersistUrlCacheException(String screenName) {
        super(MSG + screenName);
    }

}
