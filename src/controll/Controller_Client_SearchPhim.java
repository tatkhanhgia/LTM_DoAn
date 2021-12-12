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
//			//Communicate
//			while(true)
//			{				
//				over = input.readLine();
//
//				//Send to server
//				out.write(over);
//				out.newLine();
//				out.flush();
//				
//				//Receive from server
//				receive = this.Receiver(receive);
//				
//				if (receive.equals("bye")||receive.equals("exception"))
//					break;
//			}		
			
			//Close
//			input.close();	
//			Close_Client();
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
				movie.setId(receive);
				receive = in.readLine();
				movie.setTitle(receive);
				receive = in.readLine();
				movie.setOverview(receive);
				receive = in.readLine();
				movie.setVote_av(receive);
				receive = in.readLine();
				movie.setVote_count(receive);
//				receive = in.readLine();
//				movie.convert_to_image(receive);
				receive = in.readLine();
				movie.poster_path_url = receive;
				receive = in.readLine();
				movie.setKeyTrailer(receive); 
				ArrayList<String> authorr = new ArrayList();
				ArrayList<String> review  = new ArrayList();
				ArrayList<String> cast  = new ArrayList();
				ArrayList<String> crew  = new ArrayList();
				while (true) {
					receive = in.readLine();
					if(receive.equals("author")||receive.equals("null"))
						break;
					authorr.add(receive);
				}
				while (true) {
					receive = in.readLine();
					if(receive.equals("review")||receive.equals("null"))
						break;
					review.add(receive);
				}
				while (true) {
					receive = in.readLine();
					if(receive.equals("cast")||receive.equals("null"))
						break;
					cast.add(receive);
				}
				while (true) {
					receive = in.readLine();
					if(receive.equals("crew")||receive.equals("null"))
						break;
					crew.add(receive);
				}
				movie.setAuthor(authorr);
				movie.setReview(review);
				movie.setCast(cast);
				movie.setCrew(crew);
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
