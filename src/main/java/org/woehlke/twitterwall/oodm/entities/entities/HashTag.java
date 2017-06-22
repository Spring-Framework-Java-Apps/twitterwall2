package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.AbstractTwitterObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="hashtag",uniqueConstraints=@UniqueConstraint(columnNames={"text"}))
public class HashTag extends AbstractTwitterObject implements Serializable,Comparable<HashTag> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Transient
    private int[] indices;

    public String getText() {
        return this.text;
    }

    public HashTag(String text, int[] indices) {
        this.text = text;
        this.indices = indices;
    }

    private HashTag() {
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
        if (!(o instanceof HashTag)) return false;
        if (!super.equals(o)) return false;

        HashTag hashTag = (HashTag) o;

        return text != null ? text.equals(hashTag.text) : hashTag.text == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(HashTag other) {
        return text.compareTo(other.getText());
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
