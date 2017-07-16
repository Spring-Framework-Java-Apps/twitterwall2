package org.woehlke.twitterwall.oodm.entities;

import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.entities.listener.TickerSymbolListener;

import javax.persistence.*;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "tickersymbol", uniqueConstraints = {
        @UniqueConstraint(name="unique_tickersymbol", columnNames = {"url"})
}, indexes = {
        @Index(name="idx_tickersymbol_ticker_symbol", columnList="ticker_symbol")
})
/*
@NamedQueries({
        @NamedQuery(
                name = "TickerSymbol.findByUrl",
                query = "select t from TickerSymbol as t where t.url=:url"
        ),
        @NamedQuery(
                name = "TickerSymbol.findByTickerSymbolAndUrl",
                query = "select t from TickerSymbol as t where t.url=:url and t.tickerSymbol=:tickerSymbol"
        ),
    @NamedQuery(
        name = "TickerSymbol.count",
        query = "select count(t) from TickerSymbol as t"
    ),
    @NamedQuery(
        name = "TickerSymbol.getAll",
        query = "select t from TickerSymbol as t"
    )
})
*/
@EntityListeners(TickerSymbolListener.class)
public class TickerSymbol extends AbstractTwitterObject<TickerSymbol> implements DomainObjectWithUrl<TickerSymbol>,DomainObjectWithTask<TickerSymbol> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Embedded
    private TaskInfo taskInfo = new TaskInfo();

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task createdBy;

    @ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    private Task updatedBy;

    @Column(name = "ticker_symbol",length=4096)
    private String tickerSymbol;

    @Column(length=4096)
    private String url;

    /*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tickersymbol_indices", joinColumns = @JoinColumn(name = "id"))
    protected List<Integer> indices = new ArrayList<>();

    public void setIndices(int[] indices) {
        this.indices.clear();
        for(Integer index: indices){
            this.indices.add(index);
        }
    }*/

    public TickerSymbol(String tickerSymbol, String url, int[] indices) {
        //setIndices(indices);
        this.tickerSymbol = tickerSymbol;
        this.url = url;
    }

    public TickerSymbol(TaskInfo taskInfo, Task createdBy, Task updatedBy, String tickerSymbol, String url) {
        this.taskInfo = taskInfo;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tickerSymbol = tickerSymbol;
        this.url = url;
    }

    public TickerSymbol() {
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
        /*
        StringBuffer myIndieces = new StringBuffer();
        myIndieces.append("[ ");
        for (Integer index : indices) {
            myIndieces.append(index.toString());
            myIndieces.append(", ");
        }
        myIndieces.append(" ]");*/
        return "TickerSymbol{" +
                "id=" + id +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", url='" + url + '\'' +
            ",\n createdBy="+ toStringCreatedBy() +
            ",\n updatedBy=" + toStringUpdatedBy() +
            ",\n taskInfo="+ toStringTaskInfo() +
                //", indices=" + myIndieces.toString() +
                "\n}";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public static TickerSymbol getTickerSymbolFactory(String url) {
        int[] indices = {};
        return new TickerSymbol(url,url,indices);
    }
}
