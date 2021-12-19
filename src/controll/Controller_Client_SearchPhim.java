package controll;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import API.ParseJsonFromAPI;
import controll.En_Decrypt_AES;
import controll.En_Decrypt_RSA;
import Model.Model_Image;
import Model.Model_Movie;
import Model.Model_RSA;

public class Controller_Client_SearchPhim {
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private String key;
	private En_Decrypt_AES sessionkey;			//SessionKey
	public ArrayList<Model_Movie> listmovie;	//Lưu trữ movie
	
	//Function Open_Client
	public void Open_Client(String host, int port)
	{
		try {
			socket = new Socket(host,port);
			in 	   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out    = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
			//Create input in client
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String         over  = ""; //send
			String       receive = ""; //receive
				
			System.out.println("Connect success!!");
				
			//SSL HandShake
			while(true)
			{
				if(this.Get_PublicKey())
					break;
				out.write("send again");
				out.newLine();
				out.flush();
			}
			
		} catch (IOException e) {
			System.out.println("IOException!!");
			System.out.println("Close");
		} 
	}
	
	//Hàm để nhận về dữ liệu ảnh khi chạy các chức năng chỉnh sửa
	public Model_Image Receive_Image()
	{
		String receive = "";
		Model_Image temp = new Model_Image();
		while(true) {
			try {				
				receive = in.readLine();
				receive = this.decrypt_string(receive);
				if(receive.equals("end"))
					return temp;
				if(receive.equals("fail_input"))
					return null;
				//Đọc và gắn vào Model Image
				String image = receive;				
				temp.convert_to_image(image);
				receive = in.readLine(); //đọc định dạng extension
				receive = this.decrypt_string(receive);
				temp.extension = receive;
				
			} catch (IOException e) {
				return null;
			}
		}		
	}
	
	//Hàm để nhận về dữ liệu các ảnh khi chạy chức năng API (tìm ảnh tương tự)
	public ArrayList<Model_Image> Receive_API(){
		String receive = "";
		ArrayList<Model_Image> array = new ArrayList<Model_Image>();
					
		while(true) {
			try {				
				receive = in.readLine();
				receive = this.decrypt_string(receive);
				if(receive.equals("end"))
					return array;
				if(receive.equals("fail_input"))
					return null;
				//Đọc và gắn vào Model Image
				String image = receive;			
				Model_Image temp = new Model_Image();	
				temp.convert_to_image(image);				
				temp.extension = "jpg";			
				array.add(temp);
			} catch (IOException e) {
				return null;
			}
		}		
	}
	
	
	//Function Close Client
	public void Close_Client()
	{
		try {			
			in.close();
			out.close();
			socket.close();
		} catch(Exception e) {
			System.out.println("Catch Exception!! Force Close");
		}
	}
	
	//Hàm để get về phim khi chạy chức năng search phim
	public String Receiver() throws IOException {	
		listmovie = new ArrayList<Model_Movie>();
		String receive = "";	
		while(true) {														
				receive = this.decrypt_string(in.readLine());
				if (receive.equals("end"))
					return "end";
				if (receive.equals("fail_input"))
					return "fail_input";
				if (receive.equals("result_fail"))
					return "fail_search";
				Model_Movie movie = new Model_Movie();
				movie.setId(receive);	//Gắn id
				receive = this.decrypt_string(in.readLine());
				movie.setTitle(receive);	//Gắn title
				receive = this.decrypt_string(in.readLine());
				movie.setOri(receive);	//Gắn ngôn ngữ
				receive = this.decrypt_string(in.readLine());
				movie.setDate(receive);	//Gắn release date
				receive = this.decrypt_string(in.readLine());
				movie.setOverview(receive);	//Gắn mô tả
				receive = this.decrypt_string(in.readLine());
				movie.setVote_av(receive);	//Gắn vote
				receive = this.decrypt_string(in.readLine());
				movie.setVote_count(receive);	//Gắn vote
				receive = this.decrypt_string(in.readLine());
				movie.setNgansach(receive);	//Gắn ngân sách
				receive = this.decrypt_string(in.readLine());
				movie.setDoanhthu(receive);	//Gắn doanh thu
				
				receive = this.decrypt_string(in.readLine());
				if (receive.equals("null"))	//gẮN poster
					movie.setPoster_image(null);
				else
					movie.convert_to_image(receive);				
				receive = this.decrypt_string(in.readLine());
				movie.poster_path_url = receive;	//Gắn posterpath
				receive = this.decrypt_string(in.readLine());
				movie.setKeyTrailer(receive); 		//Gắn key trailer
				
				//Tạo các arraylist để lưu dữ liệu vào và add vào Object Movie
				ArrayList<String> authorr = new ArrayList();
				ArrayList<String> review  = new ArrayList();
				ArrayList<String> cast  = new ArrayList();
				ArrayList<String> crew  = new ArrayList();
				ArrayList<String> theloai  = new ArrayList();
				ArrayList<String> company  = new ArrayList();
				while (true) {
					receive = this.decrypt_string(in.readLine());
					if(receive.equals("null"))
					{
						authorr.add("null");	//Nếu null thì add chữ null để hiện ra màn hình
						break;
					}
					if(receive.equals("author"))//Ký hiệu ngắt author-review
						break;					
					authorr.add(receive);
				}
				while (true) {
					receive = this.decrypt_string(in.readLine());
					if(receive.equals("null"))
					{
						review.add("null");
						break;
					}
					if(receive.equals("review"))//Ký hiệu ngắt review
						break;			
					review.add(receive);					
				}
				while (true) {
					receive = this.decrypt_string(in.readLine());
					if(receive.equals("null"))
					{
						cast.add("null");
						break;
					}
					if(receive.equals("cast"))//Ký hiệu ngắt cast
						break;	
					cast.add(receive);
				}
				while (true) {
					receive = this.decrypt_string(in.readLine());
					if(receive.equals("null"))
					{
						crew.add("null");
						break;
					}
					if(receive.equals("crew"))//Ký hiệu ngắt crew
						break;	
					crew.add(receive);
				}
				while (true) {
					receive = this.decrypt_string(in.readLine());
					if(receive.equals("null"))
					{
						theloai.add("null");
						break;
					}
					if(receive.equals("theloai"))//Ký hiệu ngắt thể loại
						break;	
					theloai.add(receive);
				}
				while (true) {
					receive = this.decrypt_string(in.readLine());
					if(receive.equals("null"))
					{
						company.add("null");
						break;
					}
					if(receive.equals("company"))//Ký hiệu ngắt acompany
						break;	
					company.add(receive);
				}
				movie.setAuthor(authorr);
				movie.setReview(review);
				movie.setCast(cast);
				movie.setCrew(crew);
				movie.setTheLoai(theloai);
				movie.setCompany(company);
				listmovie.add(movie);		
		}								
	}
	
	//Hàm dùng để nhận publickey của server và khởi tạo aes - SSL
	private boolean Get_PublicKey() {
		try {
			//Get public key
			String receive = in.readLine();
			System.out.println("Nhận publickey_cipher:"+receive);
			Model_RSA rsa = new Model_RSA();
			rsa.setPublicKey(receive);
			System.out.println("Publickey_sau chinh:"+rsa.publickey);
			//Create AES key
			sessionkey = new En_Decrypt_AES();			
			this.key = sessionkey.createKey(); 
			System.out.println("Sessionkey:"+this.key);
			//Encrypt AES key
			String cipherkey = rsa.encrypt(sessionkey.mykey);			
			System.out.println("Sessionkey_cipher:"+cipherkey);			
			//Send to server
			out.write(cipherkey);			
			out.newLine();
			out.flush();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Lỗi trong việc thực hiện SSL handshake");
			return false;
		}
	}
	
	//Hàm dùng để gửi dữ liệu cho server
	public void send_text(String send)
	{
		try {			
			System.out.println("Trước mã hóa:"+send);
			String encrypt = sessionkey.encrypt_String(send, key);
			out.write(encrypt);
			out.newLine();
			out.flush();
		} catch (IOException e) {
		}

	}
	
	//Hàm dùng để chạy trailer cho chức năng search phim
	public void play(String id) {
		ParseJsonFromAPI a = new ParseJsonFromAPI();
		a.playTrailerFromBrowser2(id);
	}
	
	//Hàm dùng để giải mã lại khi nhận từ server
	public String decrypt_string(String input) {
		String data = sessionkey.decrypt_String(input, sessionkey.mykey);
		return data;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		Controller_Client_SearchPhim a = new Controller_Client_SearchPhim();
//		a.Open_Client("localhost", 6000);
//		a.send_text("anh");
////		a.send_text("blackpink");
////		String temp = a.Receiver();
////		System.out.println("\n\n"+temp);
////		System.out.println("\n\nSố lượng listmovie"+a.listmovie.size());
//		String test = "C:\\Users\\gia\\Desktop\\BG\\1.jpg";
//		Model_Image temp = new Model_Image();
//		temp.path = test;
//		a.send_text(temp.encodeImage());		
//		a.send_text("resize");
//		a.send_text("large");
//		a.send_text("jpg");
//		
//		a.Receive_Image();
//		a.send_text("bye");
//		a.send_text("bye");
//		a.Close_Client();
	}

}
