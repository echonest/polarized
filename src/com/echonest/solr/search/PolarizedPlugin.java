package com.echonest.solr.search;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Some filters are slow enough that you don’t even want to run them in parallel
with the query and other filters, even if they are consulted last, since asking
them “what is the next doc you match on or after this given doc” is so expensive.
For these types of filters, you really want to only ask them “do you match this doc”
only after the query and all other filters have been consulted. Solr has special
support for this called “post filtering“.

Post filtering is triggered by filters that have a cost>=100 and have explicit
support for it. If there are multiple post filters in a single request, they will
be ordered by cost.

The frange qparser has post filter support and allows powerful queries specifying
ranges over arbitrarily complex function queries.
*/

class PolarizedPlugin extends QParserPlugin {
    public static String NAME = "polarized";
    private static final Logger logger = LoggerFactory.getLogger(PolarizedPlugin.class);
    PolarizedDocIdFetcher docIdFetcher;

    @Override
    public QParser createParser(String s, SolrParams solrParams, SolrParams solrParams1, SolrQueryRequest solrQueryRequest) {
        return new PolarizedQParser(s, solrParams, solrParams1, solrQueryRequest);
    }

    public void init(NamedList namedList) {
        SolrResourceLoader loader = new SolrResourceLoader( null );
        try {
            docIdFetcher = (PolarizedDocIdFetcher) loader.findClass((String)namedList.get("docFetcher")).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}