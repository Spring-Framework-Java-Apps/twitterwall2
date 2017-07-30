package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.common.UniqueId;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.listener.UrlCacheListener;
import org.woehlke.twitterwall.oodm.entities.parts.UrlField;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


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
public class UrlCache extends AbstractDomainObject<UrlCache> implements DomainObjectWithUrl<UrlCache>,DomainObjectWithTask<UrlCache>,UniqueId {

    private static final long serialVersionUID = 1L;

    public static final String UNDEFINED = "UNDEFINED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length=4096)
    private String expanded = "";

    @Valid
    @NotNull
    @Embedded
    private UrlField url;

    public UrlCache(Task createdBy, Task updatedBy, String expanded, UrlField url) {
        super(createdBy,updatedBy);
        this.expanded = expanded;
        this.url = url;
    }

    public UrlCache(Task createdBy, Task updatedBy, UrlField url){
        super(createdBy,updatedBy);
        this.expanded = UrlCache.UNDEFINED;
        this.url = url;
    }

    private UrlCache(){
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getUniqueId() {
        return url.getUrl();
    }

    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("url",this.url);
        return parameters;
    }

    @Override
    public String getQueryNameForFindByUniqueId() {
        return "UrlCache.findByUniqueId";
    }


    @Transient
    public boolean isUrlAndExpandedTheSame(){
       return  url.getUrl().compareTo(expanded) == 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public UrlField getUrl() {
        return url;
    }

    @Override
    public void setUrl(UrlField url) {
        this.url = url;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
