package com.miempresa.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

 
import org.apache.log4j.Logger; 

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


 
import java.text.DateFormat;
import java.text.ParseException;
 
public class UtilEAI
{
  private final Logger logger = Logger.getLogger(getClass().getName());
  public static final String SALTOLINEA = "\n";
  private static HashMap<Class, JAXBContext> objMapaContexto = new HashMap();
  private static HashMap<Class, JAXBContext> mapContexts = new HashMap();
  
  public Object deserializeBytes(byte[] bytes)
    throws IOException, ClassNotFoundException
  {
    ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(bytesIn);
    Object obj = ois.readObject();
    ois.close();
    return obj;
  }
  
  public static String generateTraIdByDate()
  {
    return new SimpleDateFormat("yyyyMMddHHmmssms", Locale.getDefault()).format(GregorianCalendar.getInstance().getTime());
  }
  
  public String transformarAnyObjectToJsonText(String mensajeTransaccion, Object objJaxB)
  {
    String commandoRequestEnJson = null;
    JAXBContext objContexto = null;
    try
    {
      objContexto = obtenerContextoJaxBFromClass(mensajeTransaccion, objJaxB.getClass());
      
      Marshaller objMarshaller = objContexto.createMarshaller();
      StringWriter objStringWritter = new StringWriter();
      
      objMarshaller.setProperty("eclipselink.media-type", "application/json");
      objMarshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      objMarshaller.setProperty("jaxb.fragment", Boolean.valueOf(true));
      objMarshaller.setProperty("eclipselink.json.include-root", Boolean.valueOf(true));
      objMarshaller.marshal(new JAXBElement(new QName("", objJaxB.getClass().getSimpleName()), objJaxB.getClass(), objJaxB), objStringWritter);
      
      commandoRequestEnJson = objStringWritter.toString();
    }
    catch (Exception e)
    {
      this.logger.error(mensajeTransaccion + " ERROR parseando object to 'JSON': ", e);
    }
    return commandoRequestEnJson;
  }
  
  private JAXBContext obtenerContextoJaxBFromClass(String mensajeTransaccion, Class objClase)
  {
    JAXBContext objContexto = null;
    objContexto = (JAXBContext)objMapaContexto.get(objClase);
    if (objContexto == null) {
      try
      {
        this.logger.info(mensajeTransaccion + " INICIALIZANDO: [JaxContext...]");
        
        objContexto = JAXBContext.newInstance(new Class[] { objClase });
        objMapaContexto.put(objClase, objContexto);
      }
      catch (Exception e)
      {
        this.logger.error(mensajeTransaccion + " ERROR creando 'JAXBContext': ", e);
      }
    }
    return objContexto;
  }
  
   
  private static JAXBContext obtainJaxBContextFromClass(Class clas)
  {
    JAXBContext context = (JAXBContext)mapContexts.get(clas);
    if (context == null) {
      try
      {
        context = JAXBContext.newInstance(new Class[] { clas });
        mapContexts.put(clas, context);
      }
      catch (Exception localException) {}
    }
    return context;
  }
  
//  public String anyObjectToXmlText(Object anyObject)
//  {
//    String commandoRequestEnXml = null;
//    try
//    {
//      JAXBContext context = obtainJaxBContextFromClass(anyObject.getClass());
//      
//      Marshaller marshaller = context.createMarshaller();
//      
//      StringWriter xmlWriter = new StringWriter();
//      marshaller.marshal(new JAXBElement(new QName("", anyObject
//        .getClass().getSimpleName()), anyObject.getClass(), 
//        anyObject), xmlWriter);
//      
//      XmlObject xmlObj = XmlObject.Factory.parse(xmlWriter.toString());
//      
//      commandoRequestEnXml = xmlObj.toString();
//    }
//    catch (Exception e)
//    {
//      this.logger.error("Error parseando object to xml:", e);
//    }
//    return commandoRequestEnXml;
//  }
  
  public static String getStackTraceFromException(Exception exception)
  {
    StringWriter stringWriter = new StringWriter();
    exception.printStackTrace(new PrintWriter(stringWriter, true));
    return stringWriter.toString();
  }
  
  public static String obtenerIP()
  {
    String sHostName = "";
    try
    {
      InetAddress address = InetAddress.getLocalHost();
      
      sHostName = address.getHostAddress();
    }
    catch (Exception localException) {}
    return sHostName;
  }
  
  public static Date parsearFecha( String fecha, String formatoFecha ) throws Exception{
		Date date = null;
		try{
			if( fecha != null && !fecha.isEmpty() && formatoFecha != null && !formatoFecha.isEmpty() ){
				SimpleDateFormat formatter = new SimpleDateFormat( formatoFecha, Locale.getDefault() );
				date = formatter.parse( fecha );
			}
		}
		catch( Exception e ){
			throw e;
		}
		return date;
	}
  
  
	public static XMLGregorianCalendar convertDateToGregorianCalendar(Date fecha) {
		GregorianCalendar calender = new GregorianCalendar();
		calender.setTime(fecha);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calender);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return xmlCalendar;
	}
	
    public static Date convierteFechaStringToDate(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public static java.sql.Date convierteFechaStringToSQLDate(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date fechaDate = null;
        try {
            fechaDate = new java.sql.Date ( formato.parse(fecha).getTime() );
            
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public static String convierteSQLDateToString(java.sql.Date fecha)
    {    	
    	java.util.Date fechaDate = new java.util.Date(  fecha.getTime() );    	
    	return convierteFechaDateToString( fechaDate);
    }

    public static String convierteFechaDateToString(Date fecha)
    {
    	String fechaString="";

    	SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    	
    	fechaString = sdf.format(fecha);

        return fechaString;
    }


	public static String getTagValue(String xml, String tagName) {
		return xml.split("<" + tagName + ">")[1].split("</" + tagName + ">")[0];
	}
	

	public static String printPrettyJSONString(Object o) {
		try {
			return new ObjectMapper().setDateFormat(UtilEAI.getLocalFormat())
					.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writerWithDefaultPrettyPrinter()
					.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	/**
     * Remueve los saltos de linea de una cadena
     * @param cadena cadena a modificar
     * @return cadena sin saltos de linea
     */
//	public static String cleanStringJson(String cadena) {
//		return printPrettyJSONString(UtilEAI.removerSaltosLinea(StringEscapeUtils.unescapeJava(cadena), StringUtils.EMPTY,
//				true));
//	}

	/**
     * Reemplaza los saltos de linea de una cadena y tambien los espacios en blanco de ser solicitado
     * @param cadena cadena a modificar
     * @param reemplazo reemplazo para los saltos de linea
     * @param sinEspacios reemplazar espacios tambien
     * @return nueva cadena
     */
//    public static String removerSaltosLinea(String cadena, String reemplazo, boolean sinEspacios) {
//        return cadena != null ? cadena.replaceAll("\r?\n|\r" + (sinEspacios ? "|\\s" : StringUtils.EMPTY), reemplazo) : null;
//    }
  
    public static DateFormat getLocalFormat() {
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSXXX", Locale.getDefault());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", Locale.getDefault());
		dateFormat.setTimeZone(TimeZone.getDefault());
		return dateFormat;
	}

    public static java.sql.Date obtenerFechaActualSql() {
        return new java.sql.Date(new Date().getTime());
    }
    
//    public static String obtenerTimeStamp(){
//      Date fecha = new Date();
//      SimpleDateFormat formato = new SimpleDateFormat(Constantes.FORMATO_TIMESTAMP);
//      return formato.format(fecha);
//      
//    }
}