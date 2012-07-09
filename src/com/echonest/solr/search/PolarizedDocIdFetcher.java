package com.echonest.solr.search;

import org.apache.solr.common.util.NamedList;

import java.util.List;

public interface PolarizedDocIdFetcher {

    public void init(NamedList args);

    public String[] getDocIdList(String filterSetId);

    public int getCacheVersion(String filterSetId);

    public boolean isFresh(String filterSetId, String cacheVersion);


}
