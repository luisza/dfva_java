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
    			+"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwL3QquA22url8Kr+53RI"
    			+"hb7a2tDJZhm9m0A6yDOYpUBnqZESJKiFZro/ZIGog8eAj/4K0zen9dyf8CjUzIkj"
    			+"hHLYAt0foIgBj0dceZUQ9i34h3XsmjrD52jo28Ol2SGi/X5YN3QuFnl7SgXmwQnU"
    			+"wOxuuqztJfXh+1+fsVcn4eehdM2JKvsou1gGTvMD8yKg2NvTRVP7m9+nMPCzzKeu"
    			+"jxmfwYb8Lmzd861PwKF0PaiGMn+XgmxE6XNir9uIjo5rUgVQw5KmrCqthfHfyezA"
    			+"Kib4yYjnnPGA8i5NvmWp50+mTfaMQfDmW0wwcad0w4cgBZ7L2bCRSaWiO4+DMcFu"
    			+"IQIDAQAB\n"
    			+"-----END PUBLIC KEY-----\n";
    	settings.publicCertificate = "-----BEGIN CERTIFICATE-----\n"
    			+"MIIEVDCCAjwCFQT9BDMQv+39VRSfqgFQe5GMvwSUbTANBgkqhkiG9w0BAQsFADBX"
    			+"MQswCQYDVQQGEwJDUjElMCMGA1UECgwcRmlybWEgRGlnaXRhbCBJbnRlcm5hIEx1"
    			+"aXN6YTEhMB8GA1UEAwwYQ0EgRmlybWEgRGlnaXRhbCBJbnRlcm5hMB4XDTE3MDky"
    			+"MDE4NDgwMFoXDTE4MDkyMDE4NDgwMFowdTELMAkGA1UEBhMCQ1IxETAPBgNVBAgM"
    			+"CFNhbiBKb3NlMRMwEQYDVQQHDApDb3N0YSBSaWNhMQwwCgYDVQQKDANVQ1IxCzAJ"
    			+"BgNVBAsMAk5EMSMwIQYDVQQDDBpodHRwczovL2F1dGVudGljYS51Y3IuYS5jcjCC"
    			+"ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMnHR4iLADWqJ3o7soI+b/r3"
    			+"td8XHdl8OrZEQSergLCiSIqB4XFI8wQooCUAx9FL0/SHSh3X7RnQJ8lVK3eAidxm"
    			+"1iB20W/JU4dZEjGbE+JkSf8BHIENAA/dmpY5OidObOdTo8zRdPDDJ6cKxsYvDetL"
    			+"ldXD+eNMaJDPjai1FHrPCDfxaxTSTv5GjZeKixf/7gTFWYSIvek3DcIUBI0eXV3a"
    			+"eERXrNuh5mOn4+GmK3BkxEJVNNq3XenOx7JDzi/nMKk4sQGoSijgKYcmFmQ9oQnN"
    			+"n/1F7YqAhNv/CrQWlW2KCMCMjlXnCNyA0iNQtQrZmoh+zmmgrp0Tb73bf0IoxcsC"
    			+"AwEAATANBgkqhkiG9w0BAQsFAAOCAgEAuTme2i53hZdZdyZAuA1EJuac/u8/KJBL"
    			+"ZTd4ahYmoW651SyQsQlSYjeCVEh6tfPRVM6H8w5A7KPbQxXQTRhOt96cxlNRowgR"
    			+"JEdCQwyV7oQhaJ3NfDNtSdKyAFpNAaGcHqtowC3xDAF+2exAIsMOIskhABgvCaLY"
    			+"ZYwxBCuKn3tsLM/7Vehmo38L0eCjjdaVEskydICWbUGI7iOiOSJbpcgZEs2njZq5"
    			+"7l3WR1nmiS7DmXicV4E6Z+3sexSm6S2MB093nukg9TWe9M0RhBpeEUHmDKU38nBw"
    			+"Pt6JDE0p1eyERoVru2o1V9XgP5Jjc+rW0nPGcCue/OlutTCK7fit+QHeXOI+drPK"
    			+"JOfnkazrA+uxlXKX2U/hgvQM1BzyoDVP2VorCCRH7zlLnm1s/clwlf1Sz0SN5yF7"
    			+"glKVkgUvH7i4EtDTqXUIkKnyPk+Ks+P0EVd4obD33QGj2YMIn2ffwu+Wwhpts0eI"
    			+"PK3m0xJMM2j0MP4Ga8nCTCUTRdvFUlg0ARxTSni/69Yj6WnsV2UyU/gyN01eUKrr"
    			+"21i35V8z7p1qP6tK917f0eMClf2iwURPEl7HmT/5VX/cnnBMfEY64cTd0E20KMQa"
    			+"PfuB3wPgA+KDNXfuEkk/GfpP6ZwEG6ZZX99GmtWi8lc6/m1uf8ZLMeLQg7feNe7A"
    			+"DEbGZ7C8lx8=\n"
    			+"-----END CERTIFICATE-----\n";
    	settings.privateKey="-----BEGIN PRIVATE KEY-----\n"
    			+"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJx0eIiwA1qid6"
    			+"O7KCPm/697XfFx3ZfDq2REEnq4CwokiKgeFxSPMEKKAlAMfRS9P0h0od1+0Z0CfJ"
    			+"VSt3gIncZtYgdtFvyVOHWRIxmxPiZEn/ARyBDQAP3ZqWOTonTmznU6PM0XTwwyen"
    			+"CsbGLw3rS5XVw/njTGiQz42otRR6zwg38WsU0k7+Ro2XiosX/+4ExVmEiL3pNw3C"
    			+"FASNHl1d2nhEV6zboeZjp+PhpitwZMRCVTTat13pzseyQ84v5zCpOLEBqEoo4CmH"
    			+"JhZkPaEJzZ/9Re2KgITb/wq0FpVtigjAjI5V5wjcgNIjULUK2ZqIfs5poK6dE2+9"
    			+"239CKMXLAgMBAAECggEBAMTi2n31hALZQSQYVBqaahHs214P5GRuv6tR9nGK+0tf"
    			+"ToE4Mo6jq90G9xykIlWrM+qKiFuOciCZ5/igToWQuvSAs7eBuOLnA8Yxt3sH4uMp"
    			+"ELrujq2tzb2tckXbGU95SvJ9vnsAdqbnBi4MaeC/0Ukiu9WWIKMVz4Jdw9A2wQEC"
    			+"fdvowjmtBp6iSbVXbJOtF6MClJ6BIz7/OFUhPe+KyuvTj5/RGkkN+YM8E4T0yMv9"
    			+"Jce8wYq+3TypiqP9UI/CnMohM9GvWsr4ri5xMOA/KQ+B0tvDgOLD+B3WnpsjVREl"
    			+"cq6rtv5dN/PWnw/WEZKk0ofqY//vGUzAIdhZtDfOLSECgYEA9yuPzAMuAZSjJOe1"
    			+"0R7CLxpZjDnhJ3F1++If+V7Hs5blx4s8ey4J30eyCYBxastOMzmn56Km88BkxfEr"
    			+"C1V49Xh0SgatFBpVCYq8GL2hpaSF7XHsJNpteLRWlzFlKz+VfSpRidXwkF0GLw7K"
    			+"smHYJy4YWCgsmTFnU79Yhz/Q2zUCgYEA0PyZGQ/yinCJSlPhNXxNDpR8hV9y0WEN"
    			+"ynSUau+IV3nPq6IoG68AgJxxQRlavo9h+xGJu0tfyo/exsnKvllqxSvwdldhMjRR"
    			+"/GSASJ4zy89vTd07VzHK2WsgOgx+RLUH6/Phi9VgFU8kOB6TO0No14cOjQRRw0pc"
    			+"CK45YwCGPP8CgYBK6pEzLOOM5xhr3y9kqwYAcOQjdIeiBOnV5rv6QZgV/u62hKVY"
    			+"UKP9YSW9a4j07ZpOPHN/6fTgfLgOpwJ89PMss83LgiqDpVeRBNPuS8vrAOr3BUim"
    			+"Au6bX02/leEsMqGExobgWFAJ8agmKz2Uw8NYXMhUMBkUdbzzzVNv6w/K4QKBgHVx"
    			+"3x/khGskfyMULHYAWtWcwLW7RC1Cm5txFI9b+YUfYYOU53FXUbL1N/hbpVF+BpQv"
    			+"8PUekMCnRYz1jkiWu5f+722yAV9TY9exKx6kOXuKIYTilRyVUOgKedvldXhrO/5v"
    			+"2grZaNvQ1AvtstQVN/AXaCf7i29ydHI0Zu+qNcZFAoGAAmL/if/KqWPejRWcwwht"
    			+"ArXq1iixN6pMKKVFC7hngPRPqQx0N+Hjt6XzgtOPqgAOuolpH4ne2XnzANDtwdxY"
    			+"VcF/xdEiA/Gi4i8kiFTdlXNjXk+m6ZmpWNBxLCC7ilENMVWprbAG2u6j9IldmF5w"
    			+"wZb/X+dMop/e6rBN0yz5o+M=\n"
    			+"-----END PRIVATE KEY-----\n";
    	
    	settings.institution = "a55bb899-3e3d-4149-9381-eb75f26cebfa";
    	settings.notificationURL = "http://localhost:8000/receptor_notificacion";
    	String code;
    	InstitutionClient client = new InstitutionClient(settings);
    	
    	/** authentication  */
    	JSONObject authres = client.authenticate("04-0212-0119");
    	System.out.println(authres.toJSONString());
    	
    	/** Authentication show */
    	code = (String) authres.get("id_transaction").toString();
    	JSONObject authresshow = client.authenticate_show(code);
    	System.out.println(authresshow.toJSONString());
    	
    	
		/** Suscriptor connected 
		Boolean sucres = client.suscriptor_connected("04-0212-0119");
    	System.out.println(sucres);
    	*/
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
	    	code = (String) signres.get("id_transaction").toString();
	    	JSONObject signresshow = client.sign_show(code);
	    	System.out.println(signresshow.toJSONString());
			
			
			/** VALIDATE CERTIFICATE 
			document = new ByteArrayInputStream(
					"CERTIFICADO DE EJEMPLO".getBytes("UTF-8"));
			JSONObject validateCertres = client.validate_certificate( document);
			System.out.println(validateCertres.toJSONString());	
			*/
			
			/** VALIDATE DOCUMENT 
			document = new ByteArrayInputStream(
					"DOCUMENTO DE EJEMPLO".getBytes("UTF-8"));
			JSONObject validateDocres = client.validate_document(document);
			System.out.println(validateDocres.toJSONString());	
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
