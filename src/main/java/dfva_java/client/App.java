package dfva_java.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.json.JsonObject;


/**
 * Hello world!
 *
 */
public class App 
{
//	   public static void main( String[] args ){
//		   TEST t = new TEST();
//		   t.encrypt();
//		   
//	   }
	
	private String code;
	private Client client;
	InputStream document;
	
	public void authenticate(){
    	/** authentication  */
    	JsonObject authres = client.authenticate("04-0212-0119");
    	System.out.println(authres.toString());
    	
    	/** Authentication show */
    	code = (String) authres.get("id_transaction").toString();
    	JsonObject authresshow = client.authenticate_check(code);
    	System.out.println(authresshow.toString());
	}
	
	public void suscriptorConnected(){
		/** Suscriptor connected */
		Boolean sucres = client.suscriptor_connected("04-0212-0119");
    	System.out.println(sucres);
    	
	}
	
	public void sign(){
		String[] formats = new String[] {"xml_contrafirma","xml_cofirma", 
		"odf", "msoffice", "pdf"}; 
		for(int x=0; x<formats.length; x++){
			this.sign(formats[x]);

		}
		
	}
	public void sign(String format){
		try{
		document = new ByteArrayInputStream(
				"DOCU DE EJEMPLO".getBytes("UTF-8"));
		JsonObject signres = client.sign(
				"04-0212-0119", 
				document, 
				format, 
				"Texto de resumen",
				"sha512");
		System.out.println(format+"\t-->\t");
		System.out.println(signres.toString());
		
		
		/** Sign Show */
    	code = (String) signres.get("id_transaction").toString();
    	JsonObject signresshow = client.sign_check(code);
    	System.out.println(signresshow.toString());	
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void validate(){
		try {
			/** VALIDATE CERTIFICATE */
			document = new ByteArrayInputStream(
					"CERTIFICADO DE EJEMPLO".getBytes("UTF-8"));
			JsonObject validateCertres = client.validate_certificate( document);
			System.out.println("Certificado \t-->\t");
			System.out.println(validateCertres.toString());	
			
			String[] formats = new String[] {"cofirma", "contrafirma", 
			"odf", "msoffice", "pdf"}; 
			for(int x=0; x<formats.length; x++){
				this.validate_document(formats[x]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void validate_document(String format) throws NoSuchAlgorithmException, IOException{
		/** VALIDATE DOCUMENT */
		document = new ByteArrayInputStream(
				"DOCUMENTO DE EJEMPLO".getBytes("UTF-8"));
		JsonObject validateDocres = client.validate_document(document,
				format);
		System.out.println(format+"\t-->\t");
		System.out.println(validateDocres.toString());
	}
	
	public void delete_request(){
		try{
		document = new ByteArrayInputStream(
				"DOCU DE EJEMPLO".getBytes("UTF-8"));
		JsonObject signres = client.sign(
				"04-0212-0119", 
				document, 
				"odf", 
				"Texto de resumen",
				"sha512");
		
		
		/** Sign Show */
    	code = (String) signres.get("id_transaction").toString();
    	Boolean signresshow = client.sign_delete(code);
    	System.out.println("Sign delete: " +signresshow);	
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** Authentication delete */
    	JsonObject authres = client.authenticate("04-0212-0119"); 	
    	code = (String) authres.get("id_transaction").toString();
    	Boolean authresshow = client.authenticate_delete(code);
    	System.out.println("Authenticate delete: "+authresshow);
		
	}
	
	
    public App(Client client) {
		super();
		this.client = client;
	}

	public static void main( String[] args )
    {
    	
		SettingsManager manager = SettingsManager.getInstance();
    	Settings settings = manager.get_and_create_settings();    	
    	Client client = new Client(settings);   	
    	
    	
    	App app = new App(client);
    	
    	/**app.authenticate();
    	app.suscriptorConnected(); **/
    	app.sign();
    	/**app.validate();  
    	app.delete_request();
    	 **/
    	
	
    	
    }
}
