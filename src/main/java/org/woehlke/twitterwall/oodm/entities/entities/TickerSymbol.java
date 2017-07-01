package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.common.AbstractTwitterObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name = "tickersymbol", uniqueConstraints = {
        @UniqueConstraint(name="unique_tickersymbol", columnNames = {"url"})
}, indexes = {
        @Index(name="idx_tickersymbol_ticker_symbol", columnList="ticker_symbol")
})
@NamedQueries({
        @NamedQuery(
                name = "TickerSymbol.findByUrl",
                query = "select t from TickerSymbol as t where t.url=:url"
        ),
        @NamedQuery(
                name = "TickerSymbol.findByTickerSymbolAndUrl",
                query = "select t from TickerSymbol as t where t.url=:url and t.tickerSymbol=:tickerSymbol"
        )
})
public class TickerSymbol extends AbstractTwitterObject<TickerSymbol> implements DomainObjectWithUrl<TickerSymbol> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    @Column
    private String url;

    public TickerSymbol(String tickerSymbol, String url, int[] indices) {
        this.tickerSymbol = tickerSymbol;
        this.url = url;
        this.indices = indices;
    }

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

    @Override
    public String toString() {
        return "TickerSymbol{" +
                "id=" + id +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
