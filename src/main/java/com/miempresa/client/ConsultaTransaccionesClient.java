package com.miempresa.client;

import com.miempresa.bean.ObtenerPeticionesResponse;

//
import javax.ejb.Local;
//
//import pe.com.claro.postventa.peticionescliente.canonical.bean.HeaderRequestBean;
////import pe.com.claro.common.bean.HeaderRequestBean;
//import pe.com.claro.postventa.peticionescliente.canonical.request.ObtenerPeticionesRequest;
//import pe.com.claro.postventa.peticionescliente.canonical.response.ObtenerPeticionesResponse; 

@Local
public interface ConsultaTransaccionesClient {

	ObtenerPeticionesResponse obtenerPeticiones(
//			String mensaje 
//			,HeaderRequestBean headerRequest,
//			ObtenerPeticionesRequest  request
			) throws Exception;
}


