package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.listener.UrlCacheListener;

import javax.persistence.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER,optional = true)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER,optional = true)
    private Task updatedBy;

    @Column(length=4096)
    private String expanded;

    @Column(nullable = false,length=4096)
    private String url;

    public UrlCache(TaskInfo taskInfo, Task createdBy, Task updatedBy, String expanded, String url) {
        this.taskInfo = taskInfo;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.expanded = expanded;
        this.url = url;
    }

    public UrlCache() {
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
