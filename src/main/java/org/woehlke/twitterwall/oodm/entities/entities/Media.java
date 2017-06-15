package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.AbstractTwitterObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="media",uniqueConstraints=@UniqueConstraint(columnNames={"display","expanded","media_http","media_https","media_type"}))
public class Media extends AbstractTwitterObject implements Serializable,Comparable<Media> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private long idTwitter;

    @Column(name="media_http")
    private String mediaHttp;

    @Column(name="media_https")
    private String mediaHttps;

    @Column
    private String url;

    @Column
    private String display;

    @Column
    private String expanded;

    @Column(name="media_type")
    private String mediaType;

    @Transient
    private int[] indices;

    public Media(long idTwitter, String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType, int[] indices) {
        this.idTwitter = idTwitter;
        this.mediaHttp = mediaHttp;
        this.mediaHttps = mediaHttps;
        this.url = url;
        this.display = display;
        this.expanded = expanded;
        this.mediaType = mediaType;
        this.indices = indices;
    }

    private Media() {
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

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
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
        return Long.compare(idTwitter,other.getIdTwitter());
    }
}
