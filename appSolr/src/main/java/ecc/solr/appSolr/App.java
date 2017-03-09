package ecc.solr.appSolr;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SolrServerException, IOException
    {
		CloudSolrServer server = new CloudSolrServer("cloudera1:2181,cloudera3:2181,cloudera2:2181/solr");
		
		server.setDefaultCollection("grabaciones_nuevo");
		SolrQuery solrQuery = new SolrQuery("*.*");
		
		ModifiableSolrParams parameters = new ModifiableSolrParams();
	      parameters.set("q", "codigoservicio:CAAB0750");
	      //parameters.set("fq", "connid,agente");
	      parameters.set("fl", "id, connid, agente");
	      parameters.set("start", 0);
	      parameters.set("rows", 100);
		
		QueryResponse response1 = server.query(parameters);
		
		
		SolrDocumentList dList = response1.getResults();
		for (int i = 0; i < dList.getNumFound(); i++) 
		{
		  for (Map.Entry mE : dList.get(i).entrySet()) 
		    {
		      System.out.println(mE.getKey() + ":" + mE.getValue());
		  }
		}
    }
}
