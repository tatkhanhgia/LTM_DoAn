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
	
	//Hàm chuyển đổi sang dạng byte (lấy path hình ảnh tại client)
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
	//Hàm chuyển đổi dạng byte sang String để truyền giữa client - server
	public  String encodeImage() {
		byte[] temp = this.convert_to_byte2();
		return Base64.getEncoder().encodeToString(temp);
    }
	
	//Hàm chuyển đổi dạng String sang byte
	public byte[] decodeImage(String imageDataString) {
		return Base64.getDecoder().decode(imageDataString);
	}
	//Hàm chuyển đổi dạng byte sang image hiện lên client
	public BufferedImage convert_to_image(String input) throws IOException {
		byte[] bytes = this.decodeImage(input);
		InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage newBi = ImageIO.read(is);
        this.buffered = newBi;
        return newBi;
	}
	//Hàm chuyển đổi sang byte với tham số truyền vào là BufferedImage
	public byte[] convert_to_byte_with_buffered(BufferedImage buff,String extension) {
		try {							
		BufferedImage bi = buff;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, extension, baos);
		byte[] bytes = baos.toByteArray();
		return bytes;
		} catch (IOException e) {
			return null;
		} 
	}
	public  String encodeImage_buffered(BufferedImage buff, String extension) {
		byte[] temp = this.convert_to_byte_with_buffered(buff, extension);
		return Base64.getEncoder().encodeToString(temp);
    }
}
