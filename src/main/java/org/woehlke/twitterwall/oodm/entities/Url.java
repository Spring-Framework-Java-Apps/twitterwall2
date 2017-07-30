package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.UrlListener;
import org.woehlke.twitterwall.oodm.entities.parts.UrlField;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

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

    @Valid
    @NotNull
    @Embedded
    private UrlField url;

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        if(url == null){
            return false;
        }
        if(expanded == null){
            return false;
        }
        if(this.url.isValid()){
            return false;
        }
        if(this.expanded.isEmpty()){
            return false;
        }
        return url.getUrl().compareTo(expanded) == 0;
    }

    @Transient
    public boolean isRawUrlsFromDescription() {
        if(this.display == null){
            return false;
        }
        if(this.expanded == null){
            return false;
        }
        if(this.display.isEmpty()){
            return false;
        }
        if(this.expanded.isEmpty()){
            return false;
        }
        return (this.display.compareTo(UNDEFINED)==0)&&(this.expanded.compareTo(UNDEFINED)==0);
    }

    @Transient
    @Override
    public boolean isValid() {
        if(this.url == null){
            return false;
        }
        if(this.expanded == null){
            return false;
        }
        if(this.display == null){
            return false;
        }
        if(!this.url.isValid()){
            return false;
        }
        if(this.expanded.isEmpty()){
            return false;
        }
        if(this.display.isEmpty()){
            return false;
        }
        boolean isInvalid = this.isRawUrlsFromDescription()||this.isUrlAndExpandedTheSame();
        return !isInvalid;
    }

    @Transient
    @Override
    public String getUniqueId() {
        return url.getUrl();
    }

    @Transient
    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("url",this.url);
        return parameters;
    }

    @Transient
    @Override
    public String getQueryNameForFindByUniqueId() {
        return "Url.findByUniqueId";
    }

    public Url(Task createdBy, Task updatedBy,String display, String expanded, UrlField url) {
        super(createdBy,updatedBy);
        this.display = display;
        this.expanded = expanded;
        this.url = url;
    }

    public Url(Task createdBy, Task updatedBy,UrlField url) {
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

    @Override
    public UrlField getUrl() {
        return url;
    }

    public void setUrl(UrlField url) {
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
