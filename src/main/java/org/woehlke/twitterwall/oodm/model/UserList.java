package org.woehlke.twitterwall.oodm.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.model.listener.UserListListener;
import org.woehlke.twitterwall.oodm.model.parts.AbstractDomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public UserList(Task createdBy, Task updatedBy, long idTwitter, String name, String fullName, String uriPath, String description, String slug, boolean isPublic, boolean isFollowing, int memberCount, int subscriberCount) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserList)) return false;

        UserList userList = (UserList) o;

        if (getId() != null ? !getId().equals(userList.getId()) : userList.getId() != null) return false;
        if (getIdTwitter() != null ? !getIdTwitter().equals(userList.getIdTwitter()) : userList.getIdTwitter() != null)
            return false;
        if (getName() != null ? !getName().equals(userList.getName()) : userList.getName() != null) return false;
        if (getFullName() != null ? !getFullName().equals(userList.getFullName()) : userList.getFullName() != null)
            return false;
        if (getUriPath() != null ? !getUriPath().equals(userList.getUriPath()) : userList.getUriPath() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(userList.getDescription()) : userList.getDescription() != null)
            return false;
        if (getSlug() != null ? !getSlug().equals(userList.getSlug()) : userList.getSlug() != null) return false;
        if (isPublic != null ? !isPublic.equals(userList.isPublic) : userList.isPublic != null) return false;
        if (isFollowing != null ? !isFollowing.equals(userList.isFollowing) : userList.isFollowing != null)
            return false;
        if (getMemberCount() != null ? !getMemberCount().equals(userList.getMemberCount()) : userList.getMemberCount() != null)
            return false;
        return getSubscriberCount() != null ? getSubscriberCount().equals(userList.getSubscriberCount()) : userList.getSubscriberCount() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getIdTwitter() != null ? getIdTwitter().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getUriPath() != null ? getUriPath().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getSlug() != null ? getSlug().hashCode() : 0);
        result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
        result = 31 * result + (isFollowing != null ? isFollowing.hashCode() : 0);
        result = 31 * result + (getMemberCount() != null ? getMemberCount().hashCode() : 0);
        result = 31 * result + (getSubscriberCount() != null ? getSubscriberCount().hashCode() : 0);
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
