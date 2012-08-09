package com.echonest.solr;

import com.echonest.solr.search.PolarizedDocIdFetcher;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PolarizedMemcacheFetcher implements PolarizedDocIdFetcher {
    String[] servers;
    SockIOPool pool;
    MemCachedClient mc = null;
    private static final Logger logger = LoggerFactory.getLogger(PolarizedMemcacheFetcher.class);

    public PolarizedMemcacheFetcher() {
    }

    public void init(NamedList args) {
        SolrParams defaults = SolrParams.toSolrParams(args);
        String rawHostString = defaults.get("servers");
        servers = rawHostString.split(",");
        open();
    }

    public void open() {
        pool = SockIOPool.getInstance("PPool");
        pool.setServers( servers );
        pool.setFailover( true );
        pool.setInitConn( 10 );
        pool.setMinConn( 5 );
        pool.setMaxConn( 250 );
        pool.setMaintSleep( 30 );
        pool.setNagle( false );
        pool.setSocketTO( 500 );
        pool.setAliveCheck( true );
        pool.initialize();
        mc = new MemCachedClient("PPool");
    }

    public String[] getDocIdList(String filterSetId) {
        Object rawVal = mc.get(filterSetId);
        if (rawVal == null) {
            return new String[]{};
        }
        ArrayList<String> filterList = new ArrayList<String>();

        for (String s : ((String)rawVal).split(",")) {
            filterList.add(s.replace(" ", ""));
        }

        logger.info(String.format("retrieved %s: %s", filterSetId, filterList.toString()));
        return (String[])filterList.toArray();
    }

    public boolean isFresh(String filterSetId, String cacheVersion) {
        return true;
    }

    public int getCacheVersion(String filterSetId) {
        return 0;
    }
    public void close() {
        mc.syncAll();
        pool.shutDown();
    }
}
