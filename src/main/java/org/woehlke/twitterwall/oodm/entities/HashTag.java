package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.SafeHtml;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.HashTagListener;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "hashtag",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_hashtag",columnNames = {"text"})
    }
)
@NamedQueries({
    @NamedQuery(
        name="HashTag.findByUniqueId",
        query="select t from HashTag t where t.text=:text"
    )
})
@EntityListeners(HashTagListener.class)
public class HashTag extends AbstractDomainObject<HashTag> implements DomainObject<HashTag>,DomainObjectWithTask<HashTag> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @SafeHtml
    @Column(name="text", nullable = false,length=4096)
    private String text = "";

    public HashTag(Task createdBy, Task updatedBy, String text) {
        super(createdBy,updatedBy);
        this.text = text;
    }

    private HashTag() {
        super();
    }

    public final static String HASHTAG_TEXT_PATTERN = "[öÖäÄüÜßa-zA-Z0-9_]{1,139}";

    @Transient
    public boolean hasValidText(){
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    public static boolean isValidText(String hashtagText){
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        return m.matches();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUniqueId() {
        return text;
    }


    public String getText() {
        return this.text;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                " id=" + id +
                ", text='" + text + '\'' +
                    super.toString() +
                " }\n";
    }

    @Override
    public boolean isValid() {
        if((text == null)||(text.isEmpty())){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTag)) return false;

        HashTag hashTag = (HashTag) o;

        if (getId() != null ? !getId().equals(hashTag.getId()) : hashTag.getId() != null) return false;
        return getText() != null ? getText().equals(hashTag.getText()) : hashTag.getText() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        return result;
    }
}
