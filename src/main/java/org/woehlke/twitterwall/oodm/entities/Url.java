package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.UrlListener;
import org.woehlke.twitterwall.oodm.entities.parts.TwitterApiCaching;

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
        @Index(name="idx_url_display", columnList="display"),
        @Index(name="idx_url_fetch_tweets_from_twitter_search", columnList="remote_api_cache_fetch_tweets_from_twitter_search"),
        @Index(name="idx_url_update_tweets", columnList="remote_api_cache_update_tweets"),
        @Index(name="idx_url_update_user_profiles", columnList="remote_api_cache_update_user_profiles"),
        @Index(name="idx_url_update_user_profiles_from_mentions", columnList="remote_api_cache_update_user_profiles_from_mentions"),
        @Index(name="idx_url_fetch_users_from_defined_user_list", columnList="remote_api_cache_fetch_users_from_defined_user_list"),
        @Index(name="idx_url_controller_get_testdata_tweets", columnList="remote_api_cache_controller_get_testdata_tweets"),
        @Index(name="idx_url_controller_get_testdata_user", columnList="remote_api_cache_controller_get_testdata_user"),
        @Index(name="idx_url_controller_add_user_for_screen_name", columnList="remote_api_cache_controller_add_user_for_screen_name"),
        @Index(name="idx_url_ontroller_create_imprint_user", columnList="remote_api_cache_controller_create_imprint_user")
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

    @Valid
    @NotNull
    @Embedded
    private TwitterApiCaching twitterApiCaching = new TwitterApiCaching();

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

    public Url(Task createdBy, Task updatedBy,String display, String expanded, String url) {
        super(createdBy,updatedBy);
        this.display = display;
        this.expanded = expanded;
        this.url = url;
        if(updatedBy != null){
            twitterApiCaching.store(updatedBy.getTaskType());
        } else {
            twitterApiCaching.store(createdBy.getTaskType());
        }
    }

    public Url(Task createdBy, Task updatedBy,String url) {
        super(createdBy,updatedBy);
        this.display = Url.UNDEFINED;
        this.expanded = Url.UNDEFINED;
        this.url = url;
        if(updatedBy != null){
            twitterApiCaching.store(updatedBy.getTaskType());
        } else {
            twitterApiCaching.store(createdBy.getTaskType());
        }
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

    public TwitterApiCaching getTwitterApiCaching() {
        return twitterApiCaching;
    }

    public void setTwitterApiCaching(TwitterApiCaching twitterApiCaching) {
        this.twitterApiCaching = twitterApiCaching;
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
