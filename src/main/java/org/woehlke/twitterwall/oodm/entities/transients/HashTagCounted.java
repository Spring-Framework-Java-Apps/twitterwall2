package org.woehlke.twitterwall.oodm.entities.transients;

import java.io.Serializable;

/**
 * Created by tw on 14.06.17.
 */
public class HashTagCounted implements Serializable, Comparable<HashTagCounted> {

    private long id;
    private long number;
    private String text;

    public HashTagCounted(long id, long number, String text) {
        this.id = id;
        this.number = number;
        this.text = text;
    }

    private HashTagCounted() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagCounted)) return false;

        HashTagCounted that = (HashTagCounted) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (number ^ (number >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HashTagCounted{" +
                "id=" + id +
                ", number=" + number +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int compareTo(HashTagCounted o) {
        return  Long.valueOf(id).compareTo(Long.valueOf(o.getId()));
    }
}
