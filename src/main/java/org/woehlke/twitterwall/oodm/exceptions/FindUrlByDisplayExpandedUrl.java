package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Url Not Found") //404
public class FindUrlByDisplayExpandedUrl extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindUrlByDisplayExpandedUrl(NoResultException e, String display, String expanded, String url){
        super("Url not found for display="+display+",expanded="+expanded+",url="+url,e);
    }
}
