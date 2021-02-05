//package com.miempresa.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//
//import com.miempresa.bean.Greeting;
//import com.miempresa.bean.ObtenerPeticionesResponse;
//import com.miempresa.client.ConsultaTransaccionesClient;
// 
//
// 
//@Component
//public class PeticionesClienteServiceImpl implements  PeticionesClienteService{
//	
//	private static final Logger logger = Logger.getLogger(PeticionesClienteServiceImpl.class);
//	
// 
//	
//
//	@Autowired
//	private ConsultaTransaccionesClient service;
//	 
//
//	@Override
//	public Greeting listarPeticiones(long id, String content) { 
////		Greeting objListarPeticionResponse =new Greeting(id,content);
//		
//		ObtenerPeticionesResponse respuesta=null;
//		try {
//			respuesta=service.obtenerPeticiones();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return respuesta;
//	}
//	
//
//}
