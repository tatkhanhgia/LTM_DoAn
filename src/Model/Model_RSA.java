package Model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import controll.En_Decrypt_RSA;


public class Model_RSA {

    public byte[] encrypt;
    public PublicKey publickey;
    public PrivateKey privatekey;

    public Model_RSA() throws Exception {
//    	this.privatekey = this.getPrivateKey();
//    	this.publickey  = this.getPublicKey();
    }
    
    //Get from file
    public PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(En_Decrypt_RSA.PRIVATE_KEY_FILE).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privatekey = kf.generatePrivate(spec);
        return kf.generatePrivate(spec);
    }

    //Get from file
    public PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(En_Decrypt_RSA.PUBLIC_KEY_FILE).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publickey = kf.generatePublic(spec);
        return kf.generatePublic(spec);
    }

    //Encrypt String
    public String encrypt(String input) throws InvalidKeyException, Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publickey);

        byte encrypt[] = cipher.doFinal(input.getBytes());        
        String encrypted = Base64.getEncoder().encodeToString(encrypt);
        return encrypted;
    }
    public byte[] get_byte_cipher() {
    	return encrypt;
    }
    
    //Decrypt String
    public String decrypt(String input) throws InvalidKeyException, Exception {    	
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privatekey);

        byte[] decode = Base64.getDecoder().decode(input);
        byte[] byteDecrypted = cipher.doFinal(decode);
        String decrypted = new String(byteDecrypted);
        return decrypted;
    }

    //Get private key to String
    public String getPrivateKey_ToString() {
        PrivateKey privatekey = this.privatekey;
        RSAPrivateKey rsaPri = (RSAPrivateKey)(privatekey);
        return String.valueOf(rsaPri.getPrivateExponent());
    }
    
    //Get public key to String
    public String getPublicKey_ToString() {
    	PublicKey publickey2 = this.publickey;
    	byte[] byte_key = publickey2.getEncoded();
    	String str_key  = Base64.getEncoder().encodeToString(byte_key);
    	return str_key;
    }
    
    public void setPublicKey(String input){
    	
		try {
			byte[] byte_key = Base64.getDecoder().decode(input);
	    	KeyFactory factory;
				factory = KeyFactory.getInstance("RSA");
	    	PublicKey public_key;
			public_key = (PublicKey) factory.generatePublic(new X509EncodedKeySpec(byte_key));
			this.publickey = public_key;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
   
 // ====================================================================================
    //Phần này không cần quan tâm - từ đây trở xuống chủ yếu mã - giải file + image
//    static private void processFile(Cipher ci,InputStream in,OutputStream out)
//	throws javax.crypto.IllegalBlockSizeException,
//	       javax.crypto.BadPaddingException,
//	       java.io.IOException
//    {   
//	byte[] ibuf = new byte[1024];
//	int len;
//	while ((len = in.read(ibuf)) != -1) {
//	    byte[] obuf = ci.update(ibuf, 0, len);
//	    if ( obuf != null ) out.write(obuf);
//	}
//	byte[] obuf = ci.doFinal();
//	if ( obuf != null ) out.write(obuf);
//    }
//
//    static private void processFile(Cipher ci,String inFile,String outFile)
//	throws javax.crypto.IllegalBlockSizeException,
//	       javax.crypto.BadPaddingException,
//	       java.io.IOException
//    {
//	try (FileInputStream in = new FileInputStream(inFile);
//	     FileOutputStream out = new FileOutputStream(outFile)) {
//		processFile(ci, in, out);
//	    }
//    }
//    
//    public void doEncrypt(String temp)
//	throws java.security.NoSuchAlgorithmException,
//	       java.security.spec.InvalidKeySpecException,
//	       javax.crypto.NoSuchPaddingException,
//	       javax.crypto.BadPaddingException,
//	       java.security.InvalidKeyException,
//	       javax.crypto.IllegalBlockSizeException,
//	       java.io.IOException
//    {
//	String inputFile = temp;
//	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");        
//	cipher.init(Cipher.ENCRYPT_MODE, publickey);
//	processFile(cipher, inputFile, inputFile + ".enc");
//    }
//
//    public void doDecrypt(String temp)
//	throws java.security.NoSuchAlgorithmException,
//	       java.security.spec.InvalidKeySpecException,
//	       javax.crypto.NoSuchPaddingException,
//	       javax.crypto.BadPaddingException,
//	       java.security.InvalidKeyException,
//	       javax.crypto.IllegalBlockSizeException,
//	       java.io.IOException
//    {
//	String inputFile = temp;
//	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//	cipher.init(Cipher.DECRYPT_MODE, privatekey);
//	processFile(cipher, inputFile, inputFile + ".ver");
//    }
//    
//    public void processFile2(Cipher ci,InputStream in,OutputStream out)
//	throws javax.crypto.IllegalBlockSizeException,
//	       javax.crypto.BadPaddingException,
//	       java.io.IOException,
//	       InvalidKeyException,
//	       NoSuchAlgorithmException,
//	       NoSuchPaddingException
//    {   
//	byte[] ibuf = new byte[in.available()];
//        int i =0;
//        in.read(ibuf);        
//        
//        byte[] temp = ci.doFinal(ibuf,ibuf.length-2,1);
//        out.write(temp);
//	int len;
//	while ((len = in.read(ibuf)) != -1) {
//	    byte[] obuf = ci.update(ibuf, 0, len);
//	    if ( obuf != null ) out.write(obuf);
//	}
//	byte[] obuf = ci.doFinal();
//	if ( obuf != null ) out.write(obuf);
//    }
//    
//    public void processFile2(Cipher ci,String inFile,String outFile)
//	throws javax.crypto.IllegalBlockSizeException,
//	       javax.crypto.BadPaddingException,
//	       java.io.IOException,
//	       InvalidKeyException,
//	       NoSuchAlgorithmException,
//	       NoSuchPaddingException
//    {
//	try (FileInputStream in = new FileInputStream(inFile);
//	     FileOutputStream out = new FileOutputStream(outFile)) {
//		processFile2(ci, in, out);
//	    }
//    }
//    
//    public void enimage(String image) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
//    {
//        FileInputStream fis = new FileInputStream(
//            image);                      
//        byte[] data = new byte[fis.available()];
//        fis.read(data);
//        
//         byte[] key = this.publickey.getEncoded();
//            //key = Arrays.copyOf(key, 16);
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");        
//	cipher.init(Cipher.ENCRYPT_MODE, privatekey);               
//        
//        byte[] temp = cipher.doFinal(data);
//        
//        FileOutputStream fos = new FileOutputStream(
//            "C:\\Users\\gia\\Desktop\\encrypt.jpg");                  
//        fos.write(temp);
//        fos.close();
//        fis.close();
//        System.out.println("Encyption Done...");
//    }
    
     public static void main(String[] args) throws Exception
    {
        Model_RSA a = new Model_RSA();
        a.getPublicKey();        
        a.getPrivateKey();
        Model_RSA b = new Model_RSA();
        String publickey = a.getPublicKey_ToString();		
        b.setPublicKey(publickey);
        System.out.println("plain:hello");
        String temp = b.encrypt("hello");
        System.out.println("cipher:"+temp);
        System.out.println("plain:"+a.decrypt(temp));
    }
}