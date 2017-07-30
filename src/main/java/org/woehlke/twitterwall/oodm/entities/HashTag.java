package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.SafeHtml;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.HashTagListener;
import org.woehlke.twitterwall.oodm.entities.parts.HashTagText;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
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
public class HashTag extends AbstractDomainObject<HashTag> implements DomainObjectEntity<HashTag>, DomainObject<HashTag>,DomainObjectWithTask<HashTag> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private HashTagText text;

    @Column
    private Long numberOfTweets;

    @Column
    private Long numberOfUsers;

    public HashTag(Task createdBy, Task updatedBy, HashTagText text,Long numberOfTweets, Long numberOfUsers) {
        super(createdBy,updatedBy);
        this.text = text;
        this.numberOfTweets = numberOfTweets;
        this.numberOfUsers = numberOfUsers;
    }

    public HashTag(Task createdBy, Task updatedBy, HashTagText text) {
        super(createdBy,updatedBy);
        this.text = text;
    }

    private HashTag() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isValid() {
        if(this.text == null){
            return false;
        }
        if(!text.isValid()){
            return false;
        }
        return true;
    }

    @Override
    public String getUniqueId() {
        return this.getText().getText();
    }

    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("text",this.text);
        return parameters;
    }

    @Override
    public String getQueryNameForFindByUniqueId() {
        return "HashTag.findByUniqueId";
    }

    public HashTagText getText() {
        return text;
    }

    public void setText(HashTagText text) {
        this.text = text;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getNumberOfTweets() {
        return numberOfTweets;
    }

    public void setNumberOfTweets(Long numberOfTweets) {
        this.numberOfTweets = numberOfTweets;
    }

    public void increaseNumberOfTweets() {
        this.numberOfTweets++;
    }

    public Long getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Long numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public void increaseNumberOfUsers() {
        this.numberOfUsers++;
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
