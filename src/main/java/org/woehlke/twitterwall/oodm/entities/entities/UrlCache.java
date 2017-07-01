package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;

import javax.persistence.*;

/**
 * Created by tw on 23.06.17.
 */
@Entity
@Table(name = "url_cache",
        uniqueConstraints = { @UniqueConstraint(name="unique_url_cache", columnNames = {"url"})
}, indexes = {
        @Index(name="idx_url_cache_expanded", columnList="expanded")
})
@NamedQueries({
        @NamedQuery(
                name = "UrlCache.findByUrl",
                query = "select t from UrlCache as t where t.url=:url"
        )
})
public class UrlCache implements DomainObjectWithUrl<UrlCache> {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=2048)
    private String expanded;

    @Column(nullable = false,length=1024)
    private String url;

    @Transient
    public boolean isUrlAndExpandedTheSame(){
       return  url.compareTo(expanded) == 0;
    }

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
    public boolean equals(UrlCache o) {
        if (this == o) return true;
        if (!(o instanceof UrlCache)) return false;

        UrlCache urlCache = (UrlCache) o;

        return url.equals(urlCache.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public int compareTo(UrlCache other) {
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
