package ecc.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import common.dataAccess.SolRDB;
import common.ftp.FTPClientWav;
import common.rutinas.Rutinas;
import ecc.utiles.GlobalArea;

public class ExtractService {
	GlobalArea gDatos = new GlobalArea();
	Rutinas mylib = new Rutinas();
	
	
	public String getWav(String fname) throws Exception {
		int exitStatus=0;
		String newFName="";
		try {
			FTPClientWav ftp = new FTPClientWav();
			ftp.setConfig(gDatos.getFileConfig(), gDatos.getHbProperties());
			
			if (ftp.validaParams()) {
				ftp.getWAVtoMP3(fname);
				
				if (ftp.exitStatus()==0) {
					exitStatus=0;
					newFName = ftp.getDecodedName();
					mylib.console("FTP y Conversión Exitoso!");
				} else {
					exitStatus=99;
					mylib.console("Error FTP y Conversion");
				}
			} else {
				mylib.console("Error getWav (validando parametros ftp)");
				exitStatus=99;
			}
			
			if (exitStatus==0) {
				return newFName;
			} else {
				return "";
			}
		} catch (Exception e) {
			mylib.console(1,"Error getWav ("+e.getMessage()+")");
			throw new Exception(e.getMessage());
		}
	}
	
	public String getURL(String connid) throws Exception {
		SolRDB solrConn = new SolRDB();
		try {
			String ftpDir="";
			String fname="";
			
			solrConn.setConfig(gDatos.getFileConfig(), gDatos.getHbProperties());
			solrConn.open();
			
			if (solrConn.isConnected()) {
				//Declara filtros
				Map<String,String> filters = new HashMap<>();
				filters.put("q", "*:*");
				filters.put("fl", "ftpdir,id,fname");
				filters.put("fq", "connid:"+connid);
				filters.put("rows", "1");
				filters.put("key", "id");
				
				//Extrae row
				Map<String,String> mapRow = new HashMap<>();
				mapRow = solrConn.getRows(filters);
				
				//Busca columna URL
				for (Entry<String, String> entry : mapRow.entrySet()) {
					mylib.console("Encontrado id: "+entry.getKey()+ " value: "+entry.getValue());
					JSONObject joValue = new JSONObject(entry.getValue());
					ftpDir = (String) joValue.getJSONArray("ftpdir").get(0);
					fname = (String) joValue.getJSONArray("fname").get(0);
					break;
				}
				
				mylib.console("Retornando valor pathFileWav: "+ftpDir+fname);
				
				solrConn.close();
			}
			return ftpDir+fname;
		} catch (Exception e) {
			mylib.console(1,"Error getURL ("+e.getMessage()+")");
			throw new Exception(e.getMessage());
		}
	}

}
