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
	
    public static void main( String[] args )
    {
    	
    	
    	Settings settings = new Settings();
    	settings.publicKey = "-----BEGIN PUBLIC KEY-----\n"
+"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0P6ppvL3B02bIdppFXku\n"
+"1ctSgcCS+bSTLQJDt1/1s6kWLYbO0hbBeDAORud2WiGE1Wch8DLBrdRFKlZf1FKW\n"
+"EtTyMj1N+SQITD1DvIxdnazvnXNboLOkWMjpRN3nrirFX+xAIVZYGsk54BOYPJlm\n"
+"Sn44Ikhp5SC5LPa9y/PzjZf6ApA0QcGz3TiuRfqF8G+VxaC8JMB8u9fN9jW29yv/\n"
+"rSDGZksG9hsnQdfiKFR/0AVQAx2ZIFPaZzC1RODR6M8+swldBOk27EJpeOd5fdB8\n"
+"YdeOzWx0JIwC0hTVuTd+ZXvNOzuLuUMqoukfYfYEfwddshtSKuAVMCdDll8ZuA9G\n"
+"VwIDAQAB\n"
+"-----END PUBLIC KEY-----\n";
    	settings.publicCertificate = "-----BEGIN CERTIFICATE-----\n"
+"MIIEUDCCAjgCFQ7cWEnyf3POtaUp2ZO1OBXmtADzNzANBgkqhkiG9w0BAQsFADBX\n"
+"MQswCQYDVQQGEwJDUjElMCMGA1UECgwcRmlybWEgRGlnaXRhbCBJbnRlcm5hIEx1\n"
+"aXN6YTEhMB8GA1UEAwwYQ0EgRmlybWEgRGlnaXRhbCBJbnRlcm5hMB4XDTE3MDgw\n"
+"MTAzMzA0NFoXDTE4MDgwMTAzMzA0NFowcTELMAkGA1UEBhMCQ1IxETAPBgNVBAgM\n"
+"CFNhbiBKb3NlMRMwEQYDVQQHDApDb3N0YSBSaWNhMQ0wCwYDVQQKDAR0ZXN0MQsw\n"
+"CQYDVQQLDAJORDEeMBwGA1UEAwwVaHR0cHM6Ly9hdXRlbnRpY2EuY29tMIIBIjAN\n"
+"BgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8KWZHgNEU/DJWK2YF/DyQyomKSBX\n"
+"rjN67Xnse4mJ8lZogULSGgx0skGaUVGyOMghCcTXu6nP+PAt+VC+uMdX5+rwOCF2\n"
+"9oINEMZcm4jhArXT2igT5GQo6tnb6AHZWVkEfd7KayO0HYHbD3Y8xHBuWsPah4uw\n"
+"mIGhXGnb85NSISpcgg5oqBFMSZmrgxgNqNTQYRHkhPuHxo4mIDJmR7pPUu/yL6OW\n"
+"TqmIsxwB6YFTiwPe13+EQZWgKMql+Nz/O78z2st+nBJpRg4FS1M74nBplLbqnxzx\n"
+"8RhCg/bax7dKNAZJ5ozliNKL0+QdSxUDHgAF1XdrFrtRQy7qMAEOyaGvAQIDAQAB\n"
+"MA0GCSqGSIb3DQEBCwUAA4ICAQBnnuGgRr9nAQwYiWECn5MtK2LIiIi6AkSFSnMk\n"
+"CC0N25sjd2b8GMALyo7XcitHWgQ1L5Al5Aa7VBKws1nYqMNwyfnR+JWb+G8CNk+C\n"
+"5xMYpbfjxEyGGFPmQoFK33RYA38UdlMmSewKNxZZYC3gNIBhwqGaQ6BN1rlNIGvF\n"
+"lovtZGopcHfdeENGEOXiDtZQsDq9Aoxh4nwNW7EeaZNrAPzmBzK/1NFwsvrMZcdX\n"
+"XHyf7VYYzoAvnG3VrtBvKL2iNj45Fp2lqP2yuIHn4ucPUW7j3jYYDwCUdEDdsQiH\n"
+"ixv7YxouBu/rUTAGDt/ztNWh6bBLH4YO3mJYtuRybmdDGT7QqdDK2E27CKZqfPuz\n"
+"C2TsMMckMyB3dnuQLv5/jSfwIqYYtNio8fHNB28NT8lSsJPfK3RiCoVqUH/3RotC\n"
+"fC+B3y5xgJSysLa7fEVce4/cjelkha3imhI9XcpSiRC0pC3Wo4rgEaMfW13Fo0ef\n"
+"EPKBSEcCHybqzTs7ql1ShExKlh6XXNS/uJiue8cJYv/4pqI1sostMKryxLKMUHu4\n"
+"Ig+9QgCnA3RN9Qe3EfxCB+4ZHU15mLHsQcCnKlIOR8tJqUWsFMcXQwjtljbwXFDm\n"
+"qGrSD7CreSv/5U5UmumPonjuekgbF7YCBTWI34++PXDe/wjGKQc9+QVQ//aLQi5u\n"
+"Ud9k6w==\n"
+"-----END CERTIFICATE-----\n";
    	settings.privateKey="-----BEGIN PRIVATE KEY-----\n"
+"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDwpZkeA0RT8MlY\n"
+"rZgX8PJDKiYpIFeuM3rteex7iYnyVmiBQtIaDHSyQZpRUbI4yCEJxNe7qc/48C35\n"
+"UL64x1fn6vA4IXb2gg0QxlybiOECtdPaKBPkZCjq2dvoAdlZWQR93sprI7QdgdsP\n"
+"djzEcG5aw9qHi7CYgaFcadvzk1IhKlyCDmioEUxJmauDGA2o1NBhEeSE+4fGjiYg\n"
+"MmZHuk9S7/Ivo5ZOqYizHAHpgVOLA97Xf4RBlaAoyqX43P87vzPay36cEmlGDgVL\n"
+"UzvicGmUtuqfHPHxGEKD9trHt0o0BknmjOWI0ovT5B1LFQMeAAXVd2sWu1FDLuow\n"
+"AQ7Joa8BAgMBAAECggEAPjtU4YIOxXZbW2dpEafpvpzDyZbnldESVR9WnIvw9I6L\n"
+"8AGLu9jExORaN/VclXqLsK8K6yj683GJMhRDfh1vCjajcKCW1yDCqDfj8wTuy3ii\n"
+"FKSQoFuWiUVVrSXtQ0HV+4cF6bcN+SN/tvvosxQwNaC4fp0NSiMby35EtzS95H1D\n"
+"lVs9xUdvazDbFMv6/mAHQBQDiq1F2CCP8X10BUpWrz4Yx8mu626KFa4guAXxe1kE\n"
+"0U2OOb+pa/GqbjR0iwUaMa+odjUDOwCb6yWF5qVr2F99T+HASlLMvdJBHwqcCtBI\n"
+"zffWi4fl2UGRLcxSxJw25deO/f+YYobFl78e0BkDwQKBgQD62GXhRhguX3mhad2h\n"
+"6WGR7Rcq+Ib3GWl/K9fhOP8BYvjEyTgGbF5tTK//JJ1//bPB3r3gkFYGJ4ddez17\n"
+"VrXM6nb01bKyVYckN5d0I368ItY3oztQ7bZzAz+7GBcfwEf/ap65nFJiga3hxwaX\n"
+"/B+FKLeQ6Gzm/d6PELCKuf6+lQKBgQD1l4zPJ2hmcH7N5SkF54YCkzpK4PiDySRJ\n"
+"4GTUKpwwP0kL1qS9LqRyCglO/J4ULpjLytyYCrO91wr/BZXOdgUnhBb/gWmKl0nu\n"
+"fnlc3DKXbMCHu4xQK4PH4WcVRjJV/leVIRv2VznfOok2grRS1FAbI1EmaFS5JboW\n"
+"3rbzIANPvQKBgDOgNdeXRYLFw5Kk5yhbj+kokowVejGA2WWC8Hs578GpTOmzQASS\n"
+"aRy1m8M3FfMxk71iylFdY1cA66shohWwUtpJpxHcqVpqn9WkW1dbtsmhodTxk1Ok\n"
+"mNgy4vruilPOjwLSPriUo6ACcHb3LzQ4f4Q4TQ9VxpQDy7pGBQ+IGBXBAoGBAN86\n"
+"RinQxws9u3SIlBYi0GMs0XJYNGsM++gOh3d114vfKhURn6G5cI2bY8Sgx0rO4+Fw\n"
+"3btxFtk6Smts9zK3gY3eeUz6oyTuyKt31/Y4y99pYUK+jRYdx2V6Esl6iawySAds\n"
+"Fc9+j0LBIREkEqWrB7jzDALeGAxGJ3Q8MYyhj4D9AoGBAJGU+6IZkaEoBOjow+Fl\n"
+"GPiDV1UIWEFtqMREf/ZxYmbisyY7JU01lq+bXrUOo7xcBYr8UvGfasgC29Kb0B5u\n"
+"cd8rG8Km9Os0WrmetWEF6ATeHCHmLlOoOVPDJziXRHEgTSsN07/UfcXbMB3/R8nQ\n"
+"MnObYClt38wCurOAvlVgGzbM\n"
+"-----END PRIVATE KEY-----\n";
    	
    	settings.institution = "e994a612-9277-4965-af32-3a0928fb09b8";
    	settings.notificationURL = "http://localhost:8000/receptor_notificacion";
    	String code;
    	InstitutionClient client = new InstitutionClient(settings);
    	
    	/** authentication  */
    	JSONObject authres = client.authenticate("04-0212-0119");
    	System.out.println(authres.toJSONString());
    	
    	/** Authentication show */
    	code = (String) authres.get("id_transaction");
    	JSONObject authresshow = client.authenticate_show(code);
    	System.out.println(authresshow.toJSONString());
    	
		/** Suscriptor connected */
		Boolean sucres = client.suscriptor_connected("04-0212-0119");
    	System.out.println(sucres);
    	
    	InputStream document;
		try {
			/**  DOCUMENT  */
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
	    	code = (String) signres.get("code");
	    	JSONObject signresshow = client.sign_show("04-0212-0119", code);
	    	System.out.println(signresshow.toJSONString());
			
			
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
}
