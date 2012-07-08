package com.echonest.solr.search;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.FunctionQParserPlugin;
import org.apache.solr.search.QParser;
import org.apache.lucene.search.Filter;
import org.apache.solr.search.QueryParsing;
import org.apache.solr.search.function.FunctionQuery;
import org.apache.solr.search.function.QueryValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PolarizedQParser extends QParser{
    private static final Logger logger = LoggerFactory.getLogger(PolarizedQParser.class);

    public PolarizedQParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        super(qstr, localParams, params, req);
    }

    private Filter getFilter(String filterSetId) {
        return new PolarizedFilter(filterSetId);
    }

    @Override
    public Query parse() throws ParseException {
        String funcStr = localParams.get(QueryParsing.V, null);
        Query funcQ = subQuery(funcStr, FunctionQParserPlugin.NAME).getQuery();

        //TODO: remove
        logger.warn(String.format("funcQ: %s", funcQ.toString()));

        // create a filter of our catalogs
        Filter pFilter = getFilter("ABCDE");

        // make a new PercolatorQuery from the filter and return that
        return new PolarizedQuery(pFilter);
    }
}
