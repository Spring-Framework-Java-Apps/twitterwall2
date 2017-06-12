package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tw on 10.06.17.
 */
@Entity
public class MyMentionEntity extends MyTwitterObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true,nullable=false)
    private long idTwitter;

    @Column
    private String screenName;

    @Column
    private String name;

    @Column
    private int[] indices;

    public MyMentionEntity(long idTwitter, String screenName, String name, int[] indices) {
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
        this.indices = indices;
    }

    private MyMentionEntity() {
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
        if (!(o instanceof MyMentionEntity)) return false;

        MyMentionEntity that = (MyMentionEntity) o;

        if (idTwitter != that.idTwitter) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return Arrays.equals(indices, that.indices);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (idTwitter ^ (idTwitter >>> 32));
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(indices);
        return result;
    }
}
