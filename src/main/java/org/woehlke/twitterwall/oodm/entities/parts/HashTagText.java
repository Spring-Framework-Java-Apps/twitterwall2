package org.woehlke.twitterwall.oodm.entities.parts;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEmbededField;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.AssertTrue;
import java.util.regex.Pattern;

@Validated
@Embeddable
public class HashTagText implements DomainObjectEmbededField, Comparable<ScreenName>  {

    public final static String HASHTAG_TEXT_PATTERN_TEXT = "[öÖäÄüÜßa-zA-Z0-9_]{1,139}";

    public final static Pattern HASHTAG_TEXT_PATTERN = Pattern.compile("^"+ HASHTAG_TEXT_PATTERN_TEXT +"$");

    @SafeHtml
    @Column(name="text", nullable = false,length=4096)
    private String text = "";

    public static boolean isValidText(String hashtagText){
        return HASHTAG_TEXT_PATTERN.matcher(hashtagText).matches();
    }

    @AssertTrue
    @Override
    public boolean isValid() {
        if(this.text == null){
            return false;
        }
        if(this.text.isEmpty()){
            return false;
        }
        return HASHTAG_TEXT_PATTERN.matcher(this.text).matches();
    }

    @Override
    public int compareTo(ScreenName o) {
        return 0;
    }

    public HashTagText(String text) {
        this.text = text;
    }

    public HashTagText() {
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
        if (!(o instanceof HashTagText)) return false;

        HashTagText that = (HashTagText) o;

        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HashTagText{" +
                "text='" + text + '\'' +
                '}';
    }
}
