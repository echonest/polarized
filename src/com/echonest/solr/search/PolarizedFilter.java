package com.echonest.solr.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Filter;
import org.apache.solr.search.BitDocSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class PolarizedFilter extends Filter {
    private static final Logger logger = LoggerFactory.getLogger(PolarizedFilter.class);
    BitDocSet docSet = null;

    public PolarizedFilter(String filterSetId) {
        populateDocSetFromSetId(filterSetId);
    }

    private void populateDocSetFromSetId(String filterSetId) {
        docSet = new BitDocSet();
        // do query
        // add each doc to docSet
    }

    @Override
    public DocIdSet getDocIdSet(IndexReader indexReader) throws IOException {
        return new DocIdSet() {
            @Override
            public DocIdSetIterator iterator() throws IOException {
                return docSet.getBits().iterator();
            }
        };
    }
}
