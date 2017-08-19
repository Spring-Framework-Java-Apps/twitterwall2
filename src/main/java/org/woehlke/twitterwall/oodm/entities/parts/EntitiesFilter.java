package org.woehlke.twitterwall.oodm.entities.parts;

import org.woehlke.twitterwall.oodm.entities.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.HashTag.HASHTAG_TEXT_PATTERN;
import static org.woehlke.twitterwall.oodm.entities.Url.URL_PATTTERN_FOR_USER_HTTP;
import static org.woehlke.twitterwall.oodm.entities.Url.URL_PATTTERN_FOR_USER_HTTPS;

/**
 * Created by tw on 15.07.17.
 */
public class EntitiesFilter {

    protected Set<Mention> findByUserDescription(String description,Task task) {
        Set<Mention> mentions = new LinkedHashSet<>();
        Set<String> screenNames = new LinkedHashSet<>();
        if (description != null) {

            String USER_PROFILE_INPUT[] = {
                    "@("+ User.SCREEN_NAME_PATTERN +")(" + Entities.stopChar + ")",
                    "@("+ User.SCREEN_NAME_PATTERN +")$"
            };

            int USER_PROFILE_OUTPUT[] = {
                    1, 1
            };

            for(int i=0; i<2;i++){
                Pattern mentionPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = mentionPattern.matcher(description);
                while (m.find()) {
                    String screenName = m.group(USER_PROFILE_OUTPUT[i]);
                    screenNames.add(screenName);
                }
            }

            for(String screenName:screenNames){
                Mention newMention = Mention.createFromUserDescriptionOrTweetText(task,screenName);
                mentions.add(newMention);
            }
        }
        return mentions;
    }

    protected String getFormattedTextForMentions(Set<Mention> mentions, String formattedText) {
        for (Mention mention : mentions) {
            if(mention != null) {
                if(!mention.getScreenName().isEmpty()) {

                    String USER_PROFILE_INPUT[] = {
                            "@(" + mention.getScreenName() + ")(" + stopChar + ")",
                            "@(" + mention.getScreenName() + ")$"
                    };

                    String USER_PROFILE_OUTPUT[] = {
                            " <a class=\"tweet-action tweet-profile1\" href=\"/user/"+mention.getIdOfUser()+"\" target=\"_blank\">@$1</a>$2",
                            " <a class=\"tweet-action tweet-profile2\" href=\"/user/"+mention.getIdOfUser()+"\" target=\"_blank\">@$1</a> "
                    };

                    String USER_PROFILE_OUTPUT_UNDEFINED[] = {
                            " <a class=\"tweet-action tweet-profile1\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a>$2",
                            " <a class=\"tweet-action tweet-profile2\" href=\"https://twitter.com/$1\" target=\"_blank\">@$1</a> "
                    };

                    for (int i = 0; i < 2; i++) {
                        Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                        Matcher m = userPattern.matcher(formattedText);
                        if(mention.isNotFetchedFromTwitter()){
                            formattedText = m.replaceAll(USER_PROFILE_OUTPUT_UNDEFINED[i]);
                        } else {
                            formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
                        }
                    }
                }
            }
        }
        return formattedText;
    }

    /*
    protected String getFormattedTextForMentionsForTweets(Set<Mention> mentions, String formattedText) {
        for (Mention mention : mentions) {
            if(mention != null) {
                if (mention.isProxy() && mention.hasUser() && (!mention.getScreenName().isEmpty())) {

                    String USER_PROFILE_INPUT[] = {
                        "@(" + mention.getScreenName() + ")(" + stopChar + ")",
                        "@(" + mention.getScreenName() + ")$"
                    };

                    String USER_PROFILE_OUTPUT[] = {
                        " <a class=\"tweet-action tweet-profile1\" href=\"/user/"+mention.getIdTwitterOfUser()+"\" target=\"_blank\">@" + mention.getScreenName() + "</a>$2",
                        " <a class=\"tweet-action tweet-profile2\" href=\"/user/"+mention.getIdTwitterOfUser()+"\" target=\"_blank\">@" + mention.getScreenName() + "</a> "
                    };

                    String USER_PROFILE_OUTPUT_UNDEFINED[] = {
                        " <a class=\"tweet-action tweet-profile1\" href=\"/user/"+mention.getIdTwitterOfUser()+"\" target=\"_blank\">@" + mention.getScreenName() + "</a>$2",
                        " <a class=\"tweet-action tweet-profile2\" href=\"/user/"+mention.getIdTwitterOfUser()+"\" target=\"_blank\">@" + mention.getScreenName() + "</a> "
                    };

                    for (int i = 0; i < 2; i++) {
                        Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                        Matcher m = userPattern.matcher(formattedText);
                        if(mention.isNotFetchedFromTwitter()){
                            formattedText = m.replaceAll(USER_PROFILE_OUTPUT_UNDEFINED[i]);
                        } else {
                            formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
                        }
                    }
                }
            }
        }
        return formattedText;
    }
    */

    protected String getFormattedTextForUserProfiles(String formattedText) {

        String USER_PROFILE_INPUT[] = {
            "(" + stopChar + ")@(\\w{1,15})(" + stopChar + ")",
            "(" + stopChar + ")@(\\w{1,15})$",
            "^@(\\w{1,15})(" + stopChar + ")",
            "^@(\\w{1,15})$"
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
                "#("+HASHTAG_TEXT_PATTERN+")(" + Entities.stopChar + ")",
                "#("+HASHTAG_TEXT_PATTERN+")$"
            };

            int USER_PROFILE_OUTPUT[] = {
                1, 1
            };

            for(int i=0;i<2;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    hashTags.add(new HashTag(task,null,m.group(USER_PROFILE_OUTPUT[i])));
                }
            }
        }
        return hashTags;
    }

    protected String getFormattedTextForHashTags(Set<HashTag> tags, String formattedText ) {
        for (HashTag tag : tags) {

            long tagId = tag.getId();

            String USER_PROFILE_INPUT[] = {
                "#(" + tag.getText() + ")(" + stopChar + ")",
                "#(" + tag.getText() + ")$"
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
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$"
            };

            int USER_PROFILE_OUTPUT[] = {
                2, 1, 2, 2, 1, 2
            };

            for(int i=0;i<8;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    tickerSymbols.add(new TickerSymbol(task,null,m.group(USER_PROFILE_OUTPUT[i])));
                }
            }
        }
        return tickerSymbols;
    }

    protected String getFormattedTextForTickerSymbols(Set<TickerSymbol> tickerSymbols, String formattedText) {
        for(TickerSymbol tickerSymbol:tickerSymbols){

            String USER_PROFILE_INPUT[] = {
                "(" + tickerSymbol.getUrl() + ")(" + stopChar + ")",
                "(" + tickerSymbol.getUrl() + ")$"
            };

            String USER_PROFILE_OUTPUT[] = {
                "<br/><br/><a class=\"tweet-action tweet-photo1\" href=\"" + tickerSymbol.getUrl() + "\" target=\"_blank\">"+tickerSymbol.getTickerSymbol()+"</a>$2",
                "<br/><br/><a class=\"tweet-action tweet-photo2\" href=\"" + tickerSymbol.getUrl() + "\" target=\"_blank\">"+tickerSymbol.getTickerSymbol()+"</a> "
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
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")",
                "(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$"
            };

            int USER_PROFILE_OUTPUT[] = {
                2, 1, 2, 2, 1, 2
            };

            for(int i=0;i<6;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    media.add(new Media(task,null,m.group(USER_PROFILE_OUTPUT[i])));
                }
            }
        }
        return media;
    }


    protected String getFormattedTextForMedia(Set<Media> media, String formattedText) {
        for (Media medium : media) {
            if (medium.getMediaType().compareTo("photo") == 0) {

                String USER_PROFILE_INPUT[] = {
                    "(" + medium.getUrl() + ")(" + stopChar + ")",
                    "(" + medium.getUrl() + ")$"
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
            if(url!=null) {
                String USER_PROFILE_INPUT = url.getUrl();
                String USER_PROFILE_OUTPUT = "<a href=\"" + url.getExpanded() + "\" class=\"tw-url-db\" target=\"_blank\">" + url.getDisplay() + "</a>";

                Pattern myUrl = Pattern.compile(USER_PROFILE_INPUT);
                Matcher m = myUrl.matcher(formattedText);
                formattedText = m.replaceAll(USER_PROFILE_OUTPUT);
            }
        }
        return formattedText;
    }

    protected Set<Url> getUrlsForDescription(String description,Task task) {
        Set<Url> urls = new LinkedHashSet<>();
        Set<String> urlStrings = new LinkedHashSet<>();
        if (description != null) {

            String USER_PROFILE_INPUT[] = {
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + stopChar + ")",
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTPS + ")$",
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + stopChar + ")",
                "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$",
                "^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + stopChar + ")",
                "^(" + URL_PATTTERN_FOR_USER_HTTP + ")$",
            };

            int USER_PROFILE_OUTPUT[] = {
                2, 2, 1, 1, 2, 2, 1, 1
            };

            for(int i=0;i<8;i++){
                Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                Matcher m = userPattern.matcher(description);
                while (m.find()) {
                    String urlString = m.group(USER_PROFILE_OUTPUT[i]);
                    urlStrings.add(urlString);
                }
            }

            for(String urlString:urlStrings){
                Url newUrl = Url.createFromText(task,urlString);
                urls.add(newUrl);
            }
        }
        return urls;
    }

    protected String getFormattedTextForUrls( String formattedText) {

        String USER_PROFILE_INPUT[] = {
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + stopChar + ")",
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$",
            "^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + stopChar + ")",
            "^(" + URL_PATTTERN_FOR_USER_HTTPS + ")$",
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + stopChar + ")",
            "(" + stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$",
            "^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + stopChar + ")",
            "^(" + URL_PATTTERN_FOR_USER_HTTP + ")$",
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
            if(url!=null) {
                String USER_PROFILE_INPUT[] = {
                        "(" + url.getDisplay() + ")(" + stopChar + ")",
                        "(" + url.getDisplay() + ")$",
                        "(" + url.getExpanded() + ")(" + stopChar + ")",
                        "(" + url.getExpanded() + ")$",
                        "(" + url.getUrl() + ")(" + stopChar + ")",
                        "(" + url.getUrl() + ")$"
                };

                String USER_PROFILE_OUTPUT[] = {
                        " <a href=\"" + url.getExpanded() + "\" class=\"tw-display1\" target=\"_blank\">" + url.getDisplay() + "</a>$2",
                        " <a href=\"" + url.getExpanded() + "\" class=\"tw-display2\" target=\"_blank\">" + url.getDisplay() + "</a> ",
                        " <a href=\"" + url.getExpanded() + "\" class=\"tw-expanded1\" target=\"_blank\">" + url.getDisplay() + "</a>$2",
                        " <a href=\"" + url.getExpanded() + "\" class=\"tw-expanded2\" target=\"_blank\">" + url.getDisplay() + "</a> ",
                        " <a href=\"" + url.getExpanded() + "\" class=\"tw-url1\" target=\"_blank\">" + url.getDisplay() + "</a>$2",
                        " <a href=\"" + url.getExpanded() + "\" class=\"tw-url2\" target=\"_blank\">" + url.getDisplay() + "</a> "
                };

                String USER_PROFILE_OUTPUT_UNDEFINED[] = {
                        " <a href=\"" + url.getUrl() + "\" class=\"tw-display1\" target=\"_blank\">" + url.getUrl() + "</a>$2",
                        " <a href=\"" + url.getUrl() + "\" class=\"tw-display2\" target=\"_blank\">" + url.getUrl() + "</a> ",
                        " <a href=\"" + url.getUrl() + "\" class=\"tw-expanded1\" target=\"_blank\">" + url.getUrl() + "</a>$2",
                        " <a href=\"" + url.getUrl() + "\" class=\"tw-expanded2\" target=\"_blank\">" + url.getUrl() + "</a> ",
                        " <a href=\"" + url.getUrl() + "\" class=\"tw-url1\" target=\"_blank\">" + url.getUrl() + "</a>$2",
                        " <a href=\"" + url.getUrl() + "\" class=\"tw-url2\" target=\"_blank\">" + url.getUrl() + "</a> "
                };

                for (int i = 0; i < 6; i++) {
                    Pattern userPattern = Pattern.compile(USER_PROFILE_INPUT[i]);
                    Matcher m = userPattern.matcher(formattedText);
                    if(url.isRawUrlsFromDescription()){
                        formattedText = m.replaceAll(USER_PROFILE_OUTPUT_UNDEFINED[i]);
                    } else {
                        formattedText = m.replaceAll(USER_PROFILE_OUTPUT[i]);
                    }
                }
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
