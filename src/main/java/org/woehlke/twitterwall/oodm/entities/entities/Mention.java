package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "mention", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_twitter"}),
        @UniqueConstraint(columnNames = {"screen_name", "name"}),
        @UniqueConstraint(columnNames = {"id_twitter", "screen_name", "name"})
})
public class Mention extends AbstractTwitterObject implements DomainObject, Comparable<Mention> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_twitter")
    private long idTwitter;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "name")
    private String name;

    @Transient
    private int[] indices;

    public Mention(long idTwitter, String screenName, String name, int[] indices) {
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
        this.indices = indices;
    }

    private Mention() {
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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof Mention)) return false;
        if (!super.equals(o)) return false;

        Mention mention = (Mention) o;

        if (screenName != null ? !screenName.equals(mention.screenName) : mention.screenName != null) return false;
        return name != null ? name.equals(mention.name) : mention.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Mention other) {
        return screenName.compareTo(other.getScreenName());
    }

    @Override
    public String toString() {
        return "Mention{" +
                "id=" + id +
                ", idTwitter=" + idTwitter +
                ", screenName='" + screenName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
