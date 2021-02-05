package com.miempresa.rest;
 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

 
import javax.json.JsonValue;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miempresa.bean.Greeting;
import com.miempresa.bean.Objecto;
import com.miempresa.bean.Objecto2;
import com.miempresa.bean.ObtenerPeticionesResponse;
import com.miempresa.clientImpl.ConsultaTransaccionesClientImpl; 
import com.miempresa.util.UtilEAI;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private static final Logger LOG = Logger.getLogger(ConsultaTransaccionesClientImpl.class);
	

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Greeting respuesta=null;
		ObtenerPeticionesResponse response = new ObtenerPeticionesResponse();
		 
		
		try {

			
			LOG.info(    "hans ");

			 // Esto es lo que vamos a devolver
			  StringBuilder resultado = new StringBuilder();
			  // Crear un objeto de tipo URL
//			  URL url = new URL("http://jsonplaceholder.typicode.com/comments/1");
			  URL url = new URL("http://jsonplaceholder.typicode.com/comments");

			  // Abrir la conexión e indicar que será de tipo GET
			  HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			  conexion.setRequestMethod("GET");
			  // Búferes para leer
			  BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			  String linea;
			  // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
			  while ((linea = rd.readLine()) != null) {
			    resultado.append(linea);
			  }
			  // Cerrar el BufferedReader
			  rd.close();
			  
			
			  
//			  LOG.info(    "resultado " + resultado.toString());
////			  Object objeto=resultado.toString();
// 
//			  com.google.gson.JsonParser fichero = null;
//			    Object objeto =  fichero.parse(resultado.toString());
//			  
//			    JsonArray detalle = (JsonArray) objeto;
//			    Iterator<JsonElement> iterator = detalle.iterator();
//
//			  while (iterator.hasNext()) {
//			            JsonObject jsonObject = (JsonObject) iterator.next();
//
//			            jsonObject.keySet().forEach((key) -> {
//			                System.out.println(key + ":" + jsonObject.get(key));
//			                   });
//			}
			  
			  String resultadoss="{\"data\":"+resultado.toString()+"}";
			  LOG.info(    "resultadoss " + resultadoss);
			  
//			   JSONParser parser = new JSONParser(resultado.toString());
//		        JSONObject pagesObject = (JSONObject) parser.parse();
//		        System.out.println(pagesObject.get("id"));
//		        System.out.println(pagesObject.get("pages").getClass().getName());
//		        JSONArray jsonArray= (JSONArray) pagesObject.get("pages");
//
//		        for(int i=0; i<jsonArray.size(); i++){
//		            System.out.println(jsonArray.get(i));
//		        }
//		        
		        
			  
////			   JSONArray detalle = (JSONArray) objeto;
//			   
//			   
//			   JsonArray detalle = (JsonArray) objeto;
//			    Iterator<JsonElement> iterator = detalle.iterator();
//
//			  while (iterator.hasNext()) {
//			            JsonObject jsonObject = (JsonObject) iterator.next();
//
//			            jsonObject.keySet().forEach((key) -> {
//			                System.out.println(key + ":" + jsonObject.get(key));
//			                   });
//			}
//			  
//			  
//			   json.
//
//			   //Recorro el array para parsear cada cliente
//			   for (JSONObject jclient : json){ 
//			   //Instancia tu modelo de Cliente para que contenga los datos que vayas a necesitar
//			      Cliente cliente = new Cliente();
//
//			      //Asi consigo finalmente los campos del json
//			      cliente.nombre = jclient.getString("nombre");
//			      cliente.tel1 = jclient.getString("tel1");
//			      cliente.tel2 = jclient.getDouble("tel2");
//			      //Continuar con los demas campos...
//			      clientes.add(cliente)
//			   }
			   
			   
			  ObjectMapper mapper = new ObjectMapper();
			  response = mapper.readValue( resultadoss , ObtenerPeticionesResponse.class); 
			  
			  respuesta=new  Greeting();
			  List<Objecto2> lista=new ArrayList<Objecto2>();
			  
			  Objecto2 ofinal=null;
			  for(Objecto o:response.getData()) {
				  ofinal=new Objecto2();
			      ofinal.setPostId(o.getPostId());
				  ofinal.setId(o.getId());
				  ofinal.setEmail(o.getEmail());				  
				  lista.add(ofinal);
			  }
			  
			  LOG.info(    "clave java a json LISTA response:\n " + UtilEAI.printPrettyJSONString(response));
 
			  respuesta.setData(lista);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuesta;
		
	}
}