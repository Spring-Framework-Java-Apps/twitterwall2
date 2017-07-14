package org.woehlke.twitterwall.oodm.repository.application;

import java.util.List;
import java.util.Map;

/**
 * Created by tw on 14.07.17.
 */
public interface JdbcRepository {

    Map<String,Integer> getTableCount();

    List<String> tableNamess();
}
