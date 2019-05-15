package dfva_java.client;

import static org.junit.Assert.assertEquals;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.List;

import javax.json.JsonObject;

import org.junit.BeforeClass;
import org.junit.Test;



public class TestContrafirmaWrong {
	public static List<String> DOCUMENT_ALLOWED_TEST = Arrays.asList();
	private BaseUtils utils = BaseUtils.getInstance();

	public static void save_obj(String identification,
								String format, JsonObject obj,
								BaseUtils utils){

		Dictionary<String, JsonObject>  dicobj = utils.DOCUMENT_TRANSACTIONS_WRONG.get(identification);
		if(dicobj == null){
			dicobj = new Hashtable<String, JsonObject> ();
			utils.DOCUMENT_TRANSACTIONS_WRONG.put(identification, dicobj);
		}
		dicobj.put(format, obj);
	}

	private Integer get_response(String format, String identification, String key){
		Dictionary<String, JsonObject>  dicobj = utils.DOCUMENT_TRANSACTIONS_WRONG.get(identification);

		JsonObject obj = dicobj.get(format);
		return obj.getInt(key);
	}

	@BeforeClass
	public static void onceExecutedBeforeAll() {
		JsonObject obj;
		BaseUtils utils = BaseUtils.getInstance();
		Enumeration<String> keys = utils.DOCUMENT_RESPONSE_WRONG_TABLE.keys();

		String identification;
		while(keys.hasMoreElements()) {
			identification = (String) keys.nextElement();
			if (DOCUMENT_ALLOWED_TEST.isEmpty() || DOCUMENT_ALLOWED_TEST.indexOf(identification) != -1) {
				for (String format : utils.DOCUMENT_FORMATS) {
					obj = utils.client.sign(identification,
							utils.read_files_inputstream("xml", format, "no_contrafirmado."),
							format, //xml_cofirma, xml_contrafirma, odf, msoffice, pdf
							"Sign document with format " + format
					);
					save_obj(identification, format, obj, utils);
				}
			}
			try {
				Thread.sleep(BaseUtils.TIMEWAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void do_checks(String format, String identification) {
		Integer idtransaction = this.get_response(format, identification, "id_transaction");
		JsonObject resobj = utils.client.sign_check(String.format("%d",idtransaction));

		Integer expdata =utils.DOCUMENT_RESPONSE_WRONG_TABLE.get(identification);
		assertEquals(expdata, (Integer) resobj.getInt("status"));

		Boolean ok = utils.client.sign_delete(String.format("%d",idtransaction));
		assertEquals(true, ok);
	}

	@Test
	public void test_contrafirma_not_ok(){
		this.do_checks("xml_contrafirma", "03-0110-2020");
	}
}

