package org.woehlke.twitterwall.frontend.common;

/**
 * Created by tw on 01.07.17.
 */
public enum UrlPaths {

    TWEETS("/tweets","timeline","<span class=\"glyphicon glyphicon-home\" aria-hidden=\"true\"></span>"),
    USER_ALL("/user/all","user","<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    USER_TWEETS("/user/tweets","user","<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    USER_NOTYETFIRENDS("/user/notyetfriends","user","<i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i>"),
    PROFILE("/profile/{screenName}","profile","<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    GET_TEST_DATA("/getTestData","timeline","<i class=\"fa fa-cubes\" aria-hidden=\"true\"></i>"),
    IMPRINT("/imprint","imprint","<i class=\"fa fa-university\" aria-hidden=\"true\"></i>"),
    HASHTAGS("/hashtags","tags","<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>"),
    HASHTAG("/hashtag/{hashtagText}","timeline","<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>"),
    HASHTAG_USER("/user/hashtag/{hashtagText}","user","<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>");

    UrlPaths(String urlPath,String template,String symbol){
        this.urlPath=urlPath;
        this.template=template;
        this.symbol=symbol;
    }

    private String urlPath;

    private String template;

    private String symbol;


    public String getUrlPath() {
        return urlPath;
    }

    public String getTemplate() {
        return template;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString(){
        return urlPath;
    }
}
