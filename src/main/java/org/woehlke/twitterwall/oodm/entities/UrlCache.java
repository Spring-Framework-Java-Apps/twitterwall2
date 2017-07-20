package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.listener.UrlCacheListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 23.06.17.
 */
@Entity
@Table(
    name = "url_cache",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_url_cache", columnNames = {"url"})
    },
    indexes = {
        @Index(name="idx_url_cache_expanded", columnList="expanded")
    }
)
@EntityListeners(UrlCacheListener.class)
public class UrlCache implements DomainObjectWithUrl<UrlCache>,DomainObjectWithTask<UrlCache> {

    private static final long serialVersionUID = 1L;

    public static final String UNDEFINED = "UNDEFINED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    @Column(length=4096)
    private String expanded = "";

    @URL
    @NotEmpty
    @Column(nullable = false,length=4096)
    private String url;

    public UrlCache(String expanded, String url, Task task) {
        this.expanded = expanded;
        this.url = url;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    public UrlCache(String url,Task task){
        this.expanded = UrlCache.UNDEFINED;
        this.url = url;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    private UrlCache(){
    }

    @Transient
    public boolean isUrlAndExpandedTheSame(){
       return  url.compareTo(expanded) == 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(UrlCache o) {
        if (this == o) return true;
        if (!(o instanceof UrlCache)) return false;

        UrlCache urlCache = (UrlCache) o;

        return url.equals(urlCache.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public int compareTo(UrlCache other) {
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
        return "UrlCache{" +
                " id=" + id +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
                "}\n";
    }

    @Override
    public boolean isValid() {
        return true;
    }
}