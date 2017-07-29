package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.MediaListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "media",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_media_id_twitter", columnNames = {"id_twitter"})
    },
    indexes = {
        @Index(name="idx_media_url", columnList= "url"),
        @Index(name="idx_media_expanded", columnList="expanded"),
        @Index(name="idx_media_display", columnList="display") ,
        @Index(name="idx_media_media_http", columnList="media_http"),
        @Index(name="idx_media_media_https", columnList="media_https"),
        @Index(name="idx_media_media_type", columnList="media_type")
    }
)
@NamedQueries({
    @NamedQuery(
        name="Media.findByUniqueId",
        query="select t from Media t where t.idTwitter=:idTwitter"
    )
})
@EntityListeners(MediaListener.class)
public class Media extends AbstractDomainObject<Media> implements DomainObjectEntity<Media>,DomainObjectWithIdTwitter<Media>,DomainObjectWithUrl<Media>,DomainObjectWithTask<Media> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Column(name="id_twitter", nullable = false)
    private Long idTwitter;

    @NotNull
    @Column(name = "media_http",length=4096, nullable = false)
    private String mediaHttp = "";

    @NotNull
    @Column(name = "media_https",length=4096, nullable = false)
    private String mediaHttps = "";

    @URL
    @NotEmpty
    @Column(length=4096, nullable = false)
    private String url;

    @NotNull
    @Column(length=4096, nullable = false)
    private String display = "";

    @NotNull
    @Column(length=4096, nullable = false)
    private String expanded = "";

    @NotNull
    @Column(name = "media_type",length=4096, nullable = false)
    private String mediaType = "";


    public Media(Task createdBy, Task updatedBy, long idTwitter, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.mediaHttp = mediaHttp;
        this.mediaHttps = mediaHttps;
        this.url = url;
        this.display = display;
        this.expanded = expanded;
        this.mediaType = mediaType;
    }

    public Media(Task createdBy, Task updatedBy, String url){
        super(createdBy,updatedBy);
        this.idTwitter = -1L;
        this.mediaHttp = "UNKNOWN";
        this.mediaHttps  = "UNKNOWN";
        this.display = "UNKNOWN";
        this.expanded = "UNKNOWN";
        this.mediaType = "UNKNOWN";
        this.url=url;
    }

    private Media() {
        super();
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
        return idTwitter.toString();
    }

    @Override
    public Long getIdTwitter() {
        return idTwitter;
    }

    @Override
    public void setIdTwitter(Long idTwitter) {
        this.idTwitter = idTwitter;
    }

    public String getMediaHttp() {
        return mediaHttp;
    }

    public void setMediaHttp(String mediaHttp) {
        this.mediaHttp = mediaHttp;
    }

    public String getMediaHttps() {
        return mediaHttps;
    }

    public void setMediaHttps(String mediaHttps) {
        this.mediaHttps = mediaHttps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        return "Media{" +
                " id=" + id +
                ", idTwitter=" + idTwitter +
                ", mediaHttp='" + mediaHttp + '\'' +
                ", mediaHttps='" + mediaHttps + '\'' +
                ", url='" + url + '\'' +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", mediaType='" + mediaType + '\'' +
                    super.toString() +
                " }\n";
    }

    @Override
    public boolean isValid() {
        return true;
    }


}
