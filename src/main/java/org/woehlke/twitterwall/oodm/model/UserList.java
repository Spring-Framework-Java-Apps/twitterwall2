package org.woehlke.twitterwall.oodm.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.model.listener.UserListListener;
import org.woehlke.twitterwall.oodm.model.parts.AbstractDomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(
    name = "userlist",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_userlist",columnNames = {"id_twitter"})
    },
    indexes = {
        @Index(name="idx_userprofile_name", columnList="name"),
        @Index(name="idx_userprofile_full_name", columnList="full_name"),
        @Index(name="idx_userprofile_uri_path", columnList="uri_path"),
        @Index(name="idx_userprofile_slug", columnList="slug")
    }
)
@NamedQueries({
    @NamedQuery(
        name = "UserList.findByUniqueId",
        query = "select t from UserList as t where t.idTwitter=:idTwitter"
    )
})

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "UserList.countUserList2Subcriber",
        query = "SELECT count(*) AS z FROM userlist_subcriber"
    ),
    @NamedNativeQuery(
        name = "UserList.countUserList2Members",
        query = "SELECT count(*) AS z FROM userlist_members"
    )
})
@EntityListeners(UserListListener.class)
public class UserList extends AbstractDomainObject<UserList> implements DomainObjectWithTask<UserList>,DomainObjectWithIdTwitter<UserList> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name="id_twitter", nullable = false)
    private Long idTwitter;

    @NotEmpty
    @Column(name="name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name="full_name", nullable = false)
    private String fullName;

    @NotEmpty
    @Column(name="uri_path", nullable = false)
    private String uriPath;

    @NotNull
    @Column(name="description", nullable = false)
    private String description;

    @NotEmpty
    @Column(name="slug", nullable = false)
    private String slug;

    @NotNull
    @Column(name="is_public", nullable = false)
    private Boolean isPublic;

    @NotNull
    @Column(name="is_following", nullable = false)
    private Boolean isFollowing;

    @NotNull
    @Column(name="member_count", nullable = false)
    private Integer memberCount;

    @NotNull
    @Column(name="subscriber_count", nullable = false)
    private Integer subscriberCount;

    @Transient
    public String getListOwnersScreenName(){
        String myuriPath = uriPath;
        return myuriPath.split("/")[1];
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_user")
    private User listOwner;


    @NotNull
    @JoinTable(
        name="userlist_members"
    )
    @ManyToMany(
        cascade = { DETACH, REFRESH, REMOVE },
        fetch = LAZY
    )
    private Set<User> members = new LinkedHashSet<User>();


    @NotNull
    @JoinTable(
        name="userlist_subcriber"
    )
    @ManyToMany(
        cascade = { DETACH, REFRESH, REMOVE },
        fetch = LAZY
    )
    private Set<User> subscriber = new LinkedHashSet<User>();

    public UserList(Task createdBy, Task updatedBy, long idTwitter, String name, String fullName, String uriPath, String description, String slug, boolean isPublic, boolean isFollowing, int memberCount, int subscriberCount, User listOwner,Set<User> members,Set<User> subscriber) {
        super(createdBy, updatedBy);
        this.idTwitter = idTwitter;
        this.name = name;
        this.fullName = fullName;
        this.uriPath = uriPath;
        this.slug = slug;
        this.isPublic = isPublic;
        this.isFollowing = isFollowing;
        this.memberCount = memberCount;
        this.subscriberCount = subscriberCount;
        if(description==null){
            this.description = "";
        } else {
            this.description = description;
        }
        this.listOwner = listOwner;
        this.members = members;
        this.subscriber = subscriber;
    }

    protected UserList() {
    }


    @Transient
    @Override
    public String getUniqueId() {
        return ""+idTwitter;
    }

    @Transient
    @Override
    public boolean isValid() {
        return idTwitter != null;
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
    public Long getIdTwitter() {
        return idTwitter;
    }

    @Override
    public void setIdTwitter(Long idTwitter) {
        this.idTwitter = idTwitter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
    }


    public User getListOwner() {
        return listOwner;
    }

    public void setListOwner(User listOwner) {
        this.listOwner = listOwner;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<User> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Set<User> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserList)) return false;

        UserList userList = (UserList) o;

        if (id != null ? !id.equals(userList.id) : userList.id != null) return false;
        if (idTwitter != null ? !idTwitter.equals(userList.idTwitter) : userList.idTwitter != null) return false;
        if (name != null ? !name.equals(userList.name) : userList.name != null) return false;
        if (fullName != null ? !fullName.equals(userList.fullName) : userList.fullName != null) return false;
        if (uriPath != null ? !uriPath.equals(userList.uriPath) : userList.uriPath != null) return false;
        if (description != null ? !description.equals(userList.description) : userList.description != null)
            return false;
        if (slug != null ? !slug.equals(userList.slug) : userList.slug != null) return false;
        if (isPublic != null ? !isPublic.equals(userList.isPublic) : userList.isPublic != null) return false;
        if (isFollowing != null ? !isFollowing.equals(userList.isFollowing) : userList.isFollowing != null)
            return false;
        if (memberCount != null ? !memberCount.equals(userList.memberCount) : userList.memberCount != null)
            return false;
        if (subscriberCount != null ? !subscriberCount.equals(userList.subscriberCount) : userList.subscriberCount != null)
            return false;
        if (listOwner != null ? !listOwner.equals(userList.listOwner) : userList.listOwner != null) return false;
        if (members != null ? !members.equals(userList.members) : userList.members != null) return false;
        return subscriber != null ? subscriber.equals(userList.subscriber) : userList.subscriber == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idTwitter != null ? idTwitter.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (uriPath != null ? uriPath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
        result = 31 * result + (isFollowing != null ? isFollowing.hashCode() : 0);
        result = 31 * result + (memberCount != null ? memberCount.hashCode() : 0);
        result = 31 * result + (subscriberCount != null ? subscriberCount.hashCode() : 0);
        result = 31 * result + (listOwner != null ? listOwner.hashCode() : 0);
        result = 31 * result + (members != null ? members.hashCode() : 0);
        result = 31 * result + (subscriber != null ? subscriber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserList{" +
            "id=" + id +
            ", idTwitter=" + idTwitter +
            ", name='" + name + '\'' +
            ", fullName='" + fullName + '\'' +
            ", uriPath='" + uriPath + '\'' +
            ", description='" + description + '\'' +
            ", slug='" + slug + '\'' +
            ", isPublic=" + isPublic +
            ", isFollowing=" + isFollowing +
            ", memberCount=" + memberCount +
            ", subscriberCount=" + subscriberCount +
            '}';
    }
}
