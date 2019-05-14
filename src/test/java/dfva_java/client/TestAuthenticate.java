package dfva_java.client;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.json.JsonObject;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestAuthenticate {
	public static List<String> AUTH_ALLOWED_TEST = Arrays.asList();
	private BaseUtils utils = BaseUtils.getInstance();
		
	@BeforeClass
	public static void onceExecutedBeforeAll() {
		JsonObject obj;
		BaseUtils utils = BaseUtils.getInstance();
		Enumeration<String> keys = utils.AUTHENTICATION_RESPONSE_TABLE.keys();
		String identification;
		while(keys.hasMoreElements()){
			identification = (String)keys.nextElement();
			if( AUTH_ALLOWED_TEST.isEmpty() || 	AUTH_ALLOWED_TEST.indexOf(identification)!=-1){
				obj = utils.client.authenticate(identification);
				utils.AUTH_TRANSACTIONS.put(identification, obj);
			}
		}
		try {
			Thread.sleep(BaseUtils.WAIT_AUTH);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void do_checks(String identification) {
		if (AUTH_ALLOWED_TEST.isEmpty() || AUTH_ALLOWED_TEST.indexOf(identification) != -1) {
			List<String> noresponse = Arrays.asList("500000000000", "01-1919-2222", "01-1919-2020",
					"01-1919-2121", "9-0000-0000-000");

			if (noresponse.indexOf(identification) != -1) {
				Integer expdata = (Integer) utils.AUTHENTICATION_RESPONSE_TABLE.get(identification);
				Integer recdata = ((JsonObject) utils.AUTH_TRANSACTIONS.get(identification)).getInt("status");
				assertEquals(expdata, recdata);
			}else{
						
				JsonObject obj = utils.AUTH_TRANSACTIONS.get(identification);
				JsonObject resobj = utils.client.authenticate_check(
						String.format("%d",obj.getInt("id_transaction")));

				Integer expdata = utils.AUTHENTICATION_CHECK_RESPONSE_TABLE.get(identification);
				assertEquals((Integer) resobj.getInt("status"), expdata);

                Boolean ok = utils.client.authenticate_delete( String.format("%d",obj.getInt("id_transaction")));
                assertEquals(ok, true);
			}
		}
	}


	@Test
	public void test_auth_0119192020(){
		this.do_checks("01-1919-2020");
	}

	@Test
	public void test_auth_0111002211(){
        this.do_checks("01-1100-2211");

	}

	@Test
	public void test_auth_0177889900(){
        this.do_checks("01-7788-9900");

	}
	@Test
	public void test_auth_0133445566(){
        this.do_checks("01-3344-5566");

	}
	@Test
	public void test_auth_0160607070(){
        this.do_checks("01-6060-7070");

	}
	@Test
	public void test_auth_900000000000(){
        this.do_checks("9-0000-0000-000");

	}
	@Test
	public void test_auth_100000000000(){
        this.do_checks("100000000000");

	}
	@Test
	public void test_auth_0120203030(){
        this.do_checks("01-2020-3030");

	}
	@Test
	public void test_auth_0110102020(){
        this.do_checks("01-1010-2020");

	}
	@Test
	public void test_auth_500000000000(){
        this.do_checks("500000000000");

	}
	@Test
	public void test_auth_0119192222(){
        this.do_checks("01-1919-2222");

	}
	@Test
	public void test_auth_0140405050(){
        this.do_checks("01-4040-5050");

	}
	@Test
	public void test_auth_0180809090(){
        this.do_checks("01-8080-9090");

	}
	@Test
	public void test_auth_0119192121(){
        this.do_checks("01-1919-2121");
    }

}
