package dfva_java.client.rsa;

import java.io.IOException;
import java.io.StringReader;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.PrivateKey;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec; 

public class PEMReader {
  protected final String ENCRYPTION_ALGORITHM = "RSA"; 
	private StringReader _file;
   public PEMReader(StringReader stringReader) {
		_file = stringReader;
   	}

   public PublicKey getPubKey() throws Exception  {

		PemReader reader = new PemReader(_file);

		byte[] pubKey = reader.readPemObject().getContent();

		reader.close();

		KeyFactory keyFactory = KeyFactory.getInstance(this.ENCRYPTION_ALGORITHM);

		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKey);

		return keyFactory.generatePublic(pubKeySpec);

  }


   public PrivateKey getPrivateKey() throws IOException
   {
       try
       {
           // need to use some BC classes to parse PEM files
           // fecking Java, POS at times
           PemReader pr = new PemReader(_file);
           try
           {
               PemObject obj = pr.readPemObject();
               KeyFactory kf = KeyFactory.getInstance("RSA");
               PrivateKey key = kf.generatePrivate(new PKCS8EncodedKeySpec(obj.getContent()));
               return key;
           }
           finally
           {
               pr.close();
           }
       }
       catch (Exception e)
       {
           throw new IOException("Error loading key", e);
       }
   }
	
	public void close(){
		this._file.close();
	}
}
