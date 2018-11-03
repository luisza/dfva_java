package dfva_java.client;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.json.JsonObject;

import org.junit.BeforeClass;
import org.junit.Test;
public class TestDocumentSign {
	public static List<String> DOCUMENT_ALLOWED_TEST = Arrays.asList();
	private BaseUtils utils = BaseUtils.getInstance();

	
	public static void save_obj(String identification, 
			String format, JsonObject obj,
			BaseUtils utils){
		
		Dictionary<String, JsonObject>  dicobj = utils.DOCUMENT_TRANSACTIONS.get(identification);
		if(dicobj == null){
			dicobj = new Hashtable<String, JsonObject> ();
			utils.DOCUMENT_TRANSACTIONS.put(identification, dicobj);
		}
		dicobj.put(format, obj);
	}
	@BeforeClass
	public static void onceExecutedBeforeAll() {
		JsonObject obj;
		BaseUtils utils = BaseUtils.getInstance();
		Enumeration<String> keys = utils.DOCUMENT_RESPONSE_TABLE.keys();

		String identification;
		while(keys.hasMoreElements()){
			identification = (String)keys.nextElement();
			if( DOCUMENT_ALLOWED_TEST.isEmpty() || 	DOCUMENT_ALLOWED_TEST.indexOf(identification)!=-1){
				for(String format: utils.DOCUMENT_FORMATS){
					obj = utils.client.sign(identification,
						utils.read_files_inputstream(format), 
						format, //xml_cofirma, xml_contrafirma, odf, msoffice, pdf
						"Sign document with format "+format
						);
					TestDocumentSign.save_obj(identification, format, obj,
							utils);
				}		
			}
		}
		try {
			Thread.sleep(BaseUtils.WAIT_AUTH);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Integer get_response(String format, String identification, 
			String key){
		Dictionary<String, JsonObject>  dicobj = utils.DOCUMENT_TRANSACTIONS.get(identification);
		JsonObject obj = dicobj.get(format);
		return obj.getInt(key);
	}
	
	private void do_checks(String format, String identification) {
		if (DOCUMENT_ALLOWED_TEST.isEmpty() || DOCUMENT_ALLOWED_TEST.indexOf(identification) != -1) {
			List<String> noresponse = Arrays.asList(
					"500000000000", "01-1919-2222",
                    "01-1919-2020", "01-1919-2121", "9-0000-0000-000");

			if (noresponse.indexOf(identification) != -1) {
		
				Integer expdata = (Integer) utils.DOCUMENT_RESPONSE_TABLE.get(identification);
				Integer recdata = this.get_response(format, identification, "status");
				assertEquals(expdata, recdata);
			}else{
				Integer idtransaction = this.get_response(format, identification, "id_transaction");
				JsonObject resobj = utils.client.sign_check(
						String.format("%d",idtransaction));
				Integer expdata =utils.DOCUMENT_CHECK_RESPONSE_TABLE.get(identification);
				assertEquals((Integer) resobj.getInt("status"), expdata);
                Boolean ok = utils.client.sign_delete(String.format("%d",idtransaction));
                assertEquals(ok, true);
			}
		}
	}

	@Test
	public void test_xml_cofirma_0180809090(){
        this.do_checks("xml_cofirma", "01-8080-9090");
	}

	@Test
	public void test_xml_cofirma_0177889900(){
        this.do_checks("xml_cofirma", "01-7788-9900");
	}

	@Test
	public void test_xml_cofirma_0111002211(){
        this.do_checks("xml_cofirma", "01-1100-2211");
	}

	@Test
	public void test_xml_cofirma_0119192121(){
        this.do_checks("xml_cofirma", "01-1919-2121");
	}

	@Test
	public void test_xml_cofirma_0133445566(){
        this.do_checks("xml_cofirma", "01-3344-5566");
	}

	@Test
	public void test_xml_cofirma_0110102020(){
        this.do_checks("xml_cofirma", "01-1010-2020");
	}

	@Test
	public void test_xml_cofirma_0119192222(){
        this.do_checks("xml_cofirma", "01-1919-2222");
	}

	@Test
	public void test_xml_cofirma_0119192020(){
        this.do_checks("xml_cofirma", "01-1919-2020");
	}

	@Test
	public void test_xml_cofirma_0160607070(){
        this.do_checks("xml_cofirma", "01-6060-7070");
	}

	@Test
	public void test_xml_cofirma_0120203030(){
        this.do_checks("xml_cofirma", "01-2020-3030");
	}

	@Test
	public void test_xml_cofirma_100000000000(){
        this.do_checks("xml_cofirma", "100000000000");
	}

	@Test
	public void test_xml_cofirma_0140405050(){
        this.do_checks("xml_cofirma", "01-4040-5050");
	}

	@Test
	public void test_xml_cofirma_500000000000(){
        this.do_checks("xml_cofirma", "500000000000");
	}

	@Test
	public void test_xml_cofirma_900000000000(){
        this.do_checks("xml_cofirma", "9-0000-0000-000");
	}

	@Test
	public void test_xml_contrafirma_0180809090(){
        this.do_checks("xml_contrafirma", "01-8080-9090");
	}

	@Test
	public void test_xml_contrafirma_0177889900(){
        this.do_checks("xml_contrafirma", "01-7788-9900");
	}

	@Test
	public void test_xml_contrafirma_0111002211(){
        this.do_checks("xml_contrafirma", "01-1100-2211");
	}

	@Test
	public void test_xml_contrafirma_0119192121(){
        this.do_checks("xml_contrafirma", "01-1919-2121");
	}

	@Test
	public void test_xml_contrafirma_0133445566(){
        this.do_checks("xml_contrafirma", "01-3344-5566");
	}

	@Test
	public void test_xml_contrafirma_0110102020(){
        this.do_checks("xml_contrafirma", "01-1010-2020");
	}

	@Test
	public void test_xml_contrafirma_0119192222(){
        this.do_checks("xml_contrafirma", "01-1919-2222");
	}

	@Test
	public void test_xml_contrafirma_0119192020(){
        this.do_checks("xml_contrafirma", "01-1919-2020");
	}

	@Test
	public void test_xml_contrafirma_0160607070(){
        this.do_checks("xml_contrafirma", "01-6060-7070");
	}

	@Test
	public void test_xml_contrafirma_0120203030(){
        this.do_checks("xml_contrafirma", "01-2020-3030");
	}

	@Test
	public void test_xml_contrafirma_100000000000(){
        this.do_checks("xml_contrafirma", "100000000000");
	}

	@Test
	public void test_xml_contrafirma_0140405050(){
        this.do_checks("xml_contrafirma", "01-4040-5050");
	}

	@Test
	public void test_xml_contrafirma_500000000000(){
        this.do_checks("xml_contrafirma", "500000000000");
	}

	@Test
	public void test_xml_contrafirma_900000000000(){
        this.do_checks("xml_contrafirma", "9-0000-0000-000");
	}

	@Test
	public void test_odf_0180809090(){
        this.do_checks("odf", "01-8080-9090");
	}

	@Test
	public void test_odf_0177889900(){
        this.do_checks("odf", "01-7788-9900");
	}

	@Test
	public void test_odf_0111002211(){
        this.do_checks("odf", "01-1100-2211");
	}

	@Test
	public void test_odf_0119192121(){
        this.do_checks("odf", "01-1919-2121");
	}

	@Test
	public void test_odf_0133445566(){
        this.do_checks("odf", "01-3344-5566");
	}

	@Test
	public void test_odf_0110102020(){
        this.do_checks("odf", "01-1010-2020");
	}

	@Test
	public void test_odf_0119192222(){
        this.do_checks("odf", "01-1919-2222");
	}

	@Test
	public void test_odf_0119192020(){
        this.do_checks("odf", "01-1919-2020");
	}

	@Test
	public void test_odf_0160607070(){
        this.do_checks("odf", "01-6060-7070");
	}

	@Test
	public void test_odf_0120203030(){
        this.do_checks("odf", "01-2020-3030");
	}

	@Test
	public void test_odf_100000000000(){
        this.do_checks("odf", "100000000000");
	}

	@Test
	public void test_odf_0140405050(){
        this.do_checks("odf", "01-4040-5050");
	}

	@Test
	public void test_odf_500000000000(){
        this.do_checks("odf", "500000000000");
	}

	@Test
	public void test_odf_900000000000(){
        this.do_checks("odf", "9-0000-0000-000");
	}

	@Test
	public void test_msoffice_0180809090(){
        this.do_checks("msoffice", "01-8080-9090");
	}

	@Test
	public void test_msoffice_0177889900(){
        this.do_checks("msoffice", "01-7788-9900");
	}

	@Test
	public void test_msoffice_0111002211(){
        this.do_checks("msoffice", "01-1100-2211");
	}

	@Test
	public void test_msoffice_0119192121(){
        this.do_checks("msoffice", "01-1919-2121");
	}

	@Test
	public void test_msoffice_0133445566(){
        this.do_checks("msoffice", "01-3344-5566");
	}

	@Test
	public void test_msoffice_0110102020(){
        this.do_checks("msoffice", "01-1010-2020");
	}

	@Test
	public void test_msoffice_0119192222(){
        this.do_checks("msoffice", "01-1919-2222");
	}

	@Test
	public void test_msoffice_0119192020(){
        this.do_checks("msoffice", "01-1919-2020");
	}

	@Test
	public void test_msoffice_0160607070(){
        this.do_checks("msoffice", "01-6060-7070");
	}

	@Test
	public void test_msoffice_0120203030(){
        this.do_checks("msoffice", "01-2020-3030");
	}

	@Test
	public void test_msoffice_100000000000(){
        this.do_checks("msoffice", "100000000000");
	}

	@Test
	public void test_msoffice_0140405050(){
        this.do_checks("msoffice", "01-4040-5050");
	}

	@Test
	public void test_msoffice_500000000000(){
        this.do_checks("msoffice", "500000000000");
	}

	@Test
	public void test_msoffice_900000000000(){
        this.do_checks("msoffice", "9-0000-0000-000");
	}

	@Test
	public void test_pdf_0180809090(){
        this.do_checks("pdf", "01-8080-9090");
	}

	@Test
	public void test_pdf_0177889900(){
        this.do_checks("pdf", "01-7788-9900");
	}

	@Test
	public void test_pdf_0111002211(){
        this.do_checks("pdf", "01-1100-2211");
	}

	@Test
	public void test_pdf_0119192121(){
        this.do_checks("pdf", "01-1919-2121");
	}

	@Test
	public void test_pdf_0133445566(){
        this.do_checks("pdf", "01-3344-5566");
	}

	@Test
	public void test_pdf_0110102020(){
        this.do_checks("pdf", "01-1010-2020");
	}

	@Test
	public void test_pdf_0119192222(){
        this.do_checks("pdf", "01-1919-2222");
	}

	@Test
	public void test_pdf_0119192020(){
        this.do_checks("pdf", "01-1919-2020");
	}

	@Test
	public void test_pdf_0160607070(){
        this.do_checks("pdf", "01-6060-7070");
	}

	@Test
	public void test_pdf_0120203030(){
        this.do_checks("pdf", "01-2020-3030");
	}

	@Test
	public void test_pdf_100000000000(){
        this.do_checks("pdf", "100000000000");
	}

	@Test
	public void test_pdf_0140405050(){
        this.do_checks("pdf", "01-4040-5050");
	}

	@Test
	public void test_pdf_500000000000(){
        this.do_checks("pdf", "500000000000");
	}

	@Test
	public void test_pdf_900000000000(){
        this.do_checks("pdf", "9-0000-0000-000");
    }
	
}

