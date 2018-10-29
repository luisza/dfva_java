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
		String name ="certs/"+identification.replace("-", "")+".";
		InputStream cert = utils.read_files_inputstream("crt", "certificate", name);
		JsonObject response = utils.client.validate(cert, "certificate", name);
		
		assertEquals(response.getInt("status"), this.get_int(identification));
		boolean result =  this.get_boolean(identification);
		if(result){
			assertEquals(response.getString("full_name"), this.get_name(identification));
			assertEquals(response.getBoolean("was_successfully"), result);			
		}
	}
	
	@Test
	public void test_539895508773(){
        this.make_validation("539895508773");
	}

	@Test
	public void test_0241323596(){
        this.make_validation("02-4132-3596");
	}

	@Test
	public void test_166306239151(){
        this.make_validation("166306239151");
	}

	@Test
	public void test_0346853514(){
        this.make_validation("03-4685-3514");
	}

	@Test
	public void test_0345625753(){
        this.make_validation("03-4562-5753");
	}

	@Test
	public void test_0829597760(){
        this.make_validation("08-2959-7760");
    }

}
