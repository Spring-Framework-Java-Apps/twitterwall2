package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

/**
 * Created by tw on 17.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Media Not Found") //404
public class FindMediaByFieldsExceptionException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    public FindMediaByFieldsExceptionException(NoResultException e, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType){
        super("Media not found for mediaHttp="+mediaHttp+", mediaHttps="+mediaHttps+",url="+url+",display="+display+",expanded="+expanded+",mediaType="+mediaType,e);
    }
}
