package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.listener.UrlListener;

import javax.persistence.*;
import javax.validation.OverridesAttribute;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "url",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_url", columnNames = {"url"})
    },
    indexes = {
        @Index(name="idx_url_expanded", columnList="expanded"),
        @Index(name="idx_url_display", columnList="display")
    }
)
@EntityListeners(UrlListener.class)
public class Url extends AbstractTwitterObject<Url> implements DomainObjectWithUrl<Url>,DomainObjectWithTask<Url> {

    private static final long serialVersionUID = 1L;

    public static final String UNDEFINED = "UNDEFINED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Embedded
    private TaskInfo taskInfo  = new TaskInfo();

    @NotNull
    @JoinColumn(name = "fk_user_created_by")
    @ManyToOne(cascade = { REFRESH, DETACH }, fetch = EAGER,optional = false)
    private Task createdBy;

    @JoinColumn(name = "fk_user_updated_by")
    @ManyToOne(cascade = { REFRESH ,DETACH}, fetch = EAGER,optional = true)
    private Task updatedBy;

    @NotNull
    @Column(length=4096,nullable = false)
    private String display="";

    @URL
    @NotNull
    @Column(length=4096,nullable = false)
    private String expanded="";

    public static final String URL_PATTTERN_FOR_USER_HTTPS = "https://t\\.co/\\w*";

    public static final String URL_PATTTERN_FOR_USER_HTTP = "http://t\\.co/\\w*";

    @URL
    @NotEmpty
    @Column(nullable = false,length=4096)
    private String url;

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        return url.compareTo(expanded) == 0;
    }

    public Url(String display, String expanded, String url, Task task) {
        this.display = display;
        this.expanded = expanded;
        this.url = url;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    public Url(String url,Task task) {
        this.display = Url.UNDEFINED;
        this.expanded = Url.UNDEFINED;
        this.url = url;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    private Url() {
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Task getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Task createdBy) {
        taskInfo.setTaskInfoFromTask(createdBy);
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
        taskInfo.setTaskInfoFromTask(updatedBy);
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Url)) return false;

        Url url1 = (Url) o;

        return url != null ? url.equals(url1.url) : url1.url == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Url other) {
        return url.compareTo(other.getUrl());
    }

    private String toStringCreatedBy(){
        if(createdBy==null){
            return " null ";
        } else {
            return createdBy.toString();
        }
    }

    private String toStringUpdatedBy(){
        if(updatedBy==null){
            return " null ";
        } else {
            return updatedBy.toString();
        }
    }

    private String toStringTaskInfo(){
        if(taskInfo==null){
            return " null ";
        } else {
            return taskInfo.toString();
        }
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
                "}\n";
    }

    @Override
    public boolean isValid() {
        boolean isInvalid = (this.url == null)||(this.url.isEmpty()||isRawUrlsFromDescription())||(this.url.compareTo(this.expanded)==0);
        return !isInvalid;
    }

    public boolean isRawUrlsFromDescription() {
        return (this.getDisplay().compareTo(UNDEFINED)==0)&&(this.getExpanded().compareTo(UNDEFINED)==0);
    }
}