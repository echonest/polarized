package com.echonest.solr.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.solr.search.DelegatingCollector;
import org.apache.solr.search.PostFilter;
import org.apache.solr.search.SolrConstantScoreQuery;
import org.apache.solr.search.function.DocValues;
import org.apache.solr.search.function.ValueSourceScorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class PolarizedQuery extends SolrConstantScoreQuery implements PostFilter {
    private static final Logger logger = LoggerFactory.getLogger(PolarizedQuery.class);

    public PolarizedQuery(Filter filter) {
        super(filter);
    }

    public DelegatingCollector getFilterCollector(IndexSearcher indexSearcher) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getCache() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCache(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getCost() {
        return 101;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCost(int i) {
        logger.warn("Unable to change cost of PolarizedQuery");
    }

    public boolean getCacheSep() {
        return false;
    }

    public void setCacheSep(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
