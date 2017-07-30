package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.MentionListener;
import org.woehlke.twitterwall.oodm.entities.parts.ScreenName;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "mention",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_mention", columnNames = {"screen_name", "id_twitter"})
    },
    indexes = {
        @Index(name = "idx_mention_name", columnList = "name")
    }
)
@NamedQueries({
    @NamedQuery(
        name="Mention.findByUniqueId",
        query="select t from Mention t where t.idTwitter=:idTwitter and t.screenName=:screenName"
    )
})
@EntityListeners(MentionListener.class)
public class Mention extends AbstractDomainObject<Mention> implements DomainObjectEntity<Mention>,DomainObjectWithIdTwitter<Mention>, DomainObjectWithScreenName<Mention>,DomainObjectWithTask<Mention> {

    private static final long serialVersionUID = 1L;

    private static final long ID_TWITTER_UNDEFINED = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "id_twitter")
    private Long idTwitter;

    @Valid
    @Embedded
    private ScreenName screenName;

    @Column(name = "name",length=4096, nullable = false)
    private String name = "";

    @JoinColumn(name = "fk_user",nullable = true)
    @OneToOne(optional=true, fetch = EAGER, cascade = {DETACH, REFRESH, REMOVE})
    private User user;

    @NotNull
    @Column(name = "id_twitte_of_user",nullable = false)
    private Long idTwitterOfUser = 0L;

    public Mention(Task createdBy, Task updatedBy, long idTwitter, ScreenName screenName, String name) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
    }

    public Mention(Task createdBy, Task updatedBy, String mentionString) {
        super(createdBy,updatedBy);
        this.idTwitter = ID_TWITTER_UNDEFINED;
        this.screenName = new ScreenName(mentionString);
        this.name = mentionString;
    }

    private Mention() {
    }

    @Transient
    public Boolean isProxy(){
        return idTwitter < 0;
    }

    @Transient
    public boolean hasPersistentUser(){
        if(this.getUser() == null){
            return false;
        }
        if((this.getUser().getScreenName().compareTo(this.getScreenName())!=0)){
            return false;
        }
        if(this.getUser().getIdTwitter() != this.idTwitterOfUser){
            return false;
        }
        return true;
    }

    @Transient
    public boolean isValid() {
        if(screenName == null){
            return false;
        }
        if(!screenName.isValid()){
            return false;
        }
        if(idTwitter == null){
            return false;
        }
        if(idTwitterOfUser == null){
            return false;
        }
        if(this.getUser() != null){
            if((this.getUser().getScreenName().compareTo(this.getScreenName())!=0)){
                return false;
            }
            if(this.getUser().getIdTwitter() != this.idTwitterOfUser){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getUniqueId() {
        return idTwitter.toString() +"_"+ screenName.toString();
    }

    @Override
    public Map<String, Object> getParametersForFindByUniqueId() {
        return null;
    }

    @Override
    public String getQueryNameForFindByUniqueId() {
        return null;
    }

    public boolean hasUser() {
        return user != null;
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

    @Override
    public ScreenName getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(ScreenName screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getIdTwitter() {
        return idTwitter;
    }

    @Override
    public void setIdTwitter(Long idTwitter) {
        this.idTwitter = idTwitter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIdTwitterOfUser() {
        return idTwitterOfUser;
    }

    public void setIdTwitterOfUser(Long idTwitterOfUser) {
        this.idTwitterOfUser = idTwitterOfUser;
    }

    @Override
    public String toString() {
        return "Mention{" +
            " id=" + id +
            ", idTwitter=" + idTwitter +
            ", screenName='" + screenName + '\'' +
            ", name='" + name + '\'' +
                super.toString() +
            " }\n";
    }

    @Transient
    public boolean isRawMentionFromUserDescription() {
        return (this.getIdTwitter() == ID_TWITTER_UNDEFINED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mention)) return false;
        if (!super.equals(o)) return false;

        Mention mention = (Mention) o;

        if (getId() != null ? !getId().equals(mention.getId()) : mention.getId() != null) return false;
        if (getIdTwitter() != null ? !getIdTwitter().equals(mention.getIdTwitter()) : mention.getIdTwitter() != null)
            return false;
        return getScreenName() != null ? getScreenName().equals(mention.getScreenName()) : mention.getScreenName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getIdTwitter() != null ? getIdTwitter().hashCode() : 0);
        result = 31 * result + (getScreenName() != null ? getScreenName().hashCode() : 0);
        return result;
    }
}
