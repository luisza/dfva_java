package dfva_java.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.json.JsonObject;

import org.apache.commons.codec.binary.Base64;


public class BaseUtils {
	public static Integer TIMEWAIT = 6000;
	public static Integer FORMAT_WAIT = 500;
	public static Integer WAIT_AUTH = 500;
	private static BaseUtils instance = new BaseUtils();
	public List<String> DOCUMENT_FORMATS = Arrays.asList(
			"xml_cofirma", "xml_contrafirma",
            "odf", "msoffice", "pdf");
	public Dictionary<String, Integer> AUTHENTICATION_RESPONSE_TABLE = 
			new Hashtable<String, Integer> ();
	public Dictionary<String, Integer> AUTHENTICATION_CHECK_RESPONSE_TABLE = 
			new Hashtable<String, Integer> ();
	public Dictionary<String, Integer> DOCUMENT_RESPONSE_TABLE = 
			new Hashtable<String, Integer> ();
	public Dictionary<String, Integer> DOCUMENT_CHECK_RESPONSE_TABLE= 
			new Hashtable<String, Integer> ();

    public Dictionary<String, Integer>  DOCUMENT_RESPONSE_WRONG_TABLE=
            new Hashtable<String, Integer> ();
	
	public Dictionary<String, JsonObject> AUTH_TRANSACTIONS= 
			new Hashtable<String, JsonObject> ();	

	public Dictionary<String, Dictionary<String, JsonObject>> DOCUMENT_TRANSACTIONS= 
			new Hashtable<String, Dictionary<String, JsonObject>> ();

	public Dictionary<String, Dictionary<String, JsonObject>> DOCUMENT_TRANSACTIONS_WRONG=
			new Hashtable<String, Dictionary<String, JsonObject>> ();
	
	
	public Dictionary<String, List<String>> VALIDATE_CERTIFICATE_RESPONSE_TABLE= 
			new Hashtable<String, List<String>> ();
	
	public Dictionary<String, String> DOCUMENT_VALIDATE_NAME_TABLE= 
			new Hashtable<String, String> ();

	public Dictionary<String, Boolean> DOCUMENT_VALIDATE_SUCCESS_TABLE= 
			new Hashtable<String, Boolean> ();

	public Dictionary<String, List<Integer>> DOCUMENT_VALIDATE_CODES_TABLE= 
			new Hashtable<String, List<Integer>> ();
	
	public static String DEFAULT_PATH ="dfva_testdocument/files/";
	public Client client;
	
	private BaseUtils(){	
		AUTHENTICATION_RESPONSE_TABLE.put("500000000000", 1); 
		AUTHENTICATION_RESPONSE_TABLE.put("01-1919-2222", 4); 
		AUTHENTICATION_RESPONSE_TABLE.put("01-1919-2020", 5); 
		AUTHENTICATION_RESPONSE_TABLE.put("01-1919-2121", 9);  
		AUTHENTICATION_RESPONSE_TABLE.put("9-0000-0000-000", 10); 
        // con notificacion
		AUTHENTICATION_RESPONSE_TABLE.put("100000000000", 1);
		AUTHENTICATION_RESPONSE_TABLE.put("01-1010-2020", 2);
		AUTHENTICATION_RESPONSE_TABLE.put("01-2020-3030", 3);
		AUTHENTICATION_RESPONSE_TABLE.put("01-4040-5050", 4);
		AUTHENTICATION_RESPONSE_TABLE.put("01-6060-7070", 9);
		AUTHENTICATION_RESPONSE_TABLE.put("01-8080-9090", 10);
		AUTHENTICATION_RESPONSE_TABLE.put("01-1100-2211", 11);
		AUTHENTICATION_RESPONSE_TABLE.put("01-3344-5566", 13);
		AUTHENTICATION_RESPONSE_TABLE.put("01-7788-9900", 14);
		
		// AUTHENTICATION_CHECK_RESPONSE_TABLE  
		
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("500000000000", 0);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-1919-2222", 0);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-1919-2020", 0);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-1919-2121", 0);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("9-0000-0000-000", 0);
	    // Con notificacion
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("100000000000", 1);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-1010-2020", 2);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-2020-3030", 3);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-4040-5050", 4);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-6060-7070", 9);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-8080-9090", 10);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-1100-2211", 11);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-3344-5566", 13);
		AUTHENTICATION_CHECK_RESPONSE_TABLE.put("01-7788-9900", 14);
		
		
		
		// DOCUMENT_RESPONSE_TABLE 
		DOCUMENT_RESPONSE_TABLE.put("500000000000", 1);
		DOCUMENT_RESPONSE_TABLE.put("01-1919-2222", 4);
		DOCUMENT_RESPONSE_TABLE.put("01-1919-2020", 5);
		DOCUMENT_RESPONSE_TABLE.put("01-1919-2121", 9);
		DOCUMENT_RESPONSE_TABLE.put("9-0000-0000-000", 10);
	    // Con notificación
		DOCUMENT_RESPONSE_TABLE.put("100000000000", 1);
		DOCUMENT_RESPONSE_TABLE.put("01-1010-2020", 2);
		DOCUMENT_RESPONSE_TABLE.put("01-2020-3030", 3);
		DOCUMENT_RESPONSE_TABLE.put("01-4040-5050", 4);
		DOCUMENT_RESPONSE_TABLE.put("01-6060-7070", 9);
		DOCUMENT_RESPONSE_TABLE.put("01-8080-9090", 10);
		DOCUMENT_RESPONSE_TABLE.put("01-1100-2211", 11);
		DOCUMENT_RESPONSE_TABLE.put("01-3344-5566", 13);
		DOCUMENT_RESPONSE_TABLE.put("01-7788-9900", 14);
		
		
		// DOCUMENT_CHECK_RESPONSE_TABLE

		DOCUMENT_CHECK_RESPONSE_TABLE.put("500000000000", 0);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-1919-2222", 0);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-1919-2020", 0);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-1919-2121", 0);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("9-0000-0000-000", 0);
	    //Con notificación
		DOCUMENT_CHECK_RESPONSE_TABLE.put("100000000000", 1);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-1010-2020", 2);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-2020-3030", 3);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-4040-5050", 4);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-6060-7070", 9);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-8080-9090", 10);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-1100-2211", 11);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-3344-5566", 13);
		DOCUMENT_CHECK_RESPONSE_TABLE.put("01-7788-9900", 14);

        // DOCUMENT_RESPONSE_WRONG TABLE

        DOCUMENT_RESPONSE_WRONG_TABLE.put("03-0110-2020", 15);

		VALIDATE_CERTIFICATE_RESPONSE_TABLE.put("01-0001-0002",	Arrays.asList("ANA ROJAS PRUEBA", "0", "true"));
		VALIDATE_CERTIFICATE_RESPONSE_TABLE.put("199887755443",	Arrays.asList("NARCISO CASCANTE PRUEBA", "0", "true"));
		VALIDATE_CERTIFICATE_RESPONSE_TABLE.put("01-0001-0002exp",	Arrays.asList("ANA ROJAS PRUEBA", "3", "false"));
		VALIDATE_CERTIFICATE_RESPONSE_TABLE.put("199887755443exp",	Arrays.asList("NARCISO CASCANTE PRUEBA", "3", "false"));
		VALIDATE_CERTIFICATE_RESPONSE_TABLE.put("01-0001-0002rev",	Arrays.asList("ANA ROJAS PRUEBA", "4", "false"));
		VALIDATE_CERTIFICATE_RESPONSE_TABLE.put("199887755443rev",	Arrays.asList("NARCISO CASCANTE PRUEBA", "4", "false"));

		DOCUMENT_VALIDATE_NAME_TABLE.put(
		"cofirma", "527789139593,José María Montealegre Fernández\n"
				+ "145764968887,José Figueres Ferrer");
		DOCUMENT_VALIDATE_NAME_TABLE.put(
		 "contrafirma","09-2171-6656,Ascensión Esquivel Ibarra\n"
				 +"08-9841-4375,Francisco Orlich Bolmarcich");
		DOCUMENT_VALIDATE_NAME_TABLE.put( 
		"msoffice","06-5980-2076,Federico Tinoco Granados\n"
						 +"01-4121-6048,Vicente Herrera Zeledón");
		DOCUMENT_VALIDATE_NAME_TABLE.put(
		"odf","04-2191-3685,Luis Monge Álvarez\n"
				 +"06-2119-5314,José María Alfaro Zamora");
		DOCUMENT_VALIDATE_NAME_TABLE.put(
		"pdf","01-2645-3949,Juan Mora Fernández\n"+
			   "05-9062-3516,Rafael Calderón Fournier");
		
		DOCUMENT_VALIDATE_SUCCESS_TABLE.put("cofirma", true);
		DOCUMENT_VALIDATE_SUCCESS_TABLE.put("contrafirma", true);
		DOCUMENT_VALIDATE_SUCCESS_TABLE.put("msoffice", true);
		DOCUMENT_VALIDATE_SUCCESS_TABLE.put("odf", true);
		DOCUMENT_VALIDATE_SUCCESS_TABLE.put("pdf", true);
		

		DOCUMENT_VALIDATE_CODES_TABLE.put("cofirma", 
				Arrays.asList(23, 45, 21, 48, 12, 16));
		DOCUMENT_VALIDATE_CODES_TABLE.put("contrafirma",
				Arrays.asList(13, 24, 11, 80));
		DOCUMENT_VALIDATE_CODES_TABLE.put("msoffice", 
				Arrays.asList(32, 47, 69, 36));
		DOCUMENT_VALIDATE_CODES_TABLE.put("odf", 
				Arrays.asList(67, 51, 52, 53, 55));
		DOCUMENT_VALIDATE_CODES_TABLE.put("pdf",
				Arrays.asList(1));
		
		SettingsManager manager = SettingsManager.getInstance();
		manager.load_config();
		client = new Client(manager.getSettings());
	}

    public static BaseUtils getInstance() {
        // retorna la unica instancia
        return instance;

    }
    
    public String parse_base64(byte[] data){
    	return new String(Base64.encodeBase64(data));
    }
    
    
    public String parse_certificate(byte[] data){
    	String certificate = new String(data);
    	String open_crt = "-----BEGIN CERTIFICATE-----";
    	String close_crt = "-----END CERTIFICATE-----";
		String result;
		result = certificate;
    	if (certificate.contains(open_crt) ||  certificate.contains(close_crt)){
			result = certificate.replaceAll("\n", "");
			result = result.replaceFirst(
					"^.*"+open_crt, "").replaceFirst(
					close_crt, ""
			).replaceAll("\n", "");
		}else {
			result = new String(Base64.encodeBase64(data));
		}
        return result;
    }   
    
    private byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }
    
    public String read_file_from_name(String name){
    	Path path = FileSystems.getDefault().getPath(
    		"../"+name).toAbsolutePath();
    	return path.toString();
    }
    
    private String build_on_format(String format, String name){
    	String dev="";
    	if(format.equals("xml_cofirma") || format.equals("xml_contrafirma")  ){
    		dev=DEFAULT_PATH+"test.xml";
    	}else if(format.equals("odf")){
    		dev=DEFAULT_PATH+"test.odt";
    	}else if(format.equals("msoffice")){
    		dev=DEFAULT_PATH+"test.docx";
    	}else if(format.equals("pdf")){
    		dev=DEFAULT_PATH+"test.pdf";
    	}else{
    		dev=DEFAULT_PATH+name+format;
    	}
    	return dev;
    }
    
    public String read_files(String format, String post_read_fn, String name) {
		String data = "";
		String filePath = this.read_file_from_name(
				this.build_on_format(format, name));
		byte[] file = this.readBytesFromFile(filePath);
		if (post_read_fn.equals("base64")) {
			data = this.parse_base64(file);
		}else if (post_read_fn.equals("certificate")) {
			data = this.parse_certificate(file);
		}else {
			data = new String(file);
		}
		return data;
	}
    public InputStream read_files_inputstream(String format, String post_read_fn, String name){
    	String initialString = this.read_files(format, post_read_fn, name);
    	InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
    	return targetStream;
    }
    
    public String read_files(String format, String post_read_fn){
    	return this.read_files(format, post_read_fn, "test.");
    }
    public String read_files(String format){
    	return this.read_files(format, "base64", "test.");
    }
    
    public InputStream read_files_inputstream(String format){    
    	String initialString = this.read_files(format);
    	InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
    	return targetStream;
    }
}

