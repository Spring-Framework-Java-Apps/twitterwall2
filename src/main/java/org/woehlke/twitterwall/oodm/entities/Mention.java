package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.MentionListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Mention extends AbstractDomainObject<Mention> implements DomainObjectWithIdTwitter<Mention>, DomainObjectWithScreenName<Mention>,DomainObjectWithTask<Mention> {

    private static final long serialVersionUID = 1L;

    private static final long ID_TWITTER_UNDEFINED = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "id_twitter")
    private Long idTwitter;

    @NotEmpty
    @Column(name = "screen_name", nullable = false)
    private String screenName = "";

    @Column(name = "name",length=4096, nullable = false)
    private String name = "";

    @NotNull
    @JoinColumn(name = "fk_user")
    @OneToOne(optional=true, fetch = EAGER, cascade = {DETACH, REFRESH, REMOVE})
    private User user;

    @NotNull
    @Column(name = "id_twitte_of_user",nullable = false)
    private Long idTwitterOfUser = 0L;

    public Mention(Task createdBy, Task updatedBy, long idTwitter, String screenName, String name) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        this.name = name;
    }

    public Mention(Task createdBy, Task updatedBy, String mentionString) {
        super(createdBy,updatedBy);
        this.idTwitter = ID_TWITTER_UNDEFINED;
        this.screenName = mentionString;
        this.name = mentionString;
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
        }
    }

    private Mention() {
    }

    @Transient
    public Boolean isProxy(){
        return idTwitter < 0;
    }

    @Transient
    public boolean hasPersistentUser(){
        boolean result = false;
        User myUser = this.getUser();
        if(myUser != null){
            result =
                (myUser.getScreenName().compareTo(this.getScreenName())==0) &&
                    (idTwitterOfUser != null ) &&
                    (myUser.getIdTwitter() == idTwitterOfUser);
        }
        return result;
    }

    @Transient
    public boolean hasValidScreenName() {
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    public static boolean isValidScreenName(String screenName) {
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
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
    public String getUniqueId() {
        return idTwitter.toString() +"_"+ screenName.toString();
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
    public boolean isValid() {
        if((screenName == null) ||(screenName.isEmpty())|| isRawMentionFromUserDescription()){
            return false;
        }
        if(idTwitter <= 1L){
            return false;
        }
        return true;
    }

    @Transient
    public boolean isRawMentionFromUserDescription() {
        return (this.getIdTwitter() == ID_TWITTER_UNDEFINED);
    }

}
