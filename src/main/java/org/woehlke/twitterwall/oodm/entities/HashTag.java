package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.HashTagListener;
import org.woehlke.twitterwall.oodm.entities.parts.HashTagText;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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
        query="select t from HashTag t where t.hashTagText.text=:text"
    )
})
@EntityListeners(HashTagListener.class)
public class HashTag extends AbstractDomainObject<HashTag> implements DomainObjectEntity<HashTag>, DomainObject<HashTag>,DomainObjectWithTask<HashTag> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private HashTagText hashTagText;

    @Column
    private Long numberOfTweets;

    @Column
    private Long numberOfUsers;

    public HashTag(Task createdBy, Task updatedBy, HashTagText hashTagText,Long numberOfTweets, Long numberOfUsers) {
        super(createdBy,updatedBy);
        this.hashTagText = hashTagText;
        this.numberOfTweets = numberOfTweets;
        this.numberOfUsers = numberOfUsers;
    }

    public HashTag(Task createdBy, Task updatedBy, HashTagText hashTagText) {
        super(createdBy,updatedBy);
        this.hashTagText = hashTagText;
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
        if(this.hashTagText == null){
            return false;
        }
        if(!hashTagText.isValid()){
            return false;
        }
        return true;
    }

    @Override
    public String getUniqueId() {
        return this.getHashTagText().getText();
    }

    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("text",this.hashTagText.getText());
        return parameters;
    }

    @Override
    public String getQueryNameForFindByUniqueId() {
        return "HashTag.findByUniqueId";
    }

    public HashTagText getHashTagText() {
        return hashTagText;
    }

    public void setHashTagText(HashTagText hashTagText) {
        this.hashTagText = hashTagText;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTag)) return false;

        HashTag hashTag = (HashTag) o;

        if (id != null ? !id.equals(hashTag.id) : hashTag.id != null) return false;
        if (hashTagText != null ? !hashTagText.equals(hashTag.hashTagText) : hashTag.hashTagText != null) return false;
        if (numberOfTweets != null ? !numberOfTweets.equals(hashTag.numberOfTweets) : hashTag.numberOfTweets != null)
            return false;
        return numberOfUsers != null ? numberOfUsers.equals(hashTag.numberOfUsers) : hashTag.numberOfUsers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (hashTagText != null ? hashTagText.hashCode() : 0);
        result = 31 * result + (numberOfTweets != null ? numberOfTweets.hashCode() : 0);
        result = 31 * result + (numberOfUsers != null ? numberOfUsers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "id=" + id +
                ", hashTagText=" + hashTagText +
                ", numberOfTweets=" + numberOfTweets +
                ", numberOfUsers=" + numberOfUsers +
                '}';
    }
}
