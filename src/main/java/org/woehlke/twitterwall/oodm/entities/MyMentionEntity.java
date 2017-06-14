package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="mention")
public class MyMentionEntity extends MyTwitterObject implements Serializable,Comparable<MyMentionEntity> {

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
        if (!super.equals(o)) return false;

        MyMentionEntity that = (MyMentionEntity) o;

        return idTwitter == that.idTwitter;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (idTwitter ^ (idTwitter >>> 32));
        return result;
    }

    @Override
    public int compareTo(MyMentionEntity other) {
        return screenName.compareTo(other.getScreenName());
    }
}
