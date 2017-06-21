package org.woehlke.twitterwall.oodm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by tw on 21.06.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Fetch Url Exception") //404
public class FetchUrlException extends RuntimeException {

    private static final long serialVersionUID = -3332292346834265371L;

    private static final String MSG = "Fetch Url Exception for urlSrc = ";

    public FetchUrlException(String urlSrc,IOException e){
        super(MSG+urlSrc,e);
    }

    public FetchUrlException(String urlSrc,URISyntaxException e){
        super(MSG+urlSrc,e);
    }

    public FetchUrlException(String urlSrc,NullPointerException e){
        super(MSG+urlSrc,e);
    }
}
