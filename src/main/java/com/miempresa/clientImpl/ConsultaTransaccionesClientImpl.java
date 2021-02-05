package com.miempresa.clientImpl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miempresa.bean.ObtenerPeticionesResponse;
import com.miempresa.client.ConsultaTransaccionesClient;
import com.miempresa.util.UtilEAI;

//
import org.apache.log4j.Logger;
////import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
//
////import pe.com.claro.common.bean.HeaderRequestBean;
//import pe.com.claro.common.property.Constantes;
//import pe.com.claro.common.resource.exception.WSException;
//import pe.com.claro.common.util.JAXBUtilitarios;
//import pe.com.claro.postventa.peticionescliente.canonical.bean.HeaderRequestBean;
//import pe.com.claro.postventa.peticionescliente.canonical.request.ObtenerPeticionesRequest;
//import pe.com.claro.postventa.peticionescliente.canonical.response.ObtenerPeticionesResponse;
//import pe.com.claro.postventa.peticionescliente.integration.client.ConsultaTransaccionesClient;
//import pe.com.claro.postventa.peticionescliente.integration.util.UtilEAI;
//import pe.com.claro.postventa.peticionescliente.integration.util.UtilIntegration;
//import pe.com.claro.postventa.peticionescliente.shared.util.Propiedades; 
//import pe.com.claro.restclient.AbstractRestClient; 


@Service
//public class ConsultaTransaccionesClientImpl extends AbstractRestClient implements ConsultaTransaccionesClient {
public class ConsultaTransaccionesClientImpl   implements ConsultaTransaccionesClient {

	private static final Logger LOG = Logger.getLogger(ConsultaTransaccionesClientImpl.class);
	
//	private UtilIntegration util = new UtilIntegration();
	
//	@Autowired
//	Propiedades properties;

	@Override
	public ObtenerPeticionesResponse obtenerPeticiones( )
			throws Exception {
		long tiempoInicio = System.currentTimeMillis();

		ObtenerPeticionesResponse response = new ObtenerPeticionesResponse();
		
		try {


			ObjectMapper mapper = new ObjectMapper();
			Client clientRest = Client.create();
			clientRest.setReadTimeout(5000);
			WebResource webResource = clientRest.resource("https://jsonplaceholder.typicode.com/comments");
			Builder builder = webResource.type("application/json");
//			util.setHeaderBuilder(builder, header);
			
//			LOG.info("seteando el util " + header);
//			LOG.info("el builder " + builder);
//
//			LOG.info("******HEADER***: " + UtilEAI.printPrettyJSONString(header));
//			LOG.info("******REQUEST***: " + UtilEAI.printPrettyJSONString(request));
//			
//			LOG.info("antes del obtenerPeticiones response");
			ClientResponse clientResponse = builder.post(ClientResponse.class, mapper.writeValueAsString(null));// **
			String data = clientResponse.getEntity(String.class);
			
			response = mapper.readValue(data, ObtenerPeticionesResponse.class);
			
			
			LOG.info(    "obtenerPeticiones response:\n " + UtilEAI.printPrettyJSONString(response));
			 

		 } catch (Exception e) {
//			 LOG.error( e.getMessage(), e);
//             String errorMsg = e + Constantes.VACIO;
//             String nombreWs = properties.consultaTransaccionesNombreWS;
//             LOG.error(mensaje + Constantes.EXCEPCION_REST + nombreWs + Constantes.DOSPUNTOS + errorMsg, e);
             

     } finally {
//    	 LOG.info(mensaje + "Tiempo invocacion (ms): " + (System.currentTimeMillis() - tiempoInicio) + " milisegundos");
     }
		return response;
	}
	
	
 

}