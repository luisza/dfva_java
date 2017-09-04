package dfva_java.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.bouncycastle.openssl.PEMReader;

public class BaseClient {
	protected Settings settings;
	private HttpClient   httpClient;
	protected Crypto crypto;
	
	public BaseClient(Settings settings) {
		this.settings = settings;
		this.crypto = new Crypto(settings);
		Security.addProvider(new BouncyCastleProvider());
		this.httpClient = HttpClientBuilder.create().build();
	}
	
	private String shaString(){
		String dev="";
		if (this.settings.algorithm == "sha512"){
			dev = "SHA-512";
		}else if(this.settings.algorithm == "sha384"){
			dev = "SHA-512";
		}else{
			dev = "SHA-256";
		}
		return dev;
	}
	
	private String getHashSum(String data) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance(this.shaString());
		md.update(data.getBytes());
		 byte byteData[] = md.digest();
		
		    //convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
		 sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	protected String getHashSum(byte [] data) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance(this.shaString());
		md.update(data);
		 byte byteData[] = md.digest();
		
		    //convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
		 sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	public String getTime(){
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());
		return nowAsISO;
	}
	
	public String readInputStream(InputStream inputStream) throws IOException{
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}
		// StandardCharsets.UTF_8.name() > JDK 7
		return result.toString("UTF-8");
		
	}
	
//	private PublicKey getPublicKey() throws CertificateException, UnsupportedEncodingException{
//		ByteArrayInputStream fin = new ByteArrayInputStream(this.settings.publicCertificate.getBytes("UTF-8"));
//		CertificateFactory f = CertificateFactory.getInstance("X.509");
//		X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);	
//		return certificate.getPublicKey();
//
//	}
	
	public JSONObject getDefaltParams(JSONObject data) throws NoSuchAlgorithmException{
		JSONObject obj = new JSONObject();
		String edata = this.crypto.encrypt(data.toJSONString());
		obj.put("institution", this.settings.institution);
		obj.put("data_hash", this.getHashSum(edata));
		obj.put("algorithm", this.settings.algorithm);
		obj.put("public_certificate", this.settings.publicCertificate);	
		obj.put("data", edata);
		return obj;
	}
	
	protected JSONObject post(String url, String data){
		return post(url, data, true);
	}

	
	protected JSONObject post(String url, String data, Boolean dodecrypt){
	
		HttpResponse  response;
		JSONObject result= new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			HttpPost post = new HttpPost(url);
			StringEntity postingString = new StringEntity(data);
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			response = this.httpClient.execute(post);
			
			if (response != null) {
				result= (JSONObject) parser.parse(
						this.readInputStream(
								response.getEntity().getContent()
								)
						); //Get the data in the entity
				if(dodecrypt){
					result= (JSONObject) parser.parse(
							this.crypto.decrypt((String) result.get("data")));
				}
            }
		} catch (Exception e) {
		    // handle exception here
			e.printStackTrace();
		}
		
		return result;
	}
	
}
