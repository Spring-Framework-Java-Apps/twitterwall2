package org.woehlke.twitterwall.oodm.entities.common;

import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 23.06.17.
 */
public abstract class AbstractFormattedText extends AbstractTwitterObject {


    protected String getFormattedTextForMentions(Set<Mention> mentions, String formattedText) {
        for (Mention mention : mentions) {

            Pattern userPattern = Pattern.compile("@(" + mention.getScreenName() + ")(" + stopChar + ")");
            Matcher m1 = userPattern.matcher(formattedText);
            formattedText = m1.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a>$2");

            Pattern userPattern2 = Pattern.compile("@(" + mention.getScreenName() + ")$");
            Matcher m2 = userPattern2.matcher(formattedText);
            formattedText = m2.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a> ");
        }
        return formattedText;
    }

    protected String getFormattedTextForUserProfiles(String formattedText) {
        Pattern userPattern = Pattern.compile("@(\\w{1,15})(" + stopChar + ")");
        Matcher m1 = userPattern.matcher(formattedText);
        formattedText = m1.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"/profile/$1\">@$1</a>$2");

        Pattern userPattern2 = Pattern.compile("@(\\w{1,15})$");
        Matcher m2 = userPattern2.matcher(formattedText);
        formattedText = m2.replaceAll("<a class=\"tweet-action tweet-profile\" href=\"/profile/$1\">@$1</a> ");

        return formattedText;
    }

    protected String getFormattedTextForHashTags(Set<HashTag> tags, String formattedText) {
        for (HashTag tag : tags) {

            Pattern hashTagPattern = Pattern.compile("#(" + tag.getText() + ")(" + stopChar + ")");
            Matcher m3 = hashTagPattern.matcher(formattedText);
            formattedText = m3.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/user/hashtag/$1\">#$1</a>$2");

            Pattern hashTagPattern2 = Pattern.compile("#(" + tag.getText() + ")$");
            Matcher m4 = hashTagPattern2.matcher(formattedText);
            formattedText = m4.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/user/hashtag/$1\">#$1</a> ");
        }
        return formattedText;
    }

    protected String getFormattedTextForHashTags(String formattedText) {
        Pattern hashTagPattern = Pattern.compile("#(\\w*)(" + stopChar + ")");
        Matcher m3 = hashTagPattern.matcher(formattedText);
        formattedText = m3.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/$1\">#$1</a>$2");

        Pattern hashTagPattern2 = Pattern.compile("#(\\w*)$");
        Matcher m4 = hashTagPattern2.matcher(formattedText);
        formattedText = m4.replaceAll("<a class=\"tweet-action tweet-hashtag\" href=\"/hashtag/$1\">#$1</a> ");

        return formattedText;
    }

    protected String getFormattedTextForMedia(Set<Media> media, String formattedText) {
        for (Media medium : media) {
            if (medium.getMediaType().compareTo("photo") == 0) {
                Pattern myUrl1 = Pattern.compile("(" + medium.getUrl() + ")(" + stopChar + ")");
                Matcher m10 = myUrl1.matcher(formattedText);
                formattedText = m10.replaceAll("<br/><br/><a class=\"tweet-action tweet-photo\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a>$2");

                Pattern myUrl2 = Pattern.compile("(" + medium.getUrl() + ")$");
                Matcher m11 = myUrl2.matcher(formattedText);
                formattedText = m11.replaceAll("<br/><br/><a class=\"tweet-action tweet-photo\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a> ");
            }
        }
        return formattedText;
    }

    protected String getFormattedUrlForUrls(Set<Url> urls, String formattedText) {
        for (Url url : urls) {

            Pattern myUrl1 = Pattern.compile("(" + url.getUrl() + ")(" + stopChar + ")");
            Matcher m10 = myUrl1.matcher(formattedText);
            formattedText = m10.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url3\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl4 = Pattern.compile("(" + url.getUrl() + ")$");
            Matcher m20 = myUrl4.matcher(formattedText);
            formattedText = m20.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url3\" target=\"_blank\">" + url.getDisplay() + "</a> ");
        }
        return formattedText;
    }

    protected String getFormattedTextForUrls(Set<Url> urls, String formattedText) {
        for (Url url : urls) {

            Pattern myUrl2 = Pattern.compile("(" + url.getDisplay() + ")(" + stopChar + ")");
            Matcher m11 = myUrl2.matcher(formattedText);
            formattedText = m11.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-display1\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl5 = Pattern.compile("(" + url.getDisplay() + ")$");
            Matcher m21 = myUrl5.matcher(formattedText);
            formattedText = m21.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-display2\" target=\"_blank\">" + url.getDisplay() + "</a> ");

            Pattern myUrl3 = Pattern.compile("(" + url.getExpanded() + ")(" + stopChar + ")");
            Matcher m12 = myUrl3.matcher(formattedText);
            formattedText = m12.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-expanded1\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl6 = Pattern.compile("(" + url.getExpanded() + ")$");
            Matcher m22 = myUrl6.matcher(formattedText);
            formattedText = m22.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-expanded2\" target=\"_blank\">" + url.getDisplay() + "</a> ");

            Pattern myUrl1 = Pattern.compile("(" + url.getUrl() + ")(" + stopChar + ")");
            Matcher m10 = myUrl1.matcher(formattedText);
            formattedText = m10.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url1\" target=\"_blank\">" + url.getDisplay() + "</a>$2");

            Pattern myUrl4 = Pattern.compile("(" + url.getUrl() + ")$");
            Matcher m20 = myUrl4.matcher(formattedText);
            formattedText = m20.replaceAll("<a href=\"" + url.getExpanded() + "\" class=\"tw-url2\" target=\"_blank\">" + url.getDisplay() + "</a> ");

        }
        return formattedText;
    }

    static public String stopChar;

    static {
        StringBuffer x = new StringBuffer("[\\s");
        x.append("\\!");
        x.append("\\%");
        x.append("\\&");
        x.append("\\'");
        x.append("\\(");
        x.append("\\)");
        x.append("\\*");
        x.append("\\+");
        x.append("\\,");
        x.append("\\-");
        x.append("\\.");
        x.append("\\/");
        x.append("\\:");
        x.append("\\;");
        x.append("\\=");
        x.append("\\?");
        x.append("\\[");
        x.append("\\]");
        x.append("\\^");
        x.append("\\…");
        x.append("\\`");
        x.append("\\{");
        x.append("\\|");
        x.append("\\}");
        x.append("\\~");
        x.append("]");
        stopChar = x.toString();
    }

}
