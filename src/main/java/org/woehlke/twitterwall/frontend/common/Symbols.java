package org.woehlke.twitterwall.frontend.common;

/**
 * Created by tw on 01.07.17.
 */
public enum Symbols {

    TWEET("<i class=\"fa fa-twitter-square\" aria-hidden=\"true\"></i>"),
    USER("<i class=\"fa fa-user\" aria-hidden=\"true\"></i>"),
    HASHTAG("<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>"),
    MEDIA("<i class=\"fa fa-file-image-o\" aria-hidden=\"true\"></i>"),
    MENTION("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    TICKER_SYMBOL("<i class=\"fa fa-leaf\" aria-hidden=\"true\"></i>"),
    URL("<i class=\"fa fa-link\" aria-hidden=\"true\"></i>"),
    USERLIST("<i class=\"fa fa-user\" aria-hidden=\"true\"></i>"),
    TASK("<i class=\"fa fa-check-square\" aria-hidden=\"true\"></i>"),
    TASK_HISTORY("<i class=\"fa fa-check-square-o\" aria-hidden=\"true\"></i>"),
    TASKS_ALL("<i class=\"fa fa-check-square\" aria-hidden=\"true\"></i>"),
    TASK_START("<i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i>"),
    TWEETS_HOME_TIMELINE("<i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>"),
    TWEETS_USER_TIMELINE("<i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>"),
    TWEETS_MENTIONS("<i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>"),
    TWEETS_FAVORITES("<i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>"),
    TWEETS_RETWEETS_OF_ME("<i class=\"fa fa-twitter\" aria-hidden=\"true\"></i>"),
    USER_TWEETING("<i class=\"fa fa-user\" aria-hidden=\"true\"></i>"),
    USER_ALL("<i class=\"fa fa-user\" aria-hidden=\"true\"></i>"),
    USER_FRIENDS("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    USER_FOLLOWER("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    USER_ON_LIST("<i class=\"fa fa-handshake-o\" aria-hidden=\"true\"></i>"),
    USER_NOT_YET_ON_LIST("<i class=\"fa fa-handshake-o\" aria-hidden=\"true\"></i>"),
    USER_CONNECTIONS("<i class=\"fa fa-handshake-o\" aria-hidden=\"true\"></i>"),
    USER_NOT_YET_FRIENDS("<i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i>"),
    USER_PROFILE("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    IMPRINT("<i class=\"fa fa-university\" aria-hidden=\"true\"></i>"),
    HOME("<span class=\"glyphicon glyphicon-home\" aria-hidden=\"true\"></span>"),
    STARTPAGE("<i class=\"fa fa-play-circle\" aria-hidden=\"true\"></i>"),
    GET_TEST_DATA("<i class=\"fa fa-cubes\" aria-hidden=\"true\"></i>\n"),
    EXCEPTION("<i class=\"fa fa-bolt\" aria-hidden=\"true\"></i>"),
    LEAF("<i class=\"fa fa-leaf\" aria-hidden=\"true\"></i>"),
    DATABASE("<i class=\"fa fa-database\" aria-hidden=\"true\"></i>"),
    MANAGEMENT("<i class=\"fa fa-cog\" aria-hidden=\"true\"></i>"),
    DELETE_ALL("<i class=\"fa fa-trash\" aria-hidden=\"true\"></i>"),
    LOGIN("<i class=\"fa fa-sign-in\" aria-hidden=\"true\"></i>");


    Symbols(String html){
        this.html=html;
    }

    private String html;

    @Override
    public String toString(){
        return html;
    }

}
