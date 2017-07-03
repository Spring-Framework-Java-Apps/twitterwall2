package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.listener.entities.HashTagListener;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "hashtag", uniqueConstraints = {
        @UniqueConstraint(name="unique_hashtag",columnNames = {"text"})
})
@NamedQueries({
        @NamedQuery(
                name = "HashTag.findByText",
                query = "select t from HashTag as t where t.text=:text"
        ),
        @NamedQuery(
                name = "HashTag.getAll",
                query = "select h from HashTag as h"
        ),
        @NamedQuery(
                name = "HashTag.count" ,
                query = "select count(h) from HashTag as h"
        )
})
@EntityListeners(HashTagListener.class)
public class HashTag extends AbstractTwitterObject<HashTag> implements DomainObject<HashTag> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public final static String HASHTAG_TEXT_PATTERN = "[öÖäÄüÜßa-zA-Z0-9_]{1,139}";

    public static boolean isValidText(String hashtagText){
        //Pattern p = Pattern.compile("^"+HASHTAG_TEXT_PATTERN+"$");
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        return m.matches();
    }

    @Column(nullable = false)
    private String text;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getText() {
        return this.text;
    }

    public HashTag(String text, int[] indices) {
        super(indices);
        this.text = text;
    }

    private HashTag() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setText(String text) {
        this.text = text;
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
