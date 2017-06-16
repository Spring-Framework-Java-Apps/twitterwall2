package org.woehlke.twitterwall.oodm.entities;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tw on 10.06.17.
 */
public abstract class AbstractTwitterObject {

    @Transient
    private Map<String, Object> extraData;

    public AbstractTwitterObject() {
        this.extraData = new HashMap<String, Object>();
    }

    /**
     * @return Any fields in response from Twitter that are otherwise not mapped to any properties.
     */
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    /**
     * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
     * @param key The property's key.
     * @param value The property's value.
     */
    protected void add(String key, Object value) {
        extraData.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTwitterObject)) return false;

        AbstractTwitterObject that = (AbstractTwitterObject) o;

        return extraData != null ? extraData.equals(that.extraData) : that.extraData == null;
    }

    @Override
    public int hashCode() {
        return extraData != null ? extraData.hashCode() : 0;
    }
}