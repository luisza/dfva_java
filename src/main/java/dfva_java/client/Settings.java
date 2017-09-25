package dfva_java.client;

import java.nio.file.FileSystems;

public class Settings{
	public String  publicCertificate=null;
	public String  publicKey=null;
	public String  privateKey=null;
	
	public String baseUrl = "http://localhost:8000";
	public String authenticate = "/authenticate/institution/";
	public String sign = "/sign/institution/";
	public String validate_certificate = "/validate/institution_certificate/";
	public String validate_document = "/validate/institution_document/";
	public String suscriptor_conected = "/validate/institution_suscriptor_connected/";
	public String autenticate_show = "/authenticate/%s/institution_show/";
	public String sign_show = "/sign/%s/institution_show/";	
	public String institution = "";
	public String notificationURL = "";
	public String algorithm="sha512"; // sha512, sha384, sha256

	
	public Settings() {
		
	}
	
	
	
}
