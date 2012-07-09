package com.echonest.solr.search;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.FunctionQParserPlugin;
import org.apache.solr.search.QParser;
import org.apache.lucene.search.Filter;
import org.apache.solr.search.QueryParsing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PolarizedQParser extends QParser {
    private static final Logger logger = LoggerFactory.getLogger(PolarizedQParser.class);
    private final PolarizedDocIdFetcher docIdFetcher;

    public PolarizedQParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req, PolarizedDocIdFetcher d) {
        super(qstr, localParams, params, req);
        docIdFetcher = d;
    }

    @Override
    public Query parse() throws ParseException {
        String funcStr = localParams.get(QueryParsing.V, null);

        //TODO: remove
        logger.info(String.format("localparams: %s", localParams.toString()));
        logger.info(String.format("funcStr: %s", funcStr));

        // create a filter from our catalog
        // TODO: this needs to handle multiple catalogs and join them in      the specified way
        Filter pFilter = new PolarizedFilter(docIdFetcher,  funcStr);

        // make a new PercolatorQuery from the filter and return that
        return new PolarizedQuery(pFilter);
    }
}
