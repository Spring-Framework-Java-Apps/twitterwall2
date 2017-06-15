package org.woehlke.twitterwall.oodm.entities.entities;

import org.woehlke.twitterwall.oodm.entities.AbstractTwitterObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(name="tickersymbol",uniqueConstraints=@UniqueConstraint(columnNames={"ticker_symbol","url"}))
public class TickerSymbol extends AbstractTwitterObject implements Serializable,Comparable<TickerSymbol> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="ticker_symbol")
    private String tickerSymbol;

    @Column
    private String url;

    @Transient
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
}
