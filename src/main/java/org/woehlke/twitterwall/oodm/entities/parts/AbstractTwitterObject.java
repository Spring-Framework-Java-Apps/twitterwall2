package org.woehlke.twitterwall.oodm.entities.parts;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tw on 10.06.17.
 */
public abstract class AbstractTwitterObject<T extends DomainObject> implements DomainObject<T> {

    @Transient
    private Map<String, Object> extraData = new HashMap<>();

    public AbstractTwitterObject() {
    }

    /**
     * @return Any fields in response from Twitter that are otherwise not mapped to any properties.
     */
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    /**
     * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
     *
     * @param key   The property's key.
     * @param value The property's value.
     */
    protected void add(String key, Object value) {
        extraData.put(key, value);
    }

    public void setExtraData(Map<String, Object> extraData) {
      this.extraData = extraData;
    }

  @Override
    public boolean equals(T o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTwitterObject)) return false;

        AbstractTwitterObject that = (AbstractTwitterObject) o;

        return extraData != null ? extraData.equals(that.extraData) : that.extraData == null;
    }

    @Override
    public int hashCode() {
        return extraData != null ? extraData.hashCode() : 0;
    }

  @Override
  public String toString() {
    StringBuffer myExtraData = new StringBuffer();
    myExtraData.append("[ ");
    for (String extraDatumKey : extraData.keySet()) {
      myExtraData.append(extraDatumKey);
      myExtraData.append(", ");
    }
    myExtraData.append(" ]");
    return  ", extraData=" + myExtraData.toString();
  }

}
