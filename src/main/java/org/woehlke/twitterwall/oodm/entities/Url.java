package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.UrlListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "url",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_url", columnNames = {"url"})
    },
    indexes = {
        @Index(name="idx_url_expanded", columnList="expanded"),
        @Index(name="idx_url_display", columnList="display")
    }
)
@NamedQueries({
    @NamedQuery(
        name="Url.findByUniqueId",
        query="select t from Url t where t.url=:url"
    )
})
@EntityListeners(UrlListener.class)
public class Url extends AbstractDomainObject<Url> implements DomainObjectEntity<Url>,DomainObjectWithUrl<Url>,DomainObjectWithTask<Url> {

    private static final long serialVersionUID = 1L;

    public static final String UNDEFINED = "UNDEFINED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Column(length=4096,nullable = false)
    private String display="";

    @URL
    @NotNull
    @Column(length=4096,nullable = false)
    private String expanded="";

    public static final String URL_PATTTERN_FOR_USER_HTTPS = "https://t\\.co/\\w*";

    public static final String URL_PATTTERN_FOR_USER_HTTP = "http://t\\.co/\\w*";

    @URL
    @NotEmpty
    @Column(nullable = false,length=4096)
    private String url;

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        return url.compareTo(expanded) == 0;
    }

    public Url(Task createdBy, Task updatedBy,String display, String expanded, String url) {
        super(createdBy,updatedBy);
        this.display = display;
        this.expanded = expanded;
        this.url = url;
    }

    public Url(Task createdBy, Task updatedBy,String url) {
        super(createdBy,updatedBy);
        this.display = Url.UNDEFINED;
        this.expanded = Url.UNDEFINED;
        this.url = url;
    }

    private Url() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
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
        return "Url{" +
                "id=" + id +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
                    super.toString() +
                "}\n";
    }

    @Override
    public boolean isValid() {
        boolean isInvalid = (this.url == null)||(this.url.isEmpty()||isRawUrlsFromDescription())||(this.url.compareTo(this.expanded)==0);
        return !isInvalid;
    }

    public boolean isRawUrlsFromDescription() {
        return (this.getDisplay().compareTo(UNDEFINED)==0)&&(this.getExpanded().compareTo(UNDEFINED)==0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Url)) return false;
        if (!super.equals(o)) return false;

        Url url1 = (Url) o;

        if (getId() != null ? !getId().equals(url1.getId()) : url1.getId() != null) return false;
        return getUrl() != null ? getUrl().equals(url1.getUrl()) : url1.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }
}
