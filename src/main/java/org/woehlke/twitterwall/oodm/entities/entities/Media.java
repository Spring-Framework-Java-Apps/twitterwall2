package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.listener.entities.MediaListener;

import javax.persistence.*;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "media", uniqueConstraints = {
        @UniqueConstraint(name="unique_media_id_twitter", columnNames = {"id_twitter"})
}, indexes = {
        @Index(name="idx_media_url", columnList= "url"),
        @Index(name="idx_media_expanded", columnList="expanded"),
        @Index(name="idx_media_display", columnList="display") ,
        @Index(name="idx_media_media_http", columnList="media_http"),
        @Index(name="idx_media_media_https", columnList="media_https"),
        @Index(name="idx_media_media_type", columnList="media_type")
})
@NamedQueries({
        @NamedQuery(
                name = "Media.findByIdTwitter",
                query = "select t from Media as t where t.idTwitter=:idTwitter"
        ),
        @NamedQuery(
            name = "Media.count",
            query = "select count(t) from Media as t"
        ),
        @NamedQuery(
                name = "Media.getAll",
                query = "select t from Media as t"
        ),
        @NamedQuery(
                name = "Media.findByUrl",
                query = "select t from Media as t where t.url=:url"
        )
})
@EntityListeners(MediaListener.class)
public class Media extends AbstractTwitterObject<Media> implements DomainObjectWithIdTwitter<Media>,DomainObjectWithUrl<Media> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task updatedBy;

    @Column(name="id_twitter", nullable = false)
    private long idTwitter;

    @Column(name = "media_http")
    private String mediaHttp;

    @Column(name = "media_https")
    private String mediaHttps;

    @Column
    private String url;

    @Column
    private String display;

    @Column
    private String expanded;

    @Column(name = "media_type")
    private String mediaType;

    /*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "media_indices", joinColumns = @JoinColumn(name = "id"))
    protected List<Integer> indices = new ArrayList<>();

    public void setIndices(int[] indices) {
        this.indices.clear();
        for(Integer index: indices){
            this.indices.add(index);
        }
    }*/

    public Media(long idTwitter, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType, int[] indices) {
        //setIndices(indices);
        this.idTwitter = idTwitter;
        this.mediaHttp = mediaHttp;
        this.mediaHttps = mediaHttps;
        this.url = url;
        this.display = display;
        this.expanded = expanded;
        this.mediaType = mediaType;
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

    public long getIdTwitter() {
        return idTwitter;
    }

    public void setIdTwitter(long idTwitter) {
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

    public Task getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Task createdBy) {
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
        this.updatedBy = updatedBy;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Media)) return false;
        if (!super.equals(o)) return false;

        Media media = (Media) o;

        if (mediaHttp != null ? !mediaHttp.equals(media.mediaHttp) : media.mediaHttp != null) return false;
        if (mediaHttps != null ? !mediaHttps.equals(media.mediaHttps) : media.mediaHttps != null) return false;
        if (url != null ? !url.equals(media.url) : media.url != null) return false;
        if (display != null ? !display.equals(media.display) : media.display != null) return false;
        if (expanded != null ? !expanded.equals(media.expanded) : media.expanded != null) return false;
        return mediaType != null ? mediaType.equals(media.mediaType) : media.mediaType == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mediaHttp != null ? mediaHttp.hashCode() : 0);
        result = 31 * result + (mediaHttps != null ? mediaHttps.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (display != null ? display.hashCode() : 0);
        result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
        result = 31 * result + (mediaType != null ? mediaType.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Media other) {
        return Long.compare(idTwitter, other.getIdTwitter());
    }

    @Override
    public String toString() {/*
        StringBuffer myIndieces = new StringBuffer();
        myIndieces.append("[ ");
        for (Integer index : indices) {
            myIndieces.append(index.toString());
            myIndieces.append(", ");
        }
        myIndieces.append(" ]");*/
        return "Media{" +
                "id=" + id +
                ", idTwitter=" + idTwitter +
                ", mediaHttp='" + mediaHttp + '\'' +
                ", mediaHttps='" + mediaHttps + '\'' +
                ", url='" + url + '\'' +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", mediaType='" + mediaType + '\'' +
               // ", indices=" + myIndieces.toString() +
                '}';
    }
}
