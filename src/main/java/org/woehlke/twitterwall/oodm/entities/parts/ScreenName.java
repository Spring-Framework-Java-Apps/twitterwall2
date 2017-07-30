package org.woehlke.twitterwall.oodm.entities.parts;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectEmbededField;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import java.util.regex.Pattern;


@Validated
@Embeddable
public class ScreenName implements DomainObjectEmbededField, Comparable<ScreenName> {

    public final static String SCREEN_NAME_PATTERN_TEXT = "\\w*";

    public final static Pattern SCREEN_NAME_PATTERN = Pattern.compile("^"+ SCREEN_NAME_PATTERN_TEXT +"$");

    @NotEmpty
    @Column(name="screen_name", nullable = false)
    private String screenName;

    public ScreenName(String screenName) {
        this.screenName = screenName;
    }

    public ScreenName() {
    }

    public static boolean isValidScreenName(String screenName){
        return SCREEN_NAME_PATTERN.matcher(screenName).matches();
    }

    @AssertTrue
    @Transient
    public boolean isValid(){
        if(this.screenName==null){
            return false;
        }
        if(this.screenName.isEmpty()){
            return false;
        }
        return SCREEN_NAME_PATTERN.matcher(this.screenName).matches();
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreenName)) return false;

        ScreenName that = (ScreenName) o;

        return screenName != null ? screenName.equals(that.screenName) : that.screenName == null;
    }

    @Override
    public int hashCode() {
        return screenName != null ? screenName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ScreenName{" +
                "screenName='" + screenName + '\'' +
                '}';
    }

    @Override
    public int compareTo(ScreenName o) {
        return this.screenName.compareTo(o.getScreenName());
    }
}
