package org.woehlke.twitterwall.oodm.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by tw on 14.07.17.
 */
public interface JdbcDao {

    Map<String,Integer> getTableCount();

    List<String> tableNamess();
}
