package com.echonest.solr.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.apache.solr.search.QueryParsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

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

public class PolarizedPlugin extends QParserPlugin {
    public static String NAME = "polarized";
    private static final Logger logger = LoggerFactory.getLogger(PolarizedPlugin.class);
    PolarizedDocIdFetcher docIdFetcher;

    @Override
    public QParser createParser(String s, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        return new PolarizedQParser(s, localParams, params, req, docIdFetcher);
    }

    /*
        // Required Parameters
        //  - docFetcher
        //  - idField

        // Optional Parameters + Defaults
        //  - cache (false)
        //  - externalCacheVersioning (false)
     */
    public void init(NamedList args) {

        SolrParams defaults;
        Object d = args.get(PolarizedParams.CONFIG_DEFAULTS);
        if (d != null && d instanceof NamedList ) {
            defaults = SolrParams.toSolrParams((NamedList)d);
        } else {
            throw new SolrException(1, "No polarized defaults specified");
        }

        NamedList fetcherParams;
        Object fp = args.get(PolarizedParams.CONFIG_FETCHER);
        if (fp != null && fp instanceof NamedList ) {
            fetcherParams = (NamedList)fp;
        } else {
            fetcherParams = new NamedList();
        }

        String docFetcherName = defaults.get(PolarizedParams.CONFIG_DEFAULTS_FETCHER_CLASS);
        String idFieldName = defaults.get(PolarizedParams.CONFIG_DEFAULTS_ID_FIELD_NAME);

        try {
            Class<?> fetcherClass = Class.forName(docFetcherName);
            docIdFetcher = (PolarizedDocIdFetcher) fetcherClass.newInstance();
        } catch (Exception e) {
            throw new SolrException(1, "Invalid docFetcher class specified: " + docFetcherName, e);
        }
        docIdFetcher.init(fetcherParams);
        logger.warn("PolarizedPlugin INIT!!!!");
    }
}