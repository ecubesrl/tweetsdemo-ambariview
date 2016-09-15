package it.ecubecenter.solr.client;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by gaido on 02/09/2016.
 */
public class SolrConnection {

    private static String solrConnectionString = "";
    private static SolrConnection ourInstance = null;

    private CloudSolrClient solr;

    public static synchronized SolrConnection getInstance(String connString) {
        if(!connString.equals(solrConnectionString)){
            ourInstance=new SolrConnection(connString);
        }
        return ourInstance;
    }

    private SolrConnection(String connString) {
        solrConnectionString=connString;
        solr = new CloudSolrClient(connString);
        solr.setDefaultCollection("twitter");
    }
    public QueryResponse querySolr(SolrQuery q) throws IOException, SolrServerException {
        try {
            LoggerFactory.getLogger(this.getClass()).info("Querying Solr: " + q.toString());
            return solr.query(q);
        } catch (Exception e) {
            solrConnectionString="";
            ourInstance=null;
            throw e;
        }
    }
    /*
    private void querySolr(String query) throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery(query);
        QueryResponse res = solr.query(q);
        res.getResults().get(0).getFieldValueMap()
    }
    */

}
