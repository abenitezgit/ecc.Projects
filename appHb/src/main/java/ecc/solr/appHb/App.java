package ecc.solr.appHb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;


public class App 
{
    public static void main( String[] args )
    {
    	List<String> confs = new ArrayList<>();
    	confs.add("/usr/local/hbase_conf/cloud/hbase-site.xml");
    	confs.add("/usr/local/hbase_conf/cloud/core-site.xml");
    	confs.add("/usr/local/hbase_conf/cloud/hdfs-site.xml");

    	
		Configuration config = HBaseConfiguration.create();
		
        for (int i=0; i<confs.size(); i++) {
        	System.out.println("host: "+confs.get(i));
            config.addResource(new Path(confs.get(i)));
        }
        
        HTablePool pool = new HTablePool(config,1000);
        
        HTableInterface hTable = pool.getTable("grabaciones");

		
//				String key = (String) doc.getFieldValue("id");
//                Get g = new Get(Bytes.toBytes(key));
//                Result rs = hTable.get(g);
//
//                Grabacion grabacion = new Grabacion();
//                for (KeyValue kv : rs.raw()) {
//                    setValue(kv, grabacion);
//                }
//                mapGrab.put((String) doc.getFieldValue("id"), grabacion);

    }
}
