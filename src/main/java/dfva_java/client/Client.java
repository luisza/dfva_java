package dfva_java.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.logging.Level;

public class Client extends InternalClient {
	public Client(Settings settings) {
		super(settings);
	}

    private JsonObject get_default_validate_error(){
        JsonObject obj= Json.createObjectBuilder()
			.add("code", "N/D")
			.add("status", 2)
			.add("identification", "N/D")
			.add("id_transaction", 0)
			.add("request_datetime", "")
			.add("sign_document", "")
			.add("expiration_datetime", "")
			.add("received_notification", true)
			.add("duration", 0)
			.add("status_text", "Problema de comunicación interna").build();
        return obj;
    }

    private JsonObject get_default_sign_error(){
        JsonObject obj= Json.createObjectBuilder()
			.add("code", "N/D")
			.add("status", 2)
			.add("identification", "N/D")
			.add("id_transaction", 0)
			.add("request_datetime", "")
			.add("sign_document", "")
			.add("expiration_datetime", "")
			.add("received_notification", true)
			.add("duration", 0)
			.add("status_text", "Problema de comunicación interna").build();

        return obj;

    }

	public JsonObject authenticate(String identification){
		JsonObject obj = null;
		try {
			obj=super.authenticate(identification);
		}catch (Exception e) {
			obj=null;
			logger.log(Level.SEVERE, "Error con algoritmo", e);
		}
		if(obj == null){
			obj=this.get_default_sign_error();
		}
	
		return obj;
	}
	
	public JsonObject authenticate_check(String id_transaction){
		JsonObject obj = null;
		try {
			obj=super.authenticate_check(id_transaction);
		} catch (Exception e) {
			obj=null;
			logger.log(Level.SEVERE, "Authenticate_check: Error con algoritmo", e);
		}
		if(obj == null){
			obj=this.get_default_sign_error();
		}
		return obj;
	}

	public Boolean authenticate_delete(String id_transaction){
		Boolean dev=false;
		try {
			dev= super.authenticate_delete(id_transaction);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Authenticate_delete: Error con algoritmo", e);
		}
		return dev;
	}
	
	public JsonObject sign(String identification,
					InputStream document, 
					String format, //xml_cofirma, xml_contrafirma, odf, msoffice, pdf
					String resumen
					){
		return this.sign(identification, document, format, resumen, "sha512");
	}
	
	public JsonObject sign(String identification,
			InputStream document, 
			String format, //xml_cofirma, xml_contrafirma, odf, msoffice, pdf
			String resumen,
			String algothm_hash // sha265, sha384, sha512
			) {
		
		JsonObject obj = null;
		boolean inerror=false;
		try {
			obj= super.sign(identification, document, format, 
                            resumen, algothm_hash);
		} catch (NoSuchAlgorithmException e) {
			inerror=true;
			logger.log(Level.SEVERE, "sign: Error con algoritmo", e);
		} catch (IOException e) {
			inerror=true;
			logger.log(Level.SEVERE, "sign: Error de lectura", e);
		} catch(Exception e){
			inerror=true;
			logger.log(Level.SEVERE, "sign: Excepcion no controlada", e);
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= this.get_default_sign_error();
		}
		return obj;		
	}
	
	public JsonObject sign_check(String id_transaction){
		JsonObject obj = null;
		boolean inerror=false;
		try {
			obj=super.sign_check(id_transaction);
		} catch (NoSuchAlgorithmException e) {
			inerror=true;
			logger.log(Level.SEVERE, "sign_check: Error con algoritmo", e);
		}
        catch (Exception e) {
			inerror=true;
			logger.log(Level.SEVERE, "sign_check: Error no especificado", e);
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= this.get_default_sign_error();
		}
		return obj;
	}
	
	public Boolean sign_delete(String id_transaction){
		Boolean dev = false;
		try {
			dev= super.sign_delete(id_transaction);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "sign_delete: Error no especificado", e);
		}
		return dev;
	}
	
    public JsonObject validate(InputStream document, String type, String format){
        JsonObject obj=null;
        if(type.equals("certificate")){
            obj= this.validate_certificate(document);
        }else{
            obj = this.validate_document(document, format);
        }
        return obj;
    }
	public JsonObject validate_certificate(InputStream document){
		JsonObject obj=null;
		boolean inerror=false;
		try {
			obj = super.validate_certificate(document);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "validate_certificate: Error no especificado", e);
			inerror=true;
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj=this.get_default_validate_error();
		}
		return obj;
	}
	
	public JsonObject validate_document(InputStream document,
			String format //xml_cofirma, xml_contrafirma, odf, msoffice
			){
		JsonObject obj=null;
		boolean inerror=false;
		try {
			obj = super.validate_document(document, format);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "validate_document: Error no especificado", e);
			inerror=true;
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= this.get_default_validate_error();
		}
		return obj;
	}

	public Boolean suscriptor_connected(String identification){
		Boolean dev=false;
		try {
			dev= super.suscriptor_connected(identification) ;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "suscriptor_connected: Error no especificado", e);
		}
		return dev;
	}

    public JsonObject get_notify_data(JsonObject data){
		JsonObject obj = null;
		try {
			obj=super.get_notify_data(data);
			return obj;
		} catch (Exception e) {
			obj=null;
			logger.log(Level.SEVERE, "suscriptor_connected: Error no especificado", e);
		}
		if(obj==null){
			obj=this.get_default_sign_error();
		}
		return obj;
    }
}