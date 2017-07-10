package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.listener.entities.UrlListener;

import javax.persistence.*;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "url", uniqueConstraints = {
        @UniqueConstraint(name="unique_url", columnNames = {"url"})
}, indexes = {
        @Index(name="idx_url_expanded", columnList="expanded"),
        @Index(name="idx_url_display", columnList="display")
})
@NamedQueries({
        @NamedQuery(
                name="Url.findByUrl",
                query="select t from Url as t where t.url=:url"
        ) ,
    @NamedQuery(
        name = "Url.count",
        query = "select count(t) from Url as t"
    ),
})
@EntityListeners(UrlListener.class)
public class Url extends AbstractTwitterObject<Url> implements DomainObjectWithUrl<Url> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Task createdBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Task updatedBy;

    @Column(length=2048)
    private String display;

    @Column(length=2048)
    private String expanded;

    public static final String URL_PATTTERN_FOR_USER = "https://t\\.co/\\w*";

    @Column(nullable = false,length=1024)
    private String url;

    /*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "url_indices", joinColumns = @JoinColumn(name = "id"))
    protected List<Integer> indices = new ArrayList<>();

    public void setIndices(int[] indices) {
        this.indices.clear();
        for(Integer index: indices){
            this.indices.add(index);
        }
    }*/

    @Transient
    public boolean isUrlAndExpandedTheSame(){
        return url.compareTo(expanded) == 0;
    }

    public Url(String display, String expanded, String url, int[] indices) {
        //setIndices(indices);
        this.display = display;
        this.expanded = expanded;
        this.url = url;
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
        this.createdBy = createdBy;
    }

    public Task getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Task updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Url)) return false;
        if (!super.equals(o)) return false;

        Url url1 = (Url) o;

        return url.equals(url1.url);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public int compareTo(Url other) {
        return url.compareTo(other.getUrl());
    }

    @Override
    public String toString() {
        /*
        StringBuffer myIndieces = new StringBuffer();
        myIndieces.append("[ ");
        for (Integer index : indices) {
            myIndieces.append(index.toString());
            myIndieces.append(", ");
        }
        myIndieces.append(" ]");*/
        return "Url{" +
                "id=" + id +
                ", display='" + display + '\'' +
                ", expanded='" + expanded + '\'' +
                ", url='" + url + '\'' +
               // ", indices=" + myIndieces.toString() +
                '}';
    }
}
