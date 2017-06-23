package org.woehlke.twitterwall.oodm.entities.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tw on 23.06.17.
 */
@Entity
@Table(name="url_cache",uniqueConstraints=@UniqueConstraint(columnNames={"expanded","url"}))
public class UrlCache implements Serializable,Comparable<Url> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String expanded;

    @Column
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlCache)) return false;

        UrlCache urlCache = (UrlCache) o;

        if (!expanded.equals(urlCache.expanded)) return false;
        return url.equals(urlCache.url);
    }

    @Override
    public int hashCode() {
        int result = expanded.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public int compareTo(Url other) {
        return url.compareTo(other.getUrl());
    }

    @Override
    public String toString() {
        return "UrlCache{" +
                "id=" + id +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
