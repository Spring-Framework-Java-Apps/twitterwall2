package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.UrlListener;
import org.woehlke.twitterwall.oodm.entities.parts.UrlField;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
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
        name="Url.findByUrl",
        query="select t from Url t where t.urlField.url=:url"
    ),
    @NamedQuery(
        name="Url.findByUniqueId",
        query="select t from Url t where t.urlField.url=:url"
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
    private UrlField urlField;

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        if(urlField == null){
            return false;
        }
        if(expanded == null){
            return false;
        }
        if(this.urlField.isValid()){
            return false;
        }
        if(this.expanded.isEmpty()){
            return false;
        }
        return urlField.getUrl().compareTo(expanded) == 0;
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

    @AssertTrue
    @Transient
    @Override
    public boolean isValid() {
        if(this.urlField == null){
            return false;
        }
        if(this.expanded == null){
            return false;
        }
        if(this.display == null){
            return false;
        }
        if(this.expanded.isEmpty()){
            return false;
        }
        if(this.display.isEmpty()){
            return false;
        }
        if(!this.urlField.isValid()){
            return false;
        }
        return true;
    }

    @Transient
    @Override
    public String getUniqueId() {
        return urlField.getUrl();
    }

    @Transient
    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("url",this.urlField.getUrl());
        return parameters;
    }

    @Transient
    @Override
    public String getQueryNameForFindByUniqueId() {
        return "Url.findByUniqueId";
    }

    public Url(Task createdBy, Task updatedBy,String display, String expanded, UrlField urlField) {
        super(createdBy,updatedBy);
        this.display = display;
        this.expanded = expanded;
        this.urlField = urlField;
    }

    public Url(Task createdBy, Task updatedBy,UrlField urlField) {
        super(createdBy,updatedBy);
        this.display = Url.UNDEFINED;
        this.expanded = Url.UNDEFINED;
        this.urlField = urlField;
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
        return urlField;
    }

    public void setUrl(UrlField urlField) {
        this.urlField = urlField;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", urlField='" + urlField + '\'' +
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
