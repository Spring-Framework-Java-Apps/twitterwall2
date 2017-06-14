package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="url")
public class MyUrlEntity extends MyTwitterObject implements Serializable,Comparable<MyUrlEntity>  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String display;

    @Column
    private String expanded;

    @Column
    private String url;

    @Column
    private int[] indices;

    public MyUrlEntity(String display, String expanded, String url, int[] indices) {
        this.display = display;
        this.expanded = expanded;
        this.url = url;
        this.indices = indices;
    }

    private MyUrlEntity() {
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
        if (!(o instanceof MyUrlEntity)) return false;
        if (!super.equals(o)) return false;

        MyUrlEntity that = (MyUrlEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (display != null ? !display.equals(that.display) : that.display != null) return false;
        if (expanded != null ? !expanded.equals(that.expanded) : that.expanded != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return Arrays.equals(indices, that.indices);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (display != null ? display.hashCode() : 0);
        result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(indices);
        return result;
    }

    @Override
    public int compareTo(MyUrlEntity other) {
        return display.compareTo(other.getDisplay());
    }
}
