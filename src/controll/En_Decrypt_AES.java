package controll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class En_Decrypt_AES {
    public String mykey;
    
    public String createKey() throws NoSuchAlgorithmException
    {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(128); /* 128-bit AES */
        SecretKey secret = gen.generateKey();        
        byte[] binary = secret.getEncoded();
        String text = String.format("%032X", new BigInteger(+1, binary));
        mykey = text;
        return text;
    }
    
    public String encrypt_String(String strToEncrypt, String myKey) {
      try {
            //MessageDigest sha = MessageDigest.getInstance("SHA-1");
            //byte[] key = myKey.getBytes("UTF-8");
            byte[] key = myKey.getBytes();
            byte[] stringg = strToEncrypt.getBytes();
            //key = sha.digest(key);
            //key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] temp = cipher.doFinal(stringg);
            return Base64.getEncoder().encodeToString(temp);
            //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      } catch (Exception e) {
            System.out.println(e.toString());
      }
      return null;
    }
    
    public String decrypt_String(String strToDecrypt, String myKey) {
      try {
            //MessageDigest sha = MessageDigest.getInstance("SHA-1");
            //byte[] key = myKey.getBytes("UTF-8");
            byte[] key = myKey.getBytes();
            //key = sha.digest(key);
            //key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] temp2 = Base64.getDecoder().decode(strToDecrypt.getBytes());
            byte[] temp = cipher.doFinal(temp2);
            String decoded = new String(temp, "UTF-8"); 
            return decoded;
            //return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      } catch (Exception e) {
            System.out.println(e.toString());
      }
      return null;
}
    
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, FileNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
    	En_Decrypt_AES a = new En_Decrypt_AES();
    	String key = a.createKey();
    	System.out.println("keyaes:"+key);
    }
}
