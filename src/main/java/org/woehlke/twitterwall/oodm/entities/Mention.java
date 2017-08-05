package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithScreenName;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.listener.MentionListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "mention",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_mention", columnNames = {"screen_name_unique", "id_twitter"}),
        @UniqueConstraint(name = "unique_mention_screen_name_unique", columnNames = {"screen_name_unique"}),
    },
    indexes = {
        @Index(name = "idx_mention_name", columnList = "name"),
        @Index(name = "idx_mention_name", columnList = "screen_name"),
        @Index(name = "idx_mention_id_twitter_of_user", columnList = "id_twitter_of_user")
    }
)
@NamedQueries({
    @NamedQuery(
        name="Mention.findByUniqueId",
        query="select t from Mention t where t.idTwitter=:idTwitter and t.screenNameUnique=:screenNameUnique"
    ),
    @NamedQuery(
        name="Mention.findAllWithoutUser",
        query="select t from Mention t where t.idTwitterOfUser=0"
    ),
    @NamedQuery(
        name="Mention.countAllWithoutUser",
        query="select count(t) from Mention t where t.idTwitterOfUser=0"
    )
})
@EntityListeners(MentionListener.class)
public class Mention extends AbstractDomainObject<Mention> implements DomainObjectEntity<Mention>,DomainObjectWithIdTwitter<Mention>, DomainObjectWithScreenName<Mention>,DomainObjectWithTask<Mention> {

    private static final long serialVersionUID = 1L;

    public static final long ID_TWITTER_UNDEFINED = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "id_twitter")
    private Long idTwitter;

    @NotEmpty
    @Column(name = "screen_name", nullable = false)
    private String screenName = "";

    @NotEmpty
    @Column(name = "screen_name_unique", nullable = false)
    private String screenNameUnique = "";

    @Column(name = "name",length=4096, nullable = false)
    private String name = "";

    @NotNull
    @Column(name = "id_twitter_of_user",nullable = false)
    private Long idTwitterOfUser = 0L;

    public Mention(Task createdBy, Task updatedBy, long idTwitter, String screenName, String name) {
        super(createdBy,updatedBy);
        this.idTwitter = idTwitter;
        this.screenName = screenName;
        if(screenName!=null) {
            this.screenNameUnique = screenName.toLowerCase();
        }
        this.name = name;
    }

    public Mention(Task createdBy, Task updatedBy, String mentionString) {
        super(createdBy,updatedBy);
        this.idTwitter = ID_TWITTER_UNDEFINED;
        this.screenName = mentionString;
        if(screenName!=null) {
            this.screenNameUnique = screenName.toLowerCase();
        }
        this.name = mentionString;
    }

    private Mention() {
    }

    @Transient
    @Override
    public String getUniqueId() {
        return "" + idTwitter +"_"+ screenNameUnique;
    }

    @Transient
    @Override
    public boolean isValid() {
        if(screenName == null){
            return false;
        }
        if(screenName.isEmpty()){
            return false;
        }
        if(!this.hasValidScreenName()){
            return false;
        }
        if(screenNameUnique == null){
            return false;
        }
        if(screenNameUnique.isEmpty()){
            return false;
        }
        if(!this.hasValidScreenNameUnique()){
            return false;
        }
        if(idTwitter == null){
            return false;
        }
        if(idTwitterOfUser < 0L){
            return false;
        }
        if(this.getScreenName().toLowerCase().compareTo(this.getScreenNameUnique())!=0){
            return false;
        }
        return true;
    }

    @Transient
    public boolean hasValidScreenName() {
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    public static boolean isValidScreenName(String screenName) {
        if(screenName==null){
            return false;
        }
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_PATTERN + "$");
        Matcher m = p.matcher(screenName);
        return m.matches();
    }

    @Transient
    public boolean hasValidScreenNameUnique() {
        if(screenNameUnique.compareTo(screenName.toLowerCase())!=0){
            return false;
        }
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_UNIQUE_PATTERN+ "$");
        Matcher m = p.matcher(screenNameUnique);
        return m.matches();
    }

    public static boolean isValidScreenNameUnique(String screenNameUnique) {
        if(screenNameUnique==null){
            return false;
        }
        Pattern p = Pattern.compile("^" + User.SCREEN_NAME_UNIQUE_PATTERN + "$");
        Matcher m = p.matcher(screenNameUnique);
        return m.matches();
    }

    @Transient
    public Boolean isProxy(){
        return idTwitter == ID_TWITTER_UNDEFINED;
    }

    @Transient
    public boolean hasUser() {
        return idTwitterOfUser > 0L;
    }

    @Transient
    public boolean isRawMentionFromUserDescription() {
        if(this.isValid()) {
            return (this.getIdTwitter() == ID_TWITTER_UNDEFINED);
        } else {
            return false;
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
        if(screenName!=null) {
            this.screenNameUnique = screenName.toLowerCase();
        }
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

    public Long getIdTwitterOfUser() {
        return idTwitterOfUser;
    }

    public void setIdTwitterOfUser(Long idTwitterOfUser) {
        this.idTwitterOfUser = idTwitterOfUser;
    }

    @Override
    public String getScreenNameUnique() {
        return screenNameUnique;
    }

    @Override
    public void setScreenNameUnique(String screenNameUnique) {
        this.screenNameUnique = screenNameUnique.toLowerCase();
    }

    /*
    @Override
    public String toString() {
        return "Mention{" +
            " id=" + id +
            ", idTwitter=" + idTwitter +
            ", screenName='" + screenName + '\'' +
            ", name='" + name + '\'' +
                super.toString() +
            " }\n";
    }*/

    @Override
    public String toString() {
        return "Mention{" +
                "id=" + id +
                ", idTwitter=" + idTwitter +
                ", screenName='" + screenName + '\'' +
                ", screenNameUnique='" + screenNameUnique + '\'' +
                ", name='" + name + '\'' +
                ", idTwitterOfUser=" + idTwitterOfUser +
                super.toString() +
                '}';
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
