package dfva_java.client;

import static org.junit.Assert.assertEquals;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import org.junit.Test;


public class TestValidateDocuments {
	private BaseUtils utils = BaseUtils.getInstance();
	
	
	private List<String> get_list_names(String namestr){
		ArrayList<String> dev = new ArrayList<String>();
		for(String lines: namestr.split("\n")){
			dev.add(lines.split(",")[0]);
		}
		Collections.sort(dev); 
		return dev;
	}
	
	private List<String> prepare_names(JsonArray nameslist){
		ArrayList<String> dev = new ArrayList<String>();
		JsonObject obj;
		for (int i = 0; i < nameslist.size(); i++) {
			obj = nameslist.getJsonObject(i);
			if(obj.containsKey("identification_number")){
				dev.add( obj.getString("identification_number"));
			}
		} 
		
		Collections.sort(dev); 
		return dev;
		
	}
	private List<Integer> extract_codes(JsonArray codes){
		List<Integer> dev = new ArrayList<Integer>();
		JsonObject obj;
		for (int i = 0; i < codes.size(); i++) {
			obj = codes.getJsonObject(i);
			if(obj.containsKey("code")){
				dev.add(Integer.parseInt(obj.getString("code")));
			}
		}
		
		Collections.sort(dev); 
		return dev;
	}
	
	private List<Integer> get_experated_errors(String format){
		List<Integer> dev = utils.DOCUMENT_VALIDATE_CODES_TABLE.get(format);
		Collections.sort(dev); 
		return dev;	
	}
	
	private void do_check(String format, String filename){
		InputStream document = utils.read_files_inputstream(filename);
		JsonObject result = utils.client.validate(document, "document",format);

		List<Integer> extracted_errors = this.extract_codes(result.getJsonArray("errors"));
		List<String> extracted_signers = this.prepare_names(result.getJsonArray("signers"));

		List<Integer> expected_errors = this.get_experated_errors(format);
		List<String> expected_signers = this.get_list_names(utils.DOCUMENT_VALIDATE_NAME_TABLE.get(format));

		assertEquals(extracted_errors, expected_errors);
		assertEquals(extracted_signers, expected_signers);
		assertEquals(result.getBoolean("was_successfully"), utils.DOCUMENT_VALIDATE_SUCCESS_TABLE.get(format));
		
	}
	
	@Test
	public void test_document_cofirma(){
        this.do_check("cofirma", "xml");
	}

	@Test
	public void test_document_contrafirma(){this.do_check("contrafirma", "xml");}

	@Test
	public void test_document_msoffice(){
        this.do_check("msoffice", "msoffice");
	}

	@Test
	public void test_document_odf(){
        this.do_check("odf", "odf");
	}

	@Test
	public void test_document_pdf(){
        this.do_check("pdf", "pdf");
    }
}
