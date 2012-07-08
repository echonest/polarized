package com.echonest.solr.search;

import java.util.List;

public interface PolarizedDocIdFetcher {

    public void open();

    public List<String> getDocIdList(String filterSetId);

    public Boolean hasMoreRecentIdList(String filterSetId);

    public void close();
}
