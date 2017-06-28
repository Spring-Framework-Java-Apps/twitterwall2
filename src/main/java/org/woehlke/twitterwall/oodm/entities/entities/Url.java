package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "url", uniqueConstraints = {
        @UniqueConstraint(name="unique_url", columnNames = {"url"})
}, indexes = {
        @Index(name="idx_url_expanded", columnList="expanded"),
        @Index(name="idx_url_display", columnList="display")
})
@NamedQueries({
        @NamedQuery(
                name="Url.findByDisplayExpandedUrl",
                query="select t from Url as t where t.display=:display and t.expanded=:expanded and t.url=:url"
        ),
        @NamedQuery(
                name="Url.findByUrl",
                query="select t from Url as t where t.url=:url"
        )
})
public class Url extends AbstractTwitterObject<Url> implements DomainObjectWithUrl<Url> {
    
    private static final long serialVersionUID = 1L;

    @Column
    private String display;

    @Column
    private String expanded;

    public static final String URL_PATTTERN_FOR_USER = "https://t\\.co/\\w*";

    @Column(nullable = false)
    private String url;

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        return url.compareTo(expanded) == 0;
    }

    public Url(String display, String expanded, String url, int[] indices) {
        this.display = display;
        this.expanded = expanded;
        this.url = url;
        this.indices = indices;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(o instanceof Url)) return false;
        if (!super.equals(o)) return false;

        Url url1 = (Url) o;

        return url.equals(url1.url);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public int compareTo(Url other) {
        return url.compareTo(other.getUrl());
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
