package Model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class Model_Image {
	public String path;
	public String name;
	public String extension;
	public BufferedImage buffered;
	public byte[]   bytes;
	
	public byte[] convert_to_byte2() {
		try {
		String temp = path;
		StringTokenizer token = new StringTokenizer(temp, ".", false);
		this.name = token.nextToken();
		this.extension = token.nextToken();		
		File file = new File(path);
		BufferedImage bi = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, extension, baos);
		byte[] bytes = baos.toByteArray();
		return bytes;
		} catch (IOException e) {
			return null;
		}
		  
	}
	
	public  String encodeImage() {
		byte[] temp = this.convert_to_byte2();
		return Base64.getEncoder().encodeToString(temp);
        //return Base64.getEncoder().encode(temp).toString();
    }
	
	public byte[] decodeImage(String imageDataString) {
		return Base64.getDecoder().decode(imageDataString);
	}
	
	public BufferedImage convert_to_image(String input) throws IOException {
		byte[] bytes = this.decodeImage(input);
		InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage newBi = ImageIO.read(is);
        this.buffered = newBi;
        return this.buffered;
	}
}
