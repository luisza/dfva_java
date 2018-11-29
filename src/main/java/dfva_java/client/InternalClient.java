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

public class InternalClient extends BaseClient {

	public InternalClient(Settings settings) {
		super(settings);
	}
	
	protected JsonObject authenticate(String identification) throws NoSuchAlgorithmException{
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
	
	protected JsonObject authenticate_check(String code) throws NoSuchAlgorithmException{
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

	protected Boolean authenticate_delete(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.autenticate_delete, code),
				send_obj.toString());
		return result.getBoolean("result");
	}
	
	protected JsonObject sign(String identification,
			InputStream document, 
			String format, //xml_cofirma, xml_contrafirma, odf, msoffice, pdf
			String resumen,
			String algothm_hash, // sha265, sha384, sha512
            String place,
            String reason
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
		obj.add("document_hash", this.getHashSumB64(bdocument));
		
        if(format.equals("pdf")){
            place = place != null ? place : "Firma sin lugar";
            reason = reason != null ? reason : "Sin motivo";
    		obj.add("place", place);
		    obj.add("reason", reason);
        }

		JsonObject send_obj = this.getDefaltParams(obj.build());	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.sign
				, send_obj.toString());
		return result;
		
	}
	
	protected JsonObject sign_check(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
				.add("institution", this.settings.institution)
				.add("notification_url", this.settings.notificationURL)
				.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.sign_check, code),
				send_obj.toString() );
		return result;
	}
	
	protected Boolean sign_delete(String code) throws NoSuchAlgorithmException{
		JsonObject obj = Json.createObjectBuilder()
				.add("institution", this.settings.institution)
				.add("notification_url", this.settings.notificationURL)
				.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.sign_delete, code),
				send_obj.toString() );
		return result.getBoolean("result");
	}

	protected Boolean suscriptor_connected(String identification) throws NoSuchAlgorithmException{
		JsonObject  obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("identification", identification)
		.add("request_datetime", this.getTime()).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.suscriptor_conected
				, send_obj.toString(), false);
		return result.getBoolean("is_connected");
	}
	
	protected JsonObject validate_certificate(InputStream document) throws IOException, NoSuchAlgorithmException{
		
		JsonObject obj = Json.createObjectBuilder()
		.add("institution", this.settings.institution)
		.add("notification_url", this.settings.notificationURL)
		.add("request_datetime", this.getTime())
		.add("document", new String(
				//Base64.encodeBase64(
						this.documentToBytes(document)
				//		)
				)  ).build();
		
		JsonObject send_obj = this.getDefaltParams(obj);	
		JsonObject result = this.post(this.settings.baseUrl+this.settings.validate_certificate
				, send_obj.toString());
		// Fixme: Poner un resultado por defecto si result es null
		return result;
	}

	protected JsonObject validate_document(InputStream document,
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
	
    protected JsonObject get_notify_data(JsonObject data){
		return this.decrypt(data);
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
}
