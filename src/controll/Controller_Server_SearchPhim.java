package controll;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import API.*;
import Model.Model_Image;
import Model.Model_RSA;
import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

public class Controller_Server_SearchPhim implements Runnable{
	private Thread thread;					//Thread
	private String namethread;				//Tên thread
	public BufferedReader 	in;				//read from pipe socket
	public BufferedWriter 	out;			//write to pipe
	public ServerSocket 	listen; 		//Socket Communicate
	public Socket			socket_server;	//Socket in server
	private Model_RSA		keyrsa;			//Use for SSL Handshake
	private En_Decrypt_AES	sessionkey;		//Key AES
	
	//Constructor
	public Controller_Server_SearchPhim ()
	{
		this.in  		   = null;
		this.out 		   = null;
		this.listen 	   = null;
		this.socket_server = null;
	}
	
	public void setSocket(Socket a) {
		this.socket_server = a;
		this.setIN_OUT();
	}
	public void setIN_OUT() {
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket_server.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket_server.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setNameThread(String name)
	{
		this.namethread = name;
	}
	//Function Open Server
	public void Open_Server(int port)
	{
		while(true) {
		try {
			//listen = new ServerSocket(port);
			//System.out.println("Server open port - waiting for accept");
			//socket_server = listen.accept();
			//System.out.println("Server accepted Client");			
			//out = new BufferedWriter(new OutputStreamWriter(socket_server.getOutputStream()));
			//in = new BufferedReader(new InputStreamReader(socket_server.getInputStream()));
			
			String typeserver ="";			
			
			//SSLHandShake
			this.Start_SSL_Handshake(); //session
			
			//Communication
			while(true) {
				typeserver = in.readLine(); 
				//Giaỉ mã
				String decrypt = sessionkey.decrypt_String(typeserver, sessionkey.mykey);	
				System.out.println("Giaỉ mã loại server:"+decrypt);
				if(decrypt.equals("phim"))
				{
					System.out.println("Server phục vụ:SearchPhim");
					this.Communication_phim();
					continue;
				}
				if(decrypt.equals("anh"))
				{
					System.out.println("Server phục vụ:Xử Lý Ảnh");
					this.Communication_anh();
					continue;
				}
				if(decrypt.equals("bye"))
					break;
			}
			//Close
			Thread.sleep(1000);
			System.out.println("Close Connection");
			break;
			} catch (IOException e) {
				//Lỗi khi client ter
				System.out.println("IOException in Openport - Client terminated");		
				break;
			} catch (NullPointerException e) {
				System.out.println("NullPointer in Openport");		
				break;
			} catch (Exception e) {
				break;
			}			
		} //Loop while
		this.Close_Server();//
	}
	
	//Function write to client có mã hóa
	private void write_to_client(String input)
	{
		 /*	Example:  this.write_to_client("end");
		 * => write String "end" to client
		 */
		try{
			String encrypt = sessionkey.encrypt_String(input, sessionkey.mykey);
			out.write(encrypt); out.newLine(); out.flush();
		} catch (IOException e) {
			System.out.println("IOException in function write_to_client");
		}
	}
	
	//Function close server
	private void Close_Server()
		{
			try {
			out.close();
			in.close();
			socket_server.close();
			listen.close();
			} catch (Exception e) {
				System.out.println("Catch Exception!! Force close server");
			}		
		}
	
	//Function Trim String input
	public String trim_extend(String input)
	{
		input = input.trim();
		input = input.replaceAll("\\s\\s+", " ");
		return input;
	}
	
	//Function check input
	public boolean check_input(String input)
	{
		input = input.trim();
		String regex1= "[^\\n\\t\\f\\v]+";
		boolean gate1 = input.matches(regex1);
		String regex = "^[\\p{L}|\\s|\\d]*$";
		boolean gate2 = input.matches(regex);
		return (gate1==true&&gate2==true)?true:false;		
	}
	
	//Gửi Key RSA
	public boolean Send_key_RSA()  {
		try {
			keyrsa = new Model_RSA();
			keyrsa.publickey = keyrsa.getPublicKey();
			keyrsa.privatekey = keyrsa.getPrivateKey();
			String publickey = keyrsa.getPublicKey_ToString();
			System.out.println("Publickey của server: "+keyrsa.publickey+"\n");
			out.write(publickey); out.newLine(); out.flush();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Lỗi trong việc kết nối SSL với Client");
			return false;
		}
	}
	
	//Nhận Key Session_AES
	public boolean Get_SessionKey() {
		try {
			String sessionkey = in.readLine();
			System.out.println("SessionKey nhận từ client(chưa mã hóa):"+sessionkey);
			String temp = keyrsa.decrypt(sessionkey);			
			this.sessionkey = new En_Decrypt_AES();
			this.sessionkey.mykey = temp;
			System.out.println("SessionKey sau mã hóa:"+this.sessionkey.mykey);
			return true;
		} catch (IOException e) {
			System.out.println("Lấy Session Key thất bại!");
			return false;
		} catch (InvalidKeyException e) {
			System.out.println("Lỗi không có key");
			return false;
		} catch (Exception e) {
			System.out.println("Lỗi Get SessionKkey");
			return false;
		}
		
	}
	
	//Bắt đầu SSL handshake để thiết lập kết nối an toàn
	public void Start_SSL_Handshake() {
		//Start SSL handshake with Client
		//Create RSA key and write to file
			try {
				En_Decrypt_RSA rsa = new En_Decrypt_RSA(1024);
				rsa.generateKeysToFile();
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Lỗi tạo key RSA");
			} catch (NoSuchProviderException e) {
				System.out.println("Lỗi tạo key RSA");
			}
			boolean a = this.Send_key_RSA();
			boolean b = this.Get_SessionKey();
			if(!a&&!b)					
			{
				System.out.println("Lỗi, ngắt kết nối");
				this.Close_Server();
				return;
			}
	}
	
	//Kết nối với server search phim
	public void Communication_phim() {
		while(true) {
			try {
				//Nhận từ client_Receive from client
				String receive = in.readLine();
				
				//Giaỉ mã
				String decrypt = sessionkey.decrypt_String(receive, sessionkey.mykey);							
				StringTokenizer token = new StringTokenizer(decrypt, ";", false);
				if(token.countTokens()<2)
					if(decrypt.equals("bye"))
						break;
				String data = token.nextToken();
				String type = token.nextToken();
				//Kiểm tra đầu vào_check input
				data = this.trim_extend(data);
				if(check_input(data)==false)			
				{
					this.write_to_client("fail_input");
					continue;
				}
				//In ra thông báo nhận từ client_Print notification
				System.out.println("Nhận từ client:"+data);
				this.Get_API_Search_phim(data,type);												
			} catch (IOException e) {
				System.out.println("Client_terminate");
				break;
			}
			
		}
	}

	//Kết nối với server ảnh
	public void Communication_anh() {
		while(true) {
			try {
				//Nhận từ client_Receive from client
				String receive = in.readLine();
				
				//Giaỉ mã
				String decrypt = sessionkey.decrypt_String(receive, sessionkey.mykey);
				String data = decrypt;
				System.out.println("Server đã nhận dữ liệu");				
				
				//Kiểm tra đầu vào_check input
				//decrypt = this.trim_extend(decrypt);				
				if(decrypt.equals("bye"))
				{
					break;
				}
				//Đọc dữ liệu theo thứ tự : ảnh - chức năng - saveas(dành cho format) - đuôi ảnh
				
				receive = in.readLine();				
				decrypt = sessionkey.decrypt_String(receive, sessionkey.mykey);
				String chucnang = decrypt;
				receive = in.readLine();				
				decrypt = sessionkey.decrypt_String(receive, sessionkey.mykey);
				String saveas = decrypt;
				receive = in.readLine();				
				decrypt = sessionkey.decrypt_String(receive, sessionkey.mykey);
				String extension = decrypt;
				this.Get_API_Image(data, chucnang, saveas, extension);												
			} catch (IOException e) {				
				break;
			}
			
		}
	}
	
	//Hàm gọi API và gửi dữ liệu cho client
	//Function Call API and send data back to client
	public void Get_API_Search_phim(String phim,String type) {
		ParseJsonFromAPI API = new ParseJsonFromAPI();
		if ( type.equals("name"))
		{
			boolean a = API.searchByName(phim);	
			if ( !a) {
				this.write_to_client("result_fail");
				return;
			}
		}
		if ( type.equals("dienvien"))
		{
			boolean a = API.searchByPeople(phim);
			if ( !a) {
				this.write_to_client("result_fail");
				return;
			}
		}
		if ( type.equals("popular"))
		{
			boolean a = API.getPopularMovie();
			if ( !a) {
				this.write_to_client("result_fail");
				return;
			}
		}
		API.getPosterImage();
		API.getReviewOfMovie();
		API.getActorOfMovie();
		API.getKeyOfTrailer();
		API.getDetailOfMovie();
		
		//
		for(int i=0;i<API.arraymovie.size();i++)
		{			
			this.write_to_client(API.arraymovie.get(i).getId());		//Gửi id
			
			this.write_to_client(API.arraymovie.get(i).getTitle());		//Gửi title
			
			this.write_to_client(API.arraymovie.get(i).getOri());		//Gửi ngôn ngữ
			
			String temp = API.arraymovie.get(i).getDate();				//Gửi realease date
			if ( temp!=null)
			{
				this.write_to_client(temp);
			} else this.write_to_client("null");
			
			this.write_to_client(API.arraymovie.get(i).getOverview());	//Gửi môtả		
			
			this.write_to_client(API.arraymovie.get(i).getVote_av());	//Gửi vote
			
			this.write_to_client(API.arraymovie.get(i).getVote_count());//Gửi vote
			
			temp = API.arraymovie.get(i).getNgansach();			//Gửi ngân sách
			if ( temp!=null)
			{
				this.write_to_client(temp);
			} else this.write_to_client("null");
						
			temp = API.arraymovie.get(i).getDoanhthu();			//Gửi doanh thu
			if ( temp!=null)
			{
				this.write_to_client(temp);
			} else this.write_to_client("null");
			
			//Gửi ảnh poster
			if(API.arraymovie.get(i).getPoster_image() == null) {
				this.write_to_client("null");
			}
			else
				{this.write_to_client(API.arraymovie.get(i).encodeImage());				
				}
			
			//Gửi posterpath
			if(API.arraymovie.get(i).poster_path_url != null) {			
				this.write_to_client(API.arraymovie.get(i).poster_path_url);
			}
			else
				this.write_to_client("null");
				
			//Gửi trailer
			if(API.arraymovie.get(i).getKeyTrailer() != null) {
				this.write_to_client(API.arraymovie.get(i).getKeyTrailer());
			}
			else
				this.write_to_client("null");
			
			//Gửi Author					
			ArrayList<String> author = API.arraymovie.get(i).getAuthor();
			if ( author != null||author.size()>=1) {
				for(int j=0;j<author.size();j++)
				{					
					this.write_to_client(author.get(j));
				}
					this.write_to_client("author");
			}
			else 
				this.write_to_client("null");
			
			
			//Gửi review
			ArrayList<String> review = API.arraymovie.get(i).getReview();
			if ( review != null||review.size()>=1) {
				for(int j=0;j<review.size();j++)
				{					
					this.write_to_client(review.get(j));
				}
				this.write_to_client("review");
			}	
			else this.write_to_client("null");
			
			//Gửi cast
			ArrayList<String> casr = API.arraymovie.get(i).getCast();
			if ( casr != null || casr.size()>=1 ) {
				for(int j=0;j<casr.size();j++)
				{
					this.write_to_client(casr.get(j));
				}
				this.write_to_client("cast");
				}	
			else this.write_to_client("null");
			
			//Gửi crew
			ArrayList<String> crew = API.arraymovie.get(i).getCrew();
			if ( crew != null ||crew.size()>=1) {
				for(int j=0;j<crew.size();j++)
				{
					this.write_to_client(crew.get(j));
				}			
				this.write_to_client("crew");
				}	
			else this.write_to_client("null");
			
			//Gửi thể loại
			ArrayList<String> theloai = API.arraymovie.get(i).getTheLoai();
			if ( theloai != null ||theloai.size()>=1) {
				for(int j=0;j<theloai.size();j++)
				{
					this.write_to_client(theloai.get(j));
				}
				this.write_to_client("theloai");
			}
			else 
				this.write_to_client("null");
			
			//Gửi công ty
			ArrayList<String> company = API.arraymovie.get(i).getCompany();
			if ( company != null ||theloai.size()>=1) {
				for(int j=0;j<company.size();j++)
				{
					this.write_to_client(company.get(j));
				}
				this.write_to_client("company");
			}
			else 
				this.write_to_client("null");
		}
		this.write_to_client("end");				
	}

	//Hàm gọi API, chỉnh sửa ảnh
	//Function Call API and edit image send back to client
	public void Get_API_Image(String imagestring,String chucnang,String saveas,String extension)
	{
		try {			
			String pathsave = "";
			
			//Check xem phải chức năng API không?
			if(chucnang.equals("api")) {
				FindImage a = new FindImage();
				ArrayList<BufferedImage> result = a.getImages(imagestring);
				
				Model_Image temp = new Model_Image();
				for(int i=0; i<result.size(); i++) {					
					String send = temp.encodeImage_buffered(result.get(i), "jpg");
					this.write_to_client(send);
				}
				this.write_to_client("end");
				return;								
			}
			
			//Tạo model để convert từ String sang Image
			Model_Image image = new Model_Image();
			image.convert_to_image(imagestring);//Buffer
			//Tạo ảnh			
			BufferedImage buffer = image.buffered;//Buffer
		    File outputfile = new File(".\\Image\\temp."+extension);
		    ImageIO.write(buffer, extension, outputfile); 
		    Image images  = ImageIO.read(new File(".\\Image\\temp."+extension));			    
		    //Tạo đối tượng ImagePlus để thực hiện các chức năng
			//ImagePlus lib = new ImagePlus("anh",images);//Image
		    ImagePlus lib = IJ.openImage(".\\Image\\temp."+extension);
			//xóa ảnh temp vừa tạo ra để hỗ trợ khởi tạo ImagePlus
			
			/*switch(chucnang) {
				case "format":{					
					FileSaver file = new FileSaver(lib);
					if(saveas.equals("tif"))
					{
						file.saveAsTiff(".\\Image\\format.tif");
						pathsave = ".\\Image\\format.tif";
						extension = "tif";
					}					
					if(saveas.equals("jpg"))
					{
						file.saveAsJpeg(".\\Image\\format.jpg");
						pathsave = ".\\Image\\format.jpg";
						extension = "jpg";
					}
					if(saveas.equals("gif"))
					{
						file.saveAsGif(".\\Image\\format.gif");
						pathsave = ".\\Image\\format.gif";
						extension = "gif";
					}
					if(saveas.equals("png"))
					{
						file.saveAsPng(".\\Image\\format.png");
						pathsave = ".\\Image\\format.png";
						extension = "png";
					}
			        break;
				}
				case "compress":{					
					FileSaver file = new FileSaver(lib);
			        file.setJpegQuality(0);
			        if(extension.equals("png"))
			        {
			        	file.saveAsPng(".\\Image\\compress.png");
			        	pathsave = ".\\Image\\compress.png";
			        }
			        if(extension.equals("jpg"))
			        {
			        	file.saveAsJpeg(".\\Image\\compress.jpg");
			        	pathsave = ".\\Image\\compress.jpg";
			        }
			        if(extension.equals("tif"))
			        {
			        	file.saveAsTiff(".\\Image\\compress.tif");
			        	pathsave = ".\\Image\\compress.tif";
			        }
			        if(extension.equals("gif"))
			        {
			        	file.saveAsGif(".\\Image\\compress.gif");
			        	pathsave = ".\\Image\\compress.gif";
			        }			        
					break;
				}
				case "gray":{					
					new ImageConverter(lib).convertToGray8();					
					FileSaver file = new FileSaver(lib);
					if(extension.equals("png"))
			        {
			        	file.saveAsPng(".\\Image\\gray.png");
			        	pathsave = ".\\Image\\gray.png";
			        }
			        if(extension.equals("jpg"))
			        {
			        	file.saveAsJpeg(".\\Image\\gray.jpg");
			        	pathsave = ".\\Image\\gray.jpg";
			        }
			        if(extension.equals("tif"))
			        {
			        	file.saveAsTiff(".\\Image\\gray.tif");
			        	pathsave = ".\\Image\\gray.tif";
			        }
			        if(extension.equals("gif"))
			        {
			        	file.saveAsGif(".\\Image\\gray.gif");
			        	pathsave = ".\\Image\\gray.gif";
			        }			     
					break;
				}
				case "resize":{					
					ImageProcessor resize = null;
					if(saveas.equals("small"))				
					{	
						int width = lib.getWidth();
						int height = lib.getHeight();
						while(width>100)
						{
							width -= 50;
						}
						while(height>100)
						{
							height -= 50;
						}
						resize = lib.getProcessor().resize(width, height, false);
					}					
					if(saveas.equals("medium"))
					{	
						resize = lib.getProcessor().resize(lib.getWidth()-100, lib.getHeight()-100, false);
					}
					if(saveas.equals("large"))
					{	
						resize = lib.getProcessor().resize(lib.getWidth()+300, lib.getHeight()+300, false);
					}					
					lib.setProcessor(resize);
					FileSaver file = new FileSaver(lib);
					if(extension.equals("png"))
			        {
			        	file.saveAsPng(".\\Image\\resize.png");
			        	pathsave = ".\\Image\\resize.png";
			        }
			        if(extension.equals("jpg"))
			        {
			        	file.saveAsJpeg(".\\Image\\resize.jpg");
			        	pathsave = ".\\Image\\resize.jpg";
			        }
			        if(extension.equals("tif"))
			        {
			        	file.saveAsTiff(".\\Image\\resize.tif");
			        	pathsave = ".\\Image\\resize.tif";
			        }
			        if(extension.equals("gif"))
			        {
			        	file.saveAsGif(".\\Image\\resize.gif");
			        	pathsave = ".\\Image\\resize.gif";
			        }		
					break;
				}
				default:{
					this.write_to_client("fail_input");
					return;
				}
			}
			*/
			if(chucnang.equals("format"))
			{
				FileSaver file = new FileSaver(lib);
					if(saveas.equals("tif"))
					{
						file.saveAsTiff(".\\Image\\format.tif");
						pathsave = ".\\Image\\format.tif";
						extension = "tif";
					}					
					if(saveas.equals("jpg"))
					{
						file.saveAsJpeg(".\\Image\\format.jpg");
						pathsave = ".\\Image\\format.jpg";
						extension = "jpg";
					}
					if(saveas.equals("gif"))
					{
						file.saveAsGif(".\\Image\\format.gif");
						pathsave = ".\\Image\\format.gif";
						extension = "gif";
					}
					if(saveas.equals("png"))
					{
						file.saveAsPng(".\\Image\\format.png");
						pathsave = ".\\Image\\format.png";
						extension = "png";
					}
				
			}
			if(chucnang.equals("compress"))
			{
				
				FileSaver file = new FileSaver(lib);
		        file.setJpegQuality(0);
		        if(extension.equals("png"))
		        {		        	
		        	file.saveAsPng(".\\Image\\compress.png");
		        	pathsave = ".\\Image\\compress.png";
		        }
		        if(extension.equals("jpg"))
		        {
		        	file.saveAsJpeg(".\\Image\\compress.jpg");
		        	pathsave = ".\\Image\\compress.jpg";
		        }
		        if(extension.equals("tif"))
		        {
		        	file.saveAsTiff(".\\Image\\compress.tif");
		        	pathsave = ".\\Image\\compress.tif";
		        }
		        if(extension.equals("gif"))
		        {
		        	file.saveAsGif(".\\Image\\compress.gif");
		        	pathsave = ".\\Image\\compress.gif";
		        }			      
			}
			if(chucnang.equals("gray"))
			{				
				new ImageConverter(lib).convertToGray8();					
				FileSaver file = new FileSaver(lib);
				if(extension.equals("png"))
		        {
		        	file.saveAsPng(".\\Image\\gray.png");
		        	pathsave = ".\\Image\\gray.png";
		        }
		        if(extension.equals("jpg"))
		        {
		        	file.saveAsJpeg(".\\Image\\gray.jpg");
		        	pathsave = ".\\Image\\gray.jpg";
		        }
		        if(extension.equals("tif"))
		        {
		        	file.saveAsTiff(".\\Image\\gray.tif");
		        	pathsave = ".\\Image\\gray.tif";
		        }
		        if(extension.equals("gif"))
		        {
		        	file.saveAsGif(".\\Image\\gray.gif");
		        	pathsave = ".\\Image\\gray.gif";
		        }			     
			}
			if(chucnang.equals("resize"))
			{
				ImageProcessor resize = null;
				if(saveas.equals("small"))				
				{	
					int width = lib.getWidth();
					int height = lib.getHeight();
					while(width>100)
					{
						width -= 50;
					}
					while(height>100)
					{
						height -= 50;
					}
					resize = lib.getProcessor().resize(width, height, false);
				}					
				if(saveas.equals("medium"))
				{	
					resize = lib.getProcessor().resize(lib.getWidth()-100, lib.getHeight()-100, false);
				}
				if(saveas.equals("large"))
				{	
					resize = lib.getProcessor().resize(lib.getWidth()+300, lib.getHeight()+300, false);
				}					
				lib.setProcessor(resize);
				FileSaver file = new FileSaver(lib);
				if(extension.equals("png"))
		        {
		        	file.saveAsPng(".\\Image\\resize.png");
		        	pathsave = ".\\Image\\resize.png";
		        }
		        if(extension.equals("jpg"))
		        {
		        	file.saveAsJpeg(".\\Image\\resize.jpg");
		        	pathsave = ".\\Image\\resize.jpg";
		        }
		        if(extension.equals("tif"))
		        {
		        	file.saveAsTiff(".\\Image\\resize.tif");
		        	pathsave = ".\\Image\\resize.tif";
		        }
		        if(extension.equals("gif"))
		        {
		        	file.saveAsGif(".\\Image\\resize.gif");
		        	pathsave = ".\\Image\\resize.gif";
		        }
			}
			
			//Thực hiện đọc các ảnh vừa save để đưa về client	
			Model_Image temp = new Model_Image();
			temp.path = pathsave;						

			String send = temp.encodeImage();//String image buffer -> byte[] - >string
			this.write_to_client(send);
			this.write_to_client(extension);
			this.write_to_client("end");//Ký hiệu hết giữa client-server
			File delete = new File(pathsave);
			delete.deleteOnExit();
			delete.delete();
			outputfile.deleteOnExit();
			outputfile.delete();
			lib.close();
		} catch (IOException e) {
			this.write_to_client("fail_input");
		}
	}
	
	
	public static void main(String[] args) {
		Controller_Server_SearchPhim a = new Controller_Server_SearchPhim();
		a.Open_Server(6000);		
	}

	@Override
	public void run() {
		int a = (int)Math.random()*100+1;		
		this.Open_Server(a);		
	}
	
	public void start() {
		System.out.println("Start thread:"+this.namethread);
		if(thread == null)
		{
			thread = new Thread(this, namethread);			
			thread.start();
		}
	}
}
