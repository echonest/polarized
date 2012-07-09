package com.echonest.solr.search;

import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PolarizedSQLFetcher implements PolarizedDocIdFetcher {

    public void init(NamedList args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String[] getDocIdList(String filterSetId) {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getCacheVersion(String filterSetId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isFresh(String filterSetId, String cacheVersion) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
