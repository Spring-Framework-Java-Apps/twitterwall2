package org.woehlke.twitterwall.frontend.model;

import java.io.Serializable;

/**
 * Created by tw on 14.06.17.
 */
public class HashTagCounted implements Serializable, Comparable<HashTagCounted> {

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
        return this.text.compareTo(other.getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagCounted)) return false;

        HashTagCounted that = (HashTagCounted) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
