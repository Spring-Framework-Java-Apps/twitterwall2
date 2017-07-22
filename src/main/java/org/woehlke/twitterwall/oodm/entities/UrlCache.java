package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.listener.UrlCacheListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Created by tw on 23.06.17.
 */
@Entity
@Table(
    name = "url_cache",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_url_cache", columnNames = {"url"})
    },
    indexes = {
        @Index(name="idx_url_cache_expanded", columnList="expanded")
    }
)
@NamedQueries({
    @NamedQuery(
        name="UrlCache.findByUniqueId",
        query="select t from UrlCache t where t.url=:url"
    )
})
@EntityListeners(UrlCacheListener.class)
public class UrlCache extends AbstractDomainObject<UrlCache> implements DomainObjectWithUrl<UrlCache>,DomainObjectWithTask<UrlCache> {

    private static final long serialVersionUID = 1L;

    public static final String UNDEFINED = "UNDEFINED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length=4096)
    private String expanded = "";

    @URL
    @NotEmpty
    @Column(name="url",nullable = false,length=4096)
    private String url;

    public UrlCache(Task createdBy, Task updatedBy, String expanded, String url) {
        super(createdBy,updatedBy);
        this.expanded = expanded;
        this.url = url;
    }

    public UrlCache(Task createdBy, Task updatedBy, String url){
        super(createdBy,updatedBy);
        this.expanded = UrlCache.UNDEFINED;
        this.url = url;
    }

    private UrlCache(){
    }

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

    @Override
    public String getUniqueId() {
        return url;
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
    public String toString() {
        return "UrlCache{" +
                " id=" + id +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
                    super.toString() +
                "}\n";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlCache)) return false;
        if (!super.equals(o)) return false;

        UrlCache urlCache = (UrlCache) o;

        if (getId() != null ? !getId().equals(urlCache.getId()) : urlCache.getId() != null) return false;
        return getUrl() != null ? getUrl().equals(urlCache.getUrl()) : urlCache.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }
}
