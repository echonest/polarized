package com.echonest.solr.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PolarizedSQLFetcher implements PolarizedDocIdFetcher {
    private static final Logger logger = LoggerFactory.getLogger(PolarizedSQLFetcher.class);

    public void open() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<String> getDocIdList(String filterSetId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean hasMoreRecentIdList(String filterSetId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
