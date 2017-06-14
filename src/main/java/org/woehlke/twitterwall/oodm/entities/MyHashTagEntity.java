package org.woehlke.twitterwall.oodm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="hashtag")
public class MyHashTagEntity extends MyTwitterObject implements Serializable,Comparable<MyHashTagEntity> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column
    private int[] indices;

    public String getText() {
        return this.text;
    }

    public MyHashTagEntity(String text, int[] indices) {
        this.text = text;
        this.indices = indices;
    }

    private MyHashTagEntity() {
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

    public void setText(String text) {
        this.text = text;
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
        if (!(o instanceof MyHashTagEntity)) return false;
        if (!super.equals(o)) return false;

        MyHashTagEntity that = (MyHashTagEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return Arrays.equals(indices, that.indices);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(indices);
        return result;
    }

    @Override
    public int compareTo(MyHashTagEntity other) {
        return text.compareTo(other.getText());
    }
}
