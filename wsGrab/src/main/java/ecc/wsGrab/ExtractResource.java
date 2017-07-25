package ecc.wsGrab;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import common.rutinas.Rutinas;
import ecc.services.ExtractService;

@Path("getWav")
public class ExtractResource {
	Rutinas mylib = new Rutinas();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getNull() {
		return Response.status(429).entity("Debe ingresar valor de un connid").build();
	}
	
	
	@GET
	@Path("/{connid}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getWav(@PathParam("connid") String connid) {
		ExtractService srv = new ExtractService();
		int errCode=0;
		String errMesg="";
		String fileDecodedName="";
		try {
			mylib.console("Iniciando extraccion grabacion: "+connid);
			String myURL = srv.getURL(connid);
			
			if (!mylib.isNullOrEmpty(myURL)) {
				fileDecodedName = srv.getWav(myURL);
				if (!mylib.isNullOrEmpty(fileDecodedName)) {
					mylib.console("Decoded File Generated: "+fileDecodedName);
				} else {
					mylib.console(1, "No se pudo generar decoded file");
					errCode = 428;
					errMesg = "No se pudo generar decoded file";
				}
			} else {
				errCode = 427;
				errMesg = "No se encontro Wav asociado al connid "+connid;
			}
			
			//Finalizando la Extraccion
			if (errCode==0) {
				mylib.console("Finalizando extracción grabacion.");
				
//				InputStream is = new FileInputStream(fileDecodedName);
//		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		        int len;
//		        byte[] buffer = new byte[4096];
//		        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
//		            baos.write(buffer, 0, len);
//		        }
//		        System.out.println("Server size: " + baos.size());
//		        return Response.ok(baos).build();
				
				
				File file = new File(fileDecodedName);
				String[] tokens = fileDecodedName.split("/");
				String onlyFileName = tokens[tokens.length-1];
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename="+onlyFileName);
				//return Response.ok().entity("getDecodedWav para connid: "+connid+ " file: "+fileDecodedName).build();
				return response.build();
			} else {
				mylib.console("errCode: "+errCode);
				mylib.console("errMesg: "+errMesg);
				mylib.console("No se encontro Wav asociado al connid "+connid);
				return Response.status(errCode).entity(errMesg).build();
			}
		} catch (Exception e) {
			return Response.status(500).entity("Error ("+e.getMessage()+")").build();
		}
	}

}
