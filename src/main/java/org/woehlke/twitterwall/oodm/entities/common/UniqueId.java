package org.woehlke.twitterwall.oodm.entities.common;

import java.util.Map;

public interface UniqueId extends Validateable {

    String getUniqueId();

    Map<String,Object> getParametersForFindByUniqueId();

    String getQueryNameForFindByUniqueId();
}
