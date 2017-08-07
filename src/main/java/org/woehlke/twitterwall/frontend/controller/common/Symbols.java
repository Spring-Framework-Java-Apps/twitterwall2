package org.woehlke.twitterwall.frontend.controller.common;

/**
 * Created by tw on 01.07.17.
 */
public enum Symbols {

    HASHTAG("<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>"),
    IMPRINT("<i class=\"fa fa-university\" aria-hidden=\"true\"></i>"),
    PROFILE("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    HOME("<span class=\"glyphicon glyphicon-home\" aria-hidden=\"true\"></span>"),
    USER_ALL("<i class=\"fa fa-user\" aria-hidden=\"true\"></i>"),
    USER_TWEETS("<i class=\"fa fa-user\" aria-hidden=\"true\"></i>"),
    USER_NOT_YET_FRIENDS("<i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i>"),
    GET_TEST_DATA("<i class=\"fa fa-cubes\" aria-hidden=\"true\"></i>\n"),
    EXCEPTION("<i class=\"fa fa-bolt\" aria-hidden=\"true\"></i>"),
    LEAF("<i class=\"fa fa-leaf\" aria-hidden=\"true\"></i>"),
    DATABASE("<i class=\"fa fa-database\" aria-hidden=\"true\"></i>"),
    USER_NOT_YET_ON_LIST("<i class=\"fa fa-handshake-o\" aria-hidden=\"true\"></i>\n"),
    USER_FRIENDS("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    USER_FOLLOWER("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>"),
    TASK("<i class=\"fa fa-check-square\" aria-hidden=\"true\"></i>"),
    TASK_HISTORY("<i class=\"fa fa-check-square-o\" aria-hidden=\"true\"></i>");

    Symbols(String html){
        this.html=html;
    }

    private String html;

    @Override
    public String toString(){
        return html;
    }

}
