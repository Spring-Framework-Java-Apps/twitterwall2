package org.woehlke.twitterwall.oodm.exceptions.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by tw on 24.06.17.
 */
public abstract class TwitterwallException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(TwitterwallException.class);

    protected TwitterwallException(String urlSrc) {
        super(urlSrc);
        log.debug("......................................................");
        log.info(urlSrc);
        log.debug("......................................................");
    }

    protected TwitterwallException(String urlSrc, Exception ex) {
        super(urlSrc, ex);
        writeLogDebug(urlSrc, ex);
    }

    protected TwitterwallException(String urlSrc, RuntimeException ex) {
        super(urlSrc, ex);
        writeLogDebug(urlSrc, ex);
    }

    protected TwitterwallException(String urlSrc, PersistenceException ex) {
        super(urlSrc, ex);
        writeLogDebug(urlSrc, ex);
    }

    protected TwitterwallException(String urlSrc, IOException ex) {
        super(urlSrc, ex);
        writeLogDebug(urlSrc, ex);
    }

    protected TwitterwallException(String urlSrc, URISyntaxException ex) {
        super(urlSrc, ex);
        writeLogDebug(urlSrc, ex);
    }

    protected TwitterwallException(String urlSrc, NullPointerException ex) {
        super(urlSrc, ex);
        writeLogDebug(urlSrc, ex);
    }

    private void writeLogDebug(String msg, Exception ex) {
            log.debug(msg + "......................................................");
            log.info(msg + ex.getMessage());
            writeLogStracktrace(msg, ex);
    }

    private void writeLogStracktrace(String msg, Exception ex) {
        if (log.isDebugEnabled()) {
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            log.debug(msg + "------------------------------------------------------");
            log.debug(msg + "ClassName:         " + stackTraceElement.getClassName());
            log.debug(msg + "MethodName:        " + stackTraceElement.getMethodName());
            log.debug(msg + "FileName:          " + stackTraceElement.getFileName());
            log.debug(msg + "LineNumber:        " + stackTraceElement.getLineNumber());
            log.debug(msg + "is a native method:" + stackTraceElement.isNativeMethod());
        }
        log.debug(msg + "......................................................");
        }
    }
}
