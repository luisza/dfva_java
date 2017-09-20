package dfva_java.client;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

public class InstitutionClient extends BaseClient {

	
	public InstitutionClient(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
	private JSONObject _authenticate(String identification) throws NoSuchAlgorithmException{
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("identification", identification);
		obj.put("request_datetime", this.getTime());
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+this.settings.authenticate
				, send_obj.toJSONString());
		return result;
	}
	
	private JSONObject _authenticate_show(String code) throws NoSuchAlgorithmException{
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("request_datetime", this.getTime());
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.autenticate_show, code),
				send_obj.toJSONString());
		return result;
	}
	
	private JSONObject _sign_show(String code) throws NoSuchAlgorithmException{
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("request_datetime", this.getTime());
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+
				String.format(this.settings.sign_show, code),
				send_obj.toJSONString());
		return result;
	}
	
	
	public JSONObject authenticate(String identification){
		JSONObject obj = new JSONObject();
		try {
			obj=this._authenticate(identification);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public JSONObject authenticate_show(String id_transaction){
		JSONObject obj = new JSONObject();
		try {
			obj=this._authenticate_show(id_transaction);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	public JSONObject sign_show(String id_transaction){
		JSONObject obj = new JSONObject();
		try {
			obj=this._sign_show(id_transaction);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
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
	
	public JSONObject sign(String identification,
			InputStream document, 
			String format, //xml, odf, msoffice
			String resumen
			){
		try {
			return this.sign(identification, document, format, resumen, "sha512");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject sign(String identification,
			InputStream document, 
			String format, //xml, odf, msoffice
			String resumen,
			String algothm_hash // sha265, sha384, sha512
			) throws IOException, NoSuchAlgorithmException{
		
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("identification", identification);
		obj.put("format", format);
		obj.put("resumen", resumen);
		obj.put("request_datetime", this.getTime());
		
		byte[] bdocument = this.documentToBytes(document); 
		
		obj.put("document", new String(
				Base64.encodeBase64(bdocument)));
		
		obj.put("algorithm_hash", algothm_hash);
		obj.put("document_hash", this.getHashSum(bdocument));
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+this.settings.sign
				, send_obj.toJSONString());
		return result;
		
	}
	
	
	public JSONObject validate_certificate(InputStream document) throws IOException, NoSuchAlgorithmException{
		
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("request_datetime", this.getTime());
		obj.put("document", new String(
				Base64.encodeBase64(
						this.documentToBytes(document)
						)) );
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+this.settings.validate_certificate
				, send_obj.toJSONString());
		return result;
	}

	public JSONObject validate_document(InputStream document) throws IOException, NoSuchAlgorithmException{
		
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("request_datetime", this.getTime());
		obj.put("document", new String(
				Base64.encodeBase64(
						this.documentToBytes(document)
						)) );
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+this.settings.validate_document
				, send_obj.toJSONString());
		return result;
	}
	
	public Boolean suscriptor_connected(String identification){
		JSONObject obj = new JSONObject();
		try {
			obj=this._suscriptor_connected(identification);
			return (Boolean) obj.get("is_connected");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private JSONObject _suscriptor_connected(String identification) throws NoSuchAlgorithmException{
		JSONObject obj = new JSONObject();
		JSONObject send_obj;
		obj.put("institution", this.settings.institution);
		obj.put("notification_url", this.settings.notificationURL);
		obj.put("identification", identification);
		obj.put("request_datetime", this.getTime());
		
		send_obj = this.getDefaltParams(obj);	
		JSONObject result = this.post(this.settings.baseUrl+this.settings.suscriptor_conected
				, send_obj.toJSONString(), false);
		return result;
	}
	
}
