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
    
    public void encrypt_Image_XORbit(String image,String fileoutput) throws IOException //XOR bit
    {
    	//fileoutput =  "C:\\Users\\gia\\Desktop\\encrypt.jpg";
        FileInputStream fis = new FileInputStream(
            image);
                             
        // Converting Image into byte array, create a
        // array of same size as Image size
                             
        byte data[] = new byte[fis.available()];
                             
        // Read the array
        fis.read(data);
        int i = 0;
                             
        // Performing an XOR operation on each value of
        // byte array due to which every value of Image
        // will change.
        for (byte b : data) {
            data[i] = (byte)(b ^ mykey.hashCode());
            i++;
        }
                             
        // Opening a file for writing purpose
        FileOutputStream fos = new FileOutputStream(
           fileoutput);
                             
        // Writing new byte array value to image which
        // will Encrypt it.
                             
        fos.write(data);
                             
        // Closing file
        fos.close();
        fis.close();
        System.out.println("Encyption Done...");
    }
    
    public void decrypt_Image_XORbit(String fileinput, String fileoutput) throws FileNotFoundException, IOException//XOR bit
    {
    	//fileinput  = "C:\\Users\\gia\\Desktop\\encrypt.jpg"
    	//fileoutput = "C:\\Users\\gia\\Desktop\\decrypt.jpg";
        FileInputStream fis = new FileInputStream(
        fileinput);
          
        // Converting image into byte array,it will
        // Create a array of same size as image.
        byte data[] = new byte[fis.available()];
            
        // Read the array
            
        fis.read(data);
        int i = 0;
            
        // Performing an XOR operation
        // on each value of
        // byte array to Decrypt it.
        for (byte b : data) {
            data[i] = (byte)(b ^ mykey.hashCode());
            i++;
        }
            
        // Opening file for writting purpose
        FileOutputStream fos = new FileOutputStream(
            fileoutput);
            
        // Writting Decrypted data on Image
        fos.write(data);
        fos.close();
        fis.close();
        System.out.println("Decyption Done...");
    }
    
    public String encrypt_Image_AES(String image,String fileoutput) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {
    	//fileoutput = "C:\\Users\\gia\\Desktop\\encrypt.jpg";
        FileInputStream fis = new FileInputStream(
            image);                      
        byte[] data = new byte[fis.available()];
        fis.read(data);
        
         byte[] key = mykey.getBytes();
            //key = Arrays.copyOf(key, 16);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] temp = cipher.doFinal(data);
        
        FileOutputStream fos = new FileOutputStream(
            fileoutput);                  
        fos.write(temp);
        fos.close();
        fis.close();
        System.out.println("Encyption Done...");
        
        return Base64.getEncoder().encodeToString(temp);
    }
    
    public void decrypt_Image_AES() throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
         FileInputStream fis = new FileInputStream(
        "C:\\Users\\gia\\Desktop\\encrypt.jpg");
         
        byte[] data = new byte[fis.available()];            
        fis.read(data);        
        
        byte[] key = mykey.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);        
        byte[] temp = cipher.doFinal(data);
        
        FileOutputStream fos = new FileOutputStream(
            "C:\\Users\\gia\\Desktop\\decrypt.jpg");
        fos.write(temp);
        fos.close();
        fis.close();
        System.out.println("Decyption Done...");        
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, FileNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
    	
    }
}
