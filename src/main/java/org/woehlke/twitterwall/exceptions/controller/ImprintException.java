package org.woehlke.twitterwall.exceptions.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.woehlke.twitterwall.exceptions.common.TwitterwallException;


/**
 * Created by tw on 30.06.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ImprintException 404") //404
public class ImprintException extends TwitterwallException {
    
    private static final long serialVersionUID = -3332292346834265371L;

    public ImprintException(String s, EmptyResultDataAccessException ex) {
        super("Imprint Exception: " + s, ex);
    }
}
