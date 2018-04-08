package dfva_java.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
	private InstitutionClient client;
	InputStream document;
	
	public void authenticate(){
    	/** authentication  */
    	JsonObject authres = client.authenticate("04-0212-0119");
    	System.out.println(authres.toString());
    	
    	/** Authentication show */
    	code = authres.getString("id_transaction").toString();
    	JsonObject authresshow = client.authenticate_check(code);
    	System.out.println(authresshow.toString());
	}
	
	public void suscriptorConnected(){
		/** Suscriptor connected */
		Boolean sucres = client.suscriptor_connected("04-0212-0119");
    	System.out.println(sucres);
    	
	}
	
	public void sign(){
		try{
		document = new ByteArrayInputStream(
				"DOCU DE EJEMPLO".getBytes("UTF-8"));
		JsonObject signres = client.sign(
				"04-0212-0119", 
				document, 
				"xml", 
				"Texto de resumen",
				"sha512");
		
		System.out.println(signres.toString());
		
		/** Sign Show */
    	code = signres.getString("id_transaction").toString();
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
			System.out.println(validateCertres.toString());	
			
			
			/** VALIDATE DOCUMENT */
			document = new ByteArrayInputStream(
					"DOCUMENTO DE EJEMPLO".getBytes("UTF-8"));
			JsonObject validateDocres = client.validate_document(document);
			System.out.println(validateDocres.toString());	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
    public App(InstitutionClient client) {
		super();
		this.client = client;
	}

	public static void main( String[] args )
    {
    	
		SettingsManager manager = SettingsManager.getInstance();
    	Settings settings = manager.get_and_create_settings();    	
    	InstitutionClient client = new InstitutionClient(settings);   	
    	
    	
    	App app = new App(client);
    	
    	app.authenticate();
    	/**app.suscriptorConnected();
    	app.sign();
    	app.validate();
    	
		**/
    	
	
    	
    }
}
