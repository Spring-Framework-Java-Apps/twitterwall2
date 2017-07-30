package org.woehlke.twitterwall.oodm.entities.parts;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEmbededField;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

@Validated
@Embeddable
public class UrlField implements DomainObjectEmbededField, Comparable<UrlField> {

    public static final String URL_PATTTERN_FOR_USER_HTTPS_TEXT = "https://t\\.co/\\w*";

    public static final String URL_PATTTERN_FOR_USER_HTTP_TEXT = "http://t\\.co/\\w*";

    public static final Pattern URL_PATTTERN_FOR_USER_HTTPS = Pattern.compile(URL_PATTTERN_FOR_USER_HTTPS_TEXT);

    public static final Pattern URL_PATTTERN_FOR_USER_HTTP = Pattern.compile(URL_PATTTERN_FOR_USER_HTTP_TEXT);

    @URL
    @NotEmpty
    @Column(nullable = false,length=4096)
    private String url;

    @Override
    public int compareTo(UrlField o) {
        return url.compareTo(o.getUrl());
    }

    @Override
    public boolean isValid() {
        try {
            java.net.URL myURL = new java.net.URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UrlField(String url) {
        this.url = url;
    }

    protected UrlField(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlField)) return false;

        UrlField urlField = (UrlField) o;

        return url != null ? url.equals(urlField.url) : urlField.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UrlField{" +
                "url='" + url + '\'' +
                '}';
    }
}
