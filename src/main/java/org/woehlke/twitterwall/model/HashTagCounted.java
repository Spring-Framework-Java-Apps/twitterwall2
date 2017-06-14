package org.woehlke.twitterwall.model;

import java.io.Serializable;

/**
 * Created by tw on 14.06.17.
 */
public class HashTagCounted implements Serializable,Comparable<HashTagCounted> {

    private long number;
    private String text;

    public HashTagCounted(long number, String text) {
        this.number = number;
        this.text = text;
    }

    private HashTagCounted() {
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
    public int compareTo(HashTagCounted other) {
        return Long.compare(this.number,other.getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagCounted)) return false;

        HashTagCounted that = (HashTagCounted) o;

        if (number != that.number) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = (int) (number ^ (number >>> 32));
        result = 31 * result + text.hashCode();
        return result;
    }
}
