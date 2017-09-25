package dfva_java.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.json.simple.JSONObject;

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
    	JSONObject authres = client.authenticate("04-0212-0119");
    	System.out.println(authres.toJSONString());
    	
    	/** Authentication show */
    	code = (String) authres.get("id_transaction").toString();
    	JSONObject authresshow = client.authenticate_show(code);
    	System.out.println(authresshow.toJSONString());
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
		JSONObject signres = client.sign(
				"04-0212-0119", 
				document, 
				"xml", 
				"Texto de resumen",
				"sha512");
		
		System.out.println(signres.toJSONString());
		
		/** Sign Show */
    	code = (String) signres.get("id_transaction").toString();
    	JSONObject signresshow = client.sign_show(code);
    	System.out.println(signresshow.toJSONString());	
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
			JSONObject validateCertres = client.validate_certificate( document);
			System.out.println(validateCertres.toJSONString());	
			
			
			/** VALIDATE DOCUMENT */
			document = new ByteArrayInputStream(
					"DOCUMENTO DE EJEMPLO".getBytes("UTF-8"));
			JSONObject validateDocres = client.validate_document(document);
			System.out.println(validateDocres.toJSONString());	
			
			
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
    	
    	/**
    	App app = new App(client);
    	
    	app.authenticate();
    	app.suscriptorConnected();
    	app.sign();
    	app.validate();
    	
		**/
    	
	
    	
    }
}
