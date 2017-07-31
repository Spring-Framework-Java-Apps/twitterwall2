package org.woehlke.twitterwall.oodm.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEntity;
import org.woehlke.twitterwall.oodm.entities.parts.AbstractDomainObject;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithTask;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithUrl;
import org.woehlke.twitterwall.oodm.entities.listener.TickerSymbolListener;
import org.woehlke.twitterwall.oodm.entities.parts.UrlField;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tw on 10.06.17.
 */
@Entity
@Table(
    name = "tickersymbol",
    uniqueConstraints = {
        @UniqueConstraint(name="unique_tickersymbol", columnNames = {"url","ticker_symbol"})
    }
)
@NamedQueries({
    @NamedQuery(
        name="TickerSymbol.findByUniqueId",
        query="select t from TickerSymbol t where t.url.url=:url and t.tickerSymbol=:tickerSymbol"
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

    @Valid
    @NotNull
    @Embedded
    private UrlField url;

    public TickerSymbol(Task createdBy, Task updatedBy, String tickerSymbol, UrlField url) {
        super(createdBy,updatedBy);
        this.tickerSymbol = tickerSymbol;
        this.url = url;
    }

    public TickerSymbol(Task createdBy, Task updatedBy, UrlField url) {
        super(createdBy,updatedBy);
        this.tickerSymbol = "UNDEFINED";
        this.url = url;
    }

    private TickerSymbol() {
    }

    @Transient
    @Override
    public String getUniqueId() {
        return "" + url  +"_"+  tickerSymbol;
    }

    @Transient
    @Override
    public boolean isValid() {
        if(this.url == null){
            return false;
        }
        if(this.url.isValid()){
            return false;
        }
        if(this.tickerSymbol == null){
            return false;
        }
        if(this.tickerSymbol.isEmpty()){
            return false;
        }
        return true;
    }

    @Transient
    @Override
    public Map<String,Object> getParametersForFindByUniqueId(){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("url",this.url.getUrl());
        parameters.put("tickerSymbol",this.tickerSymbol);
        return parameters;
    }

    @Transient
    @Override
    public String getQueryNameForFindByUniqueId() {
        return "TickerSymbol.findByUniqueId";
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

    @Override
    public UrlField getUrl() {
        return url;
    }

    @Override
    public void setUrl(UrlField url) {
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
