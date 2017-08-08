package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.UrlListener;
import org.woehlke.twitterwall.oodm.entities.parts.TaskBasedCaching;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;

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

    @NotNull
    @Column(length=4096,nullable = false)
    private String expanded="";

    public static final String URL_PATTTERN_FOR_USER_HTTPS = "https://t\\.co/\\w*";

    public static final String URL_PATTTERN_FOR_USER_HTTP = "http://t\\.co/\\w*";

    @URL
    @NotEmpty
    @Column(nullable = false,length=4096)
    private String url;

    @Valid
    @NotNull
    @Embedded
    private TaskBasedCaching taskBasedCaching = new TaskBasedCaching();

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        if(this.isValid()){
            return url.compareTo(expanded) == 0;
        } else {
            return false;
        }
    }

    @Transient
    public boolean isRawUrlsFromDescription() {
        if(this.isValid()){
            return (this.display.compareTo(UNDEFINED)==0)&&(this.expanded.compareTo(UNDEFINED)==0);
        } else {
            return false;
        }
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
        if(this.url.isEmpty()){
            return false;
        }
        if(this.expanded.isEmpty()){
            return false;
        }
        if(this.display.isEmpty()){
            return false;
        }
        try {
           java.net.URL url = new java.net.URL(this.url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Override
    public String getUniqueId() {
        return url;
    }

    public Url(Task createdBy, Task updatedBy,String display, String expanded, String url) {
        super(createdBy,updatedBy);
        this.display = display;
        this.expanded = expanded;
        this.url = url;
        if(updatedBy != null){
            taskBasedCaching.store(updatedBy.getTaskType());
        } else {
            taskBasedCaching.store(createdBy.getTaskType());
        }
    }

    public Url(Task createdBy, Task updatedBy,String url) {
        super(createdBy,updatedBy);
        this.display = Url.UNDEFINED;
        this.expanded = Url.UNDEFINED;
        this.url = url;
        if(updatedBy != null){
            taskBasedCaching.store(updatedBy.getTaskType());
        } else {
            taskBasedCaching.store(createdBy.getTaskType());
        }
    }

    protected Url() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TaskBasedCaching getTaskBasedCaching() {
        return taskBasedCaching;
    }

    public void setTaskBasedCaching(TaskBasedCaching taskBasedCaching) {
        this.taskBasedCaching = taskBasedCaching;
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

        Url url1 = (Url) o;

        if (id != null ? !id.equals(url1.id) : url1.id != null) return false;
        return url != null ? url.equals(url1.url) : url1.url == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
