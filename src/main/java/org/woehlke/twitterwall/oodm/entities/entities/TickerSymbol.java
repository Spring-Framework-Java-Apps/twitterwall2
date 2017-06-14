package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.AbstractTwitterObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="tickersymbol")
public class TickerSymbol extends AbstractTwitterObject implements Serializable,Comparable<TickerSymbol> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String tickerSymbol;

    @Column
    private String url;

    @Column
    private int[] indices;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TickerSymbol)) return false;
        if (!super.equals(o)) return false;

        TickerSymbol that = (TickerSymbol) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tickerSymbol != null ? !tickerSymbol.equals(that.tickerSymbol) : that.tickerSymbol != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return Arrays.equals(indices, that.indices);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (tickerSymbol != null ? tickerSymbol.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(indices);
        return result;
    }

    @Override
    public int compareTo(TickerSymbol other) {
        return tickerSymbol.compareTo(other.getTickerSymbol());
    }
}
