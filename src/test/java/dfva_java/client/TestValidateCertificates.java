package dfva_java.client;

import static org.junit.Assert.assertEquals;
import java.io.InputStream;
import java.util.List;
import javax.json.JsonObject;

import org.junit.Test;

public class TestValidateCertificates {
	private BaseUtils utils = BaseUtils.getInstance();

	private boolean get_boolean(String identification){
		boolean dev = false;
		List<String> ids = utils.VALIDATE_CERTIFICATE_RESPONSE_TABLE.get(identification);
		String resp = ids.get(2);
		if(resp.equals("true")){
			dev=true;
		}
		return dev;
	}
	
	private int get_int(String identification){
		List<String> ids = utils.VALIDATE_CERTIFICATE_RESPONSE_TABLE.get(identification);
		return Integer.parseInt(ids.get(1));
	}

	private String get_name(String identification){
		List<String> ids = utils.VALIDATE_CERTIFICATE_RESPONSE_TABLE.get(identification);
		return ids.get(0);
	}

	
	public void make_validation(String identification){
		String format = "crt";
		String name ="certs/"+identification.replace("-", "")+".";

		InputStream cert = utils.read_files_inputstream(format, "certificate", name);
		JsonObject response = utils.client.validate(cert, "certificate", format);

		boolean result =  this.get_boolean(identification);
		assertEquals(response.getInt("status"), this.get_int(identification));
		if(result){
			assertEquals(response.getString("full_name"), this.get_name(identification));
			assertEquals(response.getBoolean("was_successfully"), result);			
		}
	}

	@Test
	public void test_0100010002(){
		this.make_validation("01-0001-0002");
	}

	@Test
	public void test_199887755443(){
		this.make_validation("199887755443");
	}

	@Test
	public void test_0100010002exp(){
		this.make_validation("01-0001-0002exp");
	}

	@Test
	public void test_199887755443exp(){this.make_validation("199887755443exp");
	}

	@Test
	public void test_0100010002rev(){this.make_validation("01-0001-0002rev");
	}

	@Test
	public void test_199887755443rev(){this.make_validation("199887755443rev");
	}


}
