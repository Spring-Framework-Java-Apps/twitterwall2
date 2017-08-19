package org.woehlke.twitterwall.oodm.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.model.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.model.listener.TickerSymbolListener;

import javax.persistence.*;
import java.net.MalformedURLException;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "tickersymbol",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_tickersymbol", columnNames = {"url","ticker_symbol"}),
        @UniqueConstraint(name="unique_tickersymbol_url", columnNames = {"url"}),
        @UniqueConstraint(name="unique_tickersymbol_ticker_symbol", columnNames = {"ticker_symbol"})
    }
)
@NamedQueries({
    @NamedQuery(
        name="TickerSymbol.findByUniqueId",
        query="select t from TickerSymbol t where t.url=:url and t.tickerSymbol=:tickerSymbol"
    )
})
@EntityListeners(TickerSymbolListener.class)
public class TickerSymbol extends AbstractDomainObject<TickerSymbol> implements DomainObjectEntity<TickerSymbol>,DomainObjectWithUrl<TickerSymbol>,DomainObjectWithTask<TickerSymbol> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotEmpty
    @SafeHtml
    @Column(name = "ticker_symbol",length=4096,nullable = false)
    private String tickerSymbol = "";

    @URL
    @NotEmpty
    @Column(name = "url",length=4096,nullable = false)
    private String url = "";

    public TickerSymbol(Task createdBy, Task updatedBy, String tickerSymbol, String url) {
        super(createdBy,updatedBy);
        this.tickerSymbol = tickerSymbol;
        this.url = url;
    }

    public TickerSymbol(Task createdBy, Task updatedBy, String url) {
        super(createdBy,updatedBy);
        this.tickerSymbol = "UNDEFINED";
        this.url = url;
    }

    private TickerSymbol() {
    }

    @Override
    public String getUniqueId() {
        return "" + url  +"_"+  tickerSymbol;
    }

    @Override
    public boolean isValid() {
        if(url==null){
            return false;
        }
        if(url.isEmpty()){
            return false;
        }
        if(tickerSymbol==null){
            return false;
        }
        if(tickerSymbol.isEmpty()){
            return false;
        }
        try {
            java.net.URL url = new java.net.URL(this.url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
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


    @Override
    public String toString() {
        return "TickerSymbol{" +
                "id=" + id +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", url='" + url + '\'' +
                    super.toString() +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TickerSymbol)) return false;
        if (!super.equals(o)) return false;

        TickerSymbol that = (TickerSymbol) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTickerSymbol() != null ? !getTickerSymbol().equals(that.getTickerSymbol()) : that.getTickerSymbol() != null)
            return false;
        return getUrl() != null ? getUrl().equals(that.getUrl()) : that.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getTickerSymbol() != null ? getTickerSymbol().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }
}
