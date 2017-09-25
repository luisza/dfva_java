package dfva_java.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.openssl.PEMReader;

public class Crypto {
	protected Settings settings;
	protected static final Logger logger =
	        Logger.getLogger("dfva_java");
	
	public Crypto(Settings settings) {
		this.settings = settings;
	}

   public PublicKey getPublicKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IOException {   
	   PEMReader pemReader = new PEMReader(new StringReader(this.settings.publicKey));
	   RSAPublicKey rsaPubKey = (RSAPublicKey) pemReader.readObject();
	   pemReader.close();
       return rsaPubKey;
    }
   
	private RSAPrivateKey getPrivateKey() throws IOException{
		PEMReader pemReader = new PEMReader(new StringReader(this.settings.privateKey));
		RSAPrivateKey rsaPubKey = (RSAPrivateKey) pemReader.readObject();
		pemReader.close();
	    return rsaPubKey;
	}
		

	private void encryptOAEP( byte[] data, OutputStream output) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException, IllegalBlockSizeException, BadPaddingException{
		Cipher  rsa = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding", "BC");//PKCS1-OAEP  , "BC"
		rsa.init(Cipher.ENCRYPT_MODE, this.getPublicKey());		
		byte[]  session =  rsa.doFinal(data);
		output.write(session);
	}
	
	private byte[] decryptOAEP(byte[] data) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException, IllegalBlockSizeException, BadPaddingException{
		Cipher  rsa = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding", "BC");//PKCS1-OAEP  , "BC"
		rsa.init(Cipher.DECRYPT_MODE, this.getPrivateKey());		
		byte[]  session =  rsa.doFinal(data);
		return session;
	}
	
	private byte[] decryptAES_EAX(byte[] key, byte[] nonce, byte[] data) throws DataLengthException, IllegalStateException, InvalidCipherTextException, IOException{
		EAXBlockCipher eax = new EAXBlockCipher(new AESFastEngine());
		
		AEADParameters params = new AEADParameters(
				new KeyParameter(key), 
				eax.getBlockSize()*8, nonce, new byte[0]);
		
		eax.init(false, params);
		
		
		byte[] ciphertext = new byte[eax.getOutputSize(data.length)];
		int written = eax.processBytes(data, 0, data.length, ciphertext, 0);
		written += eax.doFinal(ciphertext, written);
		return ciphertext;
	}
	
	
	private byte[] generateIV() {
		byte[] iv = new byte[16];
		Random r = new Random();
		r.nextBytes(iv);	
		return iv;
	}
	
	private void encryptAES_EAX(byte[] key, byte[] data, OutputStream outputBuf) throws DataLengthException, IllegalStateException, InvalidCipherTextException, IOException{
		EAXBlockCipher eax = new EAXBlockCipher(new AESFastEngine());
		
		byte[] nonce = this.generateIV();
		AEADParameters params = new AEADParameters(
				new KeyParameter(key), 
				eax.getBlockSize()*8, nonce, new byte[0]);
		
		eax.init(true, params);
		
		byte[] ciphertext = new byte[eax.getOutputSize(data.length)];
		int written = eax.processBytes(data, 0, data.length, ciphertext, 0);
		written += eax.doFinal(ciphertext, written);
		byte[] mac = eax.getMac();
		int finalsize=ciphertext.length - mac.length;
		byte[] finaltext = new byte[finalsize];
		System.arraycopy(ciphertext, 0, finaltext, 0, finaltext.length);
		
		outputBuf.write(nonce);
		outputBuf.write(mac);
		outputBuf.write(finaltext);
	}
	
	
	public String encrypt(String data) {
		String dev="";
		byte[] session = new byte[16];
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			
			// create a session seed
			SecureRandom.getInstance("SHA1PRNG").nextBytes(session);
			// encrypt session and save in output
			this.encryptOAEP( session, output);
			// encrypt data with session and save in output
			this.encryptAES_EAX(session, data.getBytes(), output);
			// convert to base64 encrypted data
			dev = new String(Base64.encodeBase64(output.toByteArray()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Problemas de encrypción", e);
		}
		return dev;
	}
	
	public String decrypt(String data){
		try {
			return this._decrypt(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Problemas al desencriptar", e);
		}

		return "";	
	}
	
	private String _decrypt(String data) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, DataLengthException, IllegalStateException, InvalidCipherTextException {
		byte[] decodedata = Base64.decodeBase64(data);
		RSAPrivateKey privateKey = this.getPrivateKey();
		int session_size = privateKey.getModulus().bitLength()/8;		
		byte[] session = new byte[session_size];
		byte [] nonce = new byte[16];
		byte [] mac = new byte[16];
		System.arraycopy(decodedata, 0, session, 0, session_size);
		byte[] key = this.decryptOAEP(session);
		byte[] edata = new byte[decodedata.length-session_size-16];
		
		System.arraycopy(decodedata, session_size, nonce, 0, 16);
		System.arraycopy(decodedata, session_size+16, mac, 0, 16);
		System.arraycopy(decodedata, session_size+32, edata, 0, edata.length-16);
		System.arraycopy(mac, 0, edata, edata.length-16, 16);
		
		return new String(decryptAES_EAX(key, nonce,edata));
	}

	
}
