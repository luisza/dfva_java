package dfva_java.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TEST {
	
	private byte[] generateIV() {
		String ivs = "HCo8TSQj0j5mmByBTDVhgQ==";
		byte [] iv = Base64.decodeBase64(ivs);
		return iv;
	}	
	
	private byte[] getkey(){
		byte [] key = Base64.decodeBase64("U/5VQWS9vclGZKKLYIBdfg==");
		return key;
	}
	
	public void encrypt(){
		String msg = "hola mundo";
		ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
		try {
			encryptAES_EAX(this.getkey(), msg.getBytes("UTF8"), outBuf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void encryptAES_EAX(byte[] key, byte[] data, OutputStream outputBuf) throws DataLengthException, IllegalStateException, InvalidCipherTextException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		Security.addProvider(new BouncyCastleProvider());
		
//		Cipher eax = Cipher.getInstance("AES/EAX/NoPadding", "BC");
//		SecretKeySpec keyspec = new SecretKeySpec(key, eax.getAlgorithm());
//		IvParameterSpec iv = new IvParameterSpec(this.generateIV());
//		eax.init(Cipher.ENCRYPT_MODE, keyspec, iv);
//				
//		byte[] ciphertext =eax.doFinal(data);
		
		EAXBlockCipher eax = new EAXBlockCipher(new AESFastEngine());
		System.out.println(eax.getAlgorithmName());
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
		
		System.out.println("IV");
		System.out.println(new String(Base64.encodeBase64(nonce)));
		
		System.out.println("MAC");
		System.out.println( new String(Base64.encodeBase64(mac)));
		System.out.println("Ciper TEXT");
		System.out.println( new String(Base64.encodeBase64(finaltext)));
		
		
//		
//		outputBuf.write(nonce);
//		outputBuf.write(mac);
//		outputBuf.write(ciphertext);
//		
//		EAXBlockCipher eaxCipher = new EAXBlockCipher(new AESFastEngine());
//		eaxCipher.init(false, params); 
//        byte[] datOut = new byte[eaxCipher.getOutputSize(written)]; 
//        int resultLen = eaxCipher.processBytes(ciphertext, 0, written, datOut, 0); 
//        eaxCipher.doFinal(datOut, resultLen);
//        System.out.println(new String(datOut));
		
		
	}
}
