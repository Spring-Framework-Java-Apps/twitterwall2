package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.listener.TickerSymbolListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "tickersymbol",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_tickersymbol", columnNames = {"url","ticker_symbol"})
    },
    indexes = {
        @Index(name="idx_tickersymbol_ticker_symbol", columnList="ticker_symbol")
    }
)
@EntityListeners(TickerSymbolListener.class)
public class TickerSymbol extends AbstractTwitterObject<TickerSymbol> implements DomainObjectWithUrl<TickerSymbol>,DomainObjectWithTask<TickerSymbol> {

    private static final long serialVersionUID = 1L;

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

    @NotEmpty
    @SafeHtml
    @Column(name = "ticker_symbol",length=4096,nullable = false)
    private String tickerSymbol = "";

    @URL
    @NotEmpty
    @Column(name = "url",length=4096,nullable = false)
    private String url = "";

    public TickerSymbol(String tickerSymbol, String url,Task task) {
        this.tickerSymbol = tickerSymbol;
        this.url = url;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }

    public TickerSymbol(String url,Task task) {
        this.tickerSymbol = "UNDEFINED";
        this.url = url;
        this.createdBy = task;
        this.updatedBy = task;
        this.taskInfo.setTaskInfoFromTask(task);
    }
/*
    public TickerSymbol(TaskInfo taskInfo, Task createdBy, Task updatedBy, String tickerSymbol, String url) {
        this.taskInfo = taskInfo;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tickerSymbol = tickerSymbol;
        this.taskInfo.setTaskInfoFromTask(createdBy);
        this.taskInfo.setTaskInfoFromTask(updatedBy);
        this.url = url;
    }*/

    private TickerSymbol() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(TickerSymbol o) {
        if (this == o) return true;
        if (!(o instanceof TickerSymbol)) return false;
        if (!super.equals(o)) return false;

        TickerSymbol that = (TickerSymbol) o;

        if (!tickerSymbol.equals(that.tickerSymbol)) return false;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + tickerSymbol.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public int compareTo(TickerSymbol other) {
        return tickerSymbol.compareTo(other.getTickerSymbol());
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
        return "TickerSymbol{" +
                "id=" + id +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", url='" + url + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
                "\n}";
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
