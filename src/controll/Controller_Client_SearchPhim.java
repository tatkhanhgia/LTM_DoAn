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
import Model.Model_Movie;
import Model.Model_RSA;

public class Controller_Client_SearchPhim {
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private String key;
	private En_Decrypt_AES aes;
	public ArrayList<Model_Movie> listmovie;
	
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
	
	public String Receiver() throws IOException {		
		listmovie = new ArrayList();
		String receive = "";	
		while(true) {										
				receive = in.readLine();
				if (receive.equals("end"))
					return "end";
				if (receive.equals("fail_input"))
					return "fail_input";
				Model_Movie movie = new Model_Movie();
				movie.setId(receive);	//Gắn id
				receive = in.readLine();
				movie.setTitle(receive);	//Gắn title
				receive = in.readLine();
				movie.setOri(receive);	//Gắn ngôn ngữ
				receive = in.readLine();
				movie.setDate(receive);	//Gắn release date
				receive = in.readLine();
				movie.setOverview(receive);	//Gắn mô tả
				receive = in.readLine();
				movie.setVote_av(receive);	//Gắn vote
				receive = in.readLine();
				movie.setVote_count(receive);	//Gắn vote
				receive = in.readLine();
				movie.setNgansach(receive);	//Gắn ngân sách
				receive = in.readLine();
				movie.setDoanhthu(receive);	//Gắn doanh thu
				
				receive = in.readLine();	//gắn poster
				if (receive.equals("null"))
					movie.setPoster_image(null);
				else
					movie.convert_to_image(receive);				
				receive = in.readLine();
				movie.poster_path_url = receive;	//Gắn posterpath
				receive = in.readLine();
				movie.setKeyTrailer(receive); 		//Gắn key trailer
				
				//Tạo các arraylist để lưu dữ liệu vào và add vào Object Movie
				ArrayList<String> authorr = new ArrayList();
				ArrayList<String> review  = new ArrayList();
				ArrayList<String> cast  = new ArrayList();
				ArrayList<String> crew  = new ArrayList();
				ArrayList<String> theloai  = new ArrayList();
				ArrayList<String> company  = new ArrayList();
				while (true) {
					receive = in.readLine();
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
					receive = in.readLine();
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
					receive = in.readLine();
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
					receive = in.readLine();
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
					receive = in.readLine();
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
					receive = in.readLine();
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
	
	private boolean Get_PublicKey() {
		try {
			//Get public key
			String receive = in.readLine();
			System.out.println("Nhận publickey_cipher:"+receive);
			Model_RSA rsa = new Model_RSA();
			rsa.setPublicKey(receive);
			System.out.println("Publickey_sau chinh:"+rsa.publickey);
			//Create AES key
			aes = new En_Decrypt_AES();			
			this.key = aes.createKey(); 
			System.out.println("Sessionkey:"+this.key);
			//Encrypt AES key
			String cipherkey = rsa.encrypt(aes.mykey);			
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
	
	public void send_text(String send)
	{
		try {
			String encrypt = aes.encrypt_String(send, key);
			out.write(encrypt);
			out.newLine();
			out.flush();
		} catch (IOException e) {
		}

	}
	
	public void play(String id) {
		ParseJsonFromAPI a = new ParseJsonFromAPI();
		a.playTrailerFromBrowser2(id);
	}
	
	public static void main(String[] args) throws IOException {
//		Controller_Client_SearchPhim a = new Controller_Client_SearchPhim();
//		a.Open_Client("localhost", 1234);
//		a.send_text("blackpink");
//		String temp = a.Receiver();
//		System.out.println("\n\n"+temp);
//		System.out.println("\n\nSố lượng listmovie"+a.listmovie.size());
		
	}

}
