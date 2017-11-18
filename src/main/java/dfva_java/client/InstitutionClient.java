package dfva_java.client;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.commons.codec.binary.Base64;

public class InstitutionClient extends BaseClient {

	public InstitutionClient(Settings settings) {
		super(settings);
	}
	
	private JsonObject _authenticate(String identification) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("identification", identification)
		.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);
		JsonObject result = this.post(this.settings.baseUrl+this.settings.authenticate
				, send_obj.toString());
		return result;
	}
	
	private JsonObject _authenticate_show(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.autenticate_show, code),
				send_obj.toString());
		return result;
	}

	private JsonObject _authenticate_delete(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.autenticate_delete, code),
				send_obj.toString());
		return result;
	}
	
	private JsonObject _sign(String identification,
			InputStream document, 
			String format, //xml_cofirma, xml_contrafirma, odf, msoffice
			String resumen,
			String algothm_hash // sha265, sha384, sha512
			) throws IOException, NoSuchAlgorithmException{
		
		 JsonObjectBuilder obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("identification", identification)
		.add("format", format)
		.add("resumen", resumen)
		.add("request_datetime", this.getTime());
		
		byte[] bdocument = this.documentToBytes(document); 
		
		obj.add("document", new String(
				Base64.encodeBase64(bdocument)));
		
		obj.add("algorithm_hash", algothm_hash);
		obj.add("document_hash", this.getHashSum(bdocument));
		
		JsonObject send_obj = this.getDefaltParams(obj.build());	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.sign
				, send_obj.toString());
		return result;
		
	}
	
	private JsonObject _sign_show(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
				.add("institution", this.settings.institution)
				.add("notification_url", this.settings.notificationURL)
				.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.sign_show, code),
				send_obj.toString() );
		return result;
	}
	
	private JsonObject _sign_delete(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
				.add("institution", this.settings.institution)
				.add("notification_url", this.settings.notificationURL)
				.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.sign_delete, code),
				send_obj.toString() );
		return result;
	}

	private JsonObject _suscriptor_connected(String identification) throws NoSuchAlgorithmException{
		JsonObject  obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("identification", identification)
		.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.suscriptor_conected
				, send_obj.toString(), false);
		return result;
	}
	
	private JsonObject _validate_certificate(InputStream document) throws IOException, NoSuchAlgorithmException{
		
		JsonObject obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("request_datetime", this.getTime())
		.add("document", new String(
				Base64.encodeBase64(
						this.documentToBytes(document)
						))  ).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.validate_certificate
				, send_obj.toString());
		// Fixme: Poner un resultado por defecto si result es null
		return result;
	}

	private JsonObject _validate_document(InputStream document,
			String format //xml_cofirma, xml_contrafirma, odf, msoffice
			) throws IOException, NoSuchAlgorithmException{
		
		JsonObject  obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("request_datetime", this.getTime())
		.add("format", format)
		.add("document", new String(
				Base64.encodeBase64(
						this.documentToBytes(document)
						)) ).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.validate_document
				, send_obj.toString());
		return result;
	}
	
	public byte[] documentToBytes(InputStream document) throws IOException{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = document.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}
		return buffer.toByteArray();
	}	

	public JsonObject authenticate(String identification){
		JsonObject obj = null;
		boolean inerror = false;
		try {
			obj=this._authenticate(identification);
		}catch (Exception e) {
			inerror=true;
			logger.log(Level.SEVERE, "Error con algoritmo", e);
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= Json.createObjectBuilder()
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
		}
		
		return obj;
	}
	
	public JsonObject authenticate_show(String id_transaction){
		JsonObject obj = null;
		boolean inerror=false;
		try {
			obj=this._authenticate_show(id_transaction);
		} catch (NoSuchAlgorithmException e) {
			inerror=true;
			logger.log(Level.SEVERE, "Error con algoritmo", e);
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= Json.createObjectBuilder()
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
		}
		return obj;
	}

	public Boolean authenticate_delete(String id_transaction){
		JsonObject obj = null;
		try {
			obj=this._authenticate_delete(id_transaction);
			return obj.getBoolean("result");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public JsonObject sign(String identification,
					InputStream document, 
					String format, //xml, odf, msoffice
					String resumen
					){
		return this.sign(identification, document, format, resumen, "sha512");
	}
	
	public JsonObject sign(String identification,
			InputStream document, 
			String format, //xml_cofirma, xml_contrafirma, odf, msoffice
			String resumen,
			String algothm_hash // sha265, sha384, sha512
			) {
		
		JsonObject obj = null;
		boolean inerror=false;
		try {
			obj= this._sign(identification, document, format, resumen, algothm_hash);
		} catch (NoSuchAlgorithmException e) {
			inerror=true;
			logger.log(Level.SEVERE, "Error con algoritmo", e);
		} catch (IOException e) {
			inerror=true;
			logger.log(Level.SEVERE, "Error de lectura", e);
		} catch(Exception e){
			inerror=true;
			logger.log(Level.SEVERE, "Excepcion no controlada", e);
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= Json.createObjectBuilder()
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
		}
		return obj;		
	}
	
	public JsonObject sign_show(String id_transaction){
		JsonObject obj = null;
		boolean inerror=false;
		try {
			obj=this._sign_show(id_transaction);
		} catch (NoSuchAlgorithmException e) {
			inerror=true;
			logger.log(Level.SEVERE, "Error con algoritmo", e);
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= Json.createObjectBuilder()
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
		}
		return obj;
	}
	
	public Boolean sign_delete(String id_transaction){
		JsonObject obj = null;
		try {
			obj=this._sign_delete(id_transaction);
			return obj.getBoolean("result");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public JsonObject validate_certificate(InputStream document){
		JsonObject obj=null;
		boolean inerror=false;
		try {
			obj = this._validate_certificate(document);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inerror=true;
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= Json.createObjectBuilder()
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
		}
		return obj;
	}
	
	public JsonObject validate_document(InputStream document,
			String format //xml_cofirma, xml_contrafirma, odf, msoffice
			){
		JsonObject obj=null;
		boolean inerror=false;
		try {
			obj = this._validate_document(document, format);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inerror=true;
		}
		if(obj==null){
			inerror=true;
		}
		
		if(inerror){
			obj= Json.createObjectBuilder()
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
		}
		return obj;
	}

	public Boolean suscriptor_connected(String identification){
		JsonObject obj = null;
		try {
			obj=this._suscriptor_connected(identification);
			return obj.getBoolean("is_connected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
