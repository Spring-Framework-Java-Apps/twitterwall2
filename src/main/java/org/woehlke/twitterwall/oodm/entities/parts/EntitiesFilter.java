package org.woehlke.twitterwall.oodm.entities.parts;

import org.woehlke.twitterwall.oodm.entities.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.parts.HashTagText.HASHTAG_TEXT_PATTERN_TEXT;
import static org.woehlke.twitterwall.oodm.entities.parts.ScreenName.SCREEN_NAME_PATTERN_TEXT;
import static org.woehlke.twitterwall.oodm.entities.parts.UrlField.URL_PATTTERN_FOR_USER_HTTP_TEXT;
import static org.woehlke.twitterwall.oodm.entities.parts.UrlField.URL_PATTTERN_FOR_USER_HTTPS_TEXT;

/**
 * Created by tw on 15.07.17.
 */
public class EntitiesFilter {

    protected Set<Mention> findByUserDescription(String description,Task task) {
        Set<Mention> mentions = new LinkedHashSet<>();
        if (description != null) {
            String USER_PROFILE_INPUT[] = {
                "@("+ SCREEN_NAME_PATTERN_TEXT +")(" + Entities.stopChar + ")",
                "@("+ SCREEN_NAME_PATTERN_TEXT +")$"
            };
            int USER_PROFILE_OUTPUT[] = {
                1, 1
            };
            for(int i=0; i<2;i++){
                Pattern mentionPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = mentionPattern.matcher(description);
                while (m.find()) {
                    Mention newMention = new Mention(task,null,m.group(USER_PROFILE_OUTPUT[i]));
                    if(!mentions.contains(newMention)){
                        mentions.add(newMention);
                    }
                }
            }
        }
        return mentions;
    }

    protected String getFormattedTextForMentions(Set<Mention> mentions, String formattedText) {
        for (Mention mention : mentions) {
            if(mention.isProxy() && mention.hasUser() && (!mention.getScreenName().getScreenName().isEmpty())) {
                String USER_PROFILE_INPUT[] = {
                    "@(" + mention.getScreenName().getScreenName() + ")(" + stopChar + ")",
                    "@(" + mention.getScreenName().getScreenName() + ")$"
                };
                String USER_PROFILE_OUTPUT[] = {
                    " <a class=\"tweet-action tweet-profile1\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a>$2",
                    " <a class=\"tweet-action tweet-profile2\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a> "
                };
                for(int i=0;i<2;i++){
                    Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                    Matcher m = userPattern.matcher(formattedText);
                    formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
                }
            }
        }
        return formattedText;
    }

    protected String getFormattedTextForUserProfiles(String formattedText) {
        String USER_PROFILE_INPUT[] = {
            "(" + stopChar + ")@("+SCREEN_NAME_PATTERN_TEXT+")(" + stopChar + ")",
            "(" + stopChar + ")@("+SCREEN_NAME_PATTERN_TEXT+")$",
            "^@("+SCREEN_NAME_PATTERN_TEXT+")(" + stopChar + ")",
            "^@("+SCREEN_NAME_PATTERN_TEXT+")$"
        };
        String USER_PROFILE_OUTPUT[] = {
            " <a class=\"tweet-action tweet-profile1\" href=\"/user/screenName/$2\">@$2</a>$3",
            " <a class=\"tweet-action tweet-profile2\" href=\"/user/screenName/$2\">@$2</a>",
            " <a class=\"tweet-action tweet-profile3\" href=\"/user/screenName/$1\">@$1</a>$2",
            " <a class=\"tweet-action tweet-profile4\" href=\"/user/screenName/$1\">@$1</a>"
        };
        for(int i=0;i<4;i++){
            Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
            Matcher m = userPattern.matcher(formattedText);
            formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
        }
        return formattedText;
    }

    protected Set<HashTag> getHashTagsForDescription(String description,Task task) {
        Set<HashTag> hashTags = new LinkedHashSet<>();
        if (description != null) {
            String USER_PROFILE_INPUT[] = {
                "#("+HASHTAG_TEXT_PATTERN_TEXT+")(" + Entities.stopChar + ")",
                "#("+HASHTAG_TEXT_PATTERN_TEXT+")$"
            };
            int USER_PROFILE_OUTPUT[] = {
                1, 1
            };
            for(int i=0;i<2;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    HashTagText text = new HashTagText(m.group(USER_PROFILE_OUTPUT[i]));
                    hashTags.add(new HashTag(task,null,text));
                }
            }
        }
        return hashTags;
    }

    protected String getFormattedTextForHashTags(Set<HashTag> tags, String formattedText ) {
        for (HashTag tag : tags) {
            long tagId = tag.getId();
            String USER_PROFILE_INPUT[] = {
                "#(" + tag.getHashTagText().getText() + ")(" + stopChar + ")",
                "#(" + tag.getHashTagText().getText() + ")$"
            };
            String USER_PROFILE_OUTPUT[] = {
                " <a class=\"tweet-action tweet-hashtag1\" href=\"/hashtag/"+tagId+"\">#$1</a>$2",
                " <a class=\"tweet-action tweet-hashtag2\" href=\"/hashtag/"+tagId+"\">#$1</a> "
            };
            for(int i=0;i<2;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(formattedText);
                formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
            }
        }
        return formattedText;
    }

    protected Set<TickerSymbol> getTickerSymbolsFor(String description,Task task) {
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
        if (description != null) {
            String USER_PROFILE_INPUT[] = {
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")$",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")$"
            };
            int USER_PROFILE_OUTPUT[] = {
                2, 1, 2, 2, 1, 2
            };
            for(int i=0;i<8;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    UrlField uf = new UrlField(m.group(USER_PROFILE_OUTPUT[i]));
                    tickerSymbols.add(new TickerSymbol(task,null,uf));
                }
            }
        }
        return tickerSymbols;
    }

    protected String getFormattedTextForTickerSymbols(Set<TickerSymbol> tickerSymbols, String formattedText) {
        for(TickerSymbol tickerSymbol:tickerSymbols){
            String USER_PROFILE_INPUT[] = {
                "(" + tickerSymbol.getUrl().getUrl() + ")(" + stopChar + ")",
                "(" + tickerSymbol.getUrl().getUrl() + ")$"
            };
            String USER_PROFILE_OUTPUT[] = {
                "<br/><br/><a class=\"tweet-action tweet-photo1\" href=\"" + tickerSymbol.getUrl().getUrl() + "\" target=\"_blank\">"+tickerSymbol.getTickerSymbol()+"</a>$2",
                "<br/><br/><a class=\"tweet-action tweet-photo2\" href=\"" + tickerSymbol.getUrl().getUrl() + "\" target=\"_blank\">"+tickerSymbol.getTickerSymbol()+"</a> "
            };
            for(int i=0;i<2;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(formattedText);
                formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
            }
        }
        return formattedText;
    }


    protected Set<Media> getMediaForDescription(String description,Task task) {
        Set<Media> media =  new LinkedHashSet<Media>();
        if (description != null) {
            String USER_PROFILE_INPUT[] = {
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")$",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")$"
            };
            int USER_PROFILE_OUTPUT[] = {
                2, 1, 2, 2, 1, 2
            };
            for(int i=0;i<6;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    UrlField uf = new UrlField(m.group(USER_PROFILE_OUTPUT[i]));
                    media.add(new Media(task,null,uf));
                }
            }
        }
        return media;
    }


    protected String getFormattedTextForMedia(Set<Media> media, String formattedText) {
        for (Media medium : media) {
            if (medium.getMediaType().compareTo("photo") == 0) {
                String USER_PROFILE_INPUT[] = {
                    "(" + medium.getUrl().getUrl() + ")(" + stopChar + ")",
                    "(" + medium.getUrl().getUrl() + ")$"
                };
                String USER_PROFILE_OUTPUT[] = {
                    "<br/><br/><a class=\"tweet-action tweet-photo1\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a>$2",
                    "<br/><br/><a class=\"tweet-action tweet-photo2\" href=\"" + medium.getExpanded() + "\" target=\"_blank\"><img class=\"tweet-photo\" src=\"" + medium.getMediaHttps() + "\" /></a> "
                };
                for(int i=0;i<2;i++){
                    Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                    Matcher m = userPattern.matcher(formattedText);
                    formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
                }
            }
        }
        return formattedText;
    }

    public String getFormattedUrlForUrls(Set<Url> urls, String formattedText) {
        for (Url url : urls) {
            String USER_PROFILE_INPUT=url.getUrl().getUrl();
            String USER_PROFILE_OUTPUT="<a href=\"" + url.getExpanded() + "\" class=\"tw-url-db\" target=\"_blank\">" + url.getDisplay() + "</a>";
            Pattern myUrl = Pattern.compile(USER_PROFILE_INPUT);
            Matcher m = myUrl.matcher(formattedText);
            formattedText = m.replaceAll(USER_PROFILE_OUTPUT);
        }
        return formattedText;
    }

    protected Set<Url> getUrlsForDescription(String description,Task task) {
        Set<Url> urls = new LinkedHashSet<>();
        if (description != null) {
            String USER_PROFILE_INPUT[] = {
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + stopChar + ")",
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")$",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")$",
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + stopChar + ")",
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")$",
                "^(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")$",
            };
            int USER_PROFILE_OUTPUT[] = {
                2, 2, 1, 1, 2, 2, 1, 1
            };
            for(int i=0;i<8;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    UrlField uf = new UrlField(m.group(USER_PROFILE_OUTPUT[i]));
                    urls.add(new Url(task,null, uf));
                }
            }
        }
        return urls;
    }

    protected String getFormattedTextForUrls( String formattedText) {
        String USER_PROFILE_INPUT[] = {
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + stopChar + ")",
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")$",
            "^(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")(" + stopChar + ")",
            "^(" + URL_PATTTERN_FOR_USER_HTTPS_TEXT + ")$",
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + stopChar + ")",
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")$",
            "^(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")(" + stopChar + ")",
            "^(" + URL_PATTTERN_FOR_USER_HTTP_TEXT + ")$",
        };
        String USER_PROFILE_OUTPUT[] = {
            "$1<a href=\"$2\" class=\"tw-url-regex1\" target=\"_blank\">$2</a>$3",
            "$1<a href=\"$2\" class=\"tw-url-regex2\" target=\"_blank\">$2</a> ",
            "<a href=\"$1\" class=\"tw-url-regex3\" target=\"_blank\">$1</a>$2",
            "<a href=\"$1\" class=\"tw-url-regex4\" target=\"_blank\">$1</a> ",
            "$1<a href=\"$2\" class=\"tw-url-regex5\" target=\"_blank\">$2</a>$3",
            "$1<a href=\"$2\" class=\"tw-url-regex6\" target=\"_blank\">$2</a> ",
            "<a href=\"$1\" class=\"tw-url-regex7\" target=\"_blank\">$1</a>$2",
            "<a href=\"$1\" class=\"tw-url-regex8\" target=\"_blank\">$1</a> ",
        };
        for(int i=0;i<8;i++){
            Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
            Matcher m = userPattern.matcher(formattedText);
            formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
        }
        return formattedText;
    }

    protected String getFormattedTextForUrls(Set<Url> urls, String formattedText) {
        for (Url url : urls) {
            String USER_PROFILE_INPUT[] = {
                "(" + url.getDisplay() + ")(" + stopChar + ")",
                "(" + url.getDisplay() + ")$",
                "(" + url.getExpanded() + ")(" + stopChar + ")",
                "(" + url.getExpanded() + ")$",
                "(" + url.getUrl().getUrl() + ")(" + stopChar + ")",
                "(" + url.getUrl().getUrl() + ")$"
            };
            String USER_PROFILE_OUTPUT[] = {
                " <a href=\"" + url.getExpanded() + "\" class=\"tw-display1\" target=\"_blank\">" + url.getDisplay() + "</a>$2",
                " <a href=\"" + url.getExpanded() + "\" class=\"tw-display2\" target=\"_blank\">" + url.getDisplay() + "</a> ",
                " <a href=\"" + url.getExpanded() + "\" class=\"tw-expanded1\" target=\"_blank\">" + url.getDisplay() + "</a>$2",
                " <a href=\"" + url.getExpanded() + "\" class=\"tw-expanded2\" target=\"_blank\">" + url.getDisplay() + "</a> ",
                " <a href=\"" + url.getExpanded() + "\" class=\"tw-url1\" target=\"_blank\">" + url.getDisplay() + "</a>$2",
                " <a href=\"" + url.getExpanded() + "\" class=\"tw-url2\" target=\"_blank\">" + url.getDisplay() + "</a> "
            };
            for(int i=0;i<6;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(formattedText);
                formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
            }
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
