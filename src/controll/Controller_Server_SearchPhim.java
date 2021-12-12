package controll;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
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
import API.*;
import Model.Model_RSA;

public class Controller_Server_SearchPhim {
	private BufferedReader 	in;				//read from pipe socket
	private BufferedWriter 	out;			//write to pipe
	private ServerSocket 	listen; 		//Socket Communicate
	private Socket			socket_server;	//Socket in server
	private Model_RSA		keyrsa;			//Use for SSL Handshake
	private En_Decrypt_AES	sessionkey;		//Key AES
	
	//Constructor
	public Controller_Server_SearchPhim()
	{
		this.in  		   = null;
		this.out 		   = null;
		this.listen 	   = null;
		this.socket_server = null;
	}
	
	//Function Open Server
	public void Open_Server(int port)
	{
		while(true) {
		try {
			listen = new ServerSocket(port);
			System.out.println("Server open port - waiting for accept");
			socket_server = listen.accept();
			System.out.println("Server accepted Client");			
			out = new BufferedWriter(new OutputStreamWriter(socket_server.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket_server.getInputStream()));
			
			String data ="";			
			
			//SSLHandShake
			this.Start_SSL_Handshake();
			
			//Communication
			this.Communication();
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
		this.Close_Server();
	}
	
	//Function write to client with array String
	private void write_to_client(String[] array)
	{
		/*	Example:  String[] example = new String[]{"hello","world","end"};
		 *				this.write_to_client(example);
		 * => write array String example to client
		 */
		for(String a:array)
		{
			try {
				out.write(a);
				out.newLine();
				out.flush();
			} catch (IOException e) {			
				System.out.println("IOException in function write_to_client!");
			}			
		}
	}
	
	//Function write to client
	private void write_to_client(String input)
	{
		 /*	Example:  this.write_to_client("end");
		 * => write String "end" to client
		 */
		try{
			out.write(input); out.newLine(); out.flush();
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
	
	public boolean Send_key_RSA()  {
		try {
			keyrsa = new Model_RSA();
			keyrsa.publickey = keyrsa.getPublicKey();
			keyrsa.privatekey = keyrsa.getPrivateKey();
			String publickey = keyrsa.getPublicKey_ToString();
			System.out.println("Publickey của server: "+keyrsa.publickey+"\n");
			this.write_to_client(publickey);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Lỗi trong việc kết nối SSL với Client");
			return false;
		}
	}
	
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
	
	public void Communication() {
		while(true) {
			try {
				String receive = in.readLine();		
				String decrypt = sessionkey.decrypt_String(receive, sessionkey.mykey);
				decrypt = this.trim_extend(decrypt);
				if(check_input(decrypt)==false)
				{
					this.write_to_client("fail_input");
					continue;
				}
				if(decrypt.equals("bye"))
				{
					break;
				}
				System.out.println("Nhận từ client:"+decrypt);
				this.Get_API_Search_phim(decrypt);
			} catch (IOException e) {
				System.out.println("Client_terminate");
				break;
			}
			
		}
	}

	
	public void Get_API_Search_phim(String phim) {
		ParseJsonFromAPI API = new ParseJsonFromAPI();
		API.searchByName(phim);
		API.getPosterImage(phim);
		API.getReviewOfMovie(phim);
		API.getActorOfMovie(phim);
		API.getKeyOfTrailer(phim);
		//ArrayList<String> temp =API.searchByName(phim); 
		//------------ server hiện
//				for(int i=0; i<API.arraymovie.size();i++)
//				{
//					System.out.println("ID:"+API.arraymovie.get(i).getId());
//					System.out.println("Title:"+API.arraymovie.get(i).getTitle());
//					System.out.println("Vote_av:"+API.arraymovie.get(i).getVote_av());
//					System.out.println("Vote count:"+API.arraymovie.get(i).getVote_count());
//					System.out.println("Poster:"+API.arraymovie.get(i).poster_path_url);
//					System.out.println("key:"+API.arraymovie.get(i).getKeyTrailer());				
//					ArrayList<String> author = API.arraymovie.get(i).getAuthor();
//					if ( author != null) {
//					for(int j=0;j<author.size();j++)
//					{
//						System.out.println("Author:"+author.get(j));
//					}
//					}
//					ArrayList<String> review = API.arraymovie.get(i).getReview();
//					if ( review != null) {
//					for(int j=0;j<review.size();j++)
//					{
//						System.out.println("Review:"+review.get(j));
//					}}
//					ArrayList<String> actor = API.arraymovie.get(i).getCast();
//					if ( actor != null) {
//					for(int j=0;j<actor.size();j++)
//					{
//						System.out.println("Actor:"+actor.get(j));
//					}}
//					ArrayList<String> crew = API.arraymovie.get(i).getCrew();
//					if ( crew != null) {
//					for(int j=0;j<crew.size();j++)
//					{
//						System.out.println("Crew:"+crew.get(j));
//					}
//					}
//					System.out.println("\n\n\tNext movie:");
//				}
		
		//
		for(int i=0;i<API.arraymovie.size();i++)
		{			
			this.write_to_client(API.arraymovie.get(i).getId());
			this.write_to_client(API.arraymovie.get(i).getTitle());
			this.write_to_client(API.arraymovie.get(i).getOverview());
			this.write_to_client(API.arraymovie.get(i).getVote_av());
			this.write_to_client(API.arraymovie.get(i).getVote_count());	
//			if(API.arraymovie.get(i).getPoster_image() != null) {
//				this.write_to_client("null");
//			}
//			else
//				this.write_to_client(API.arraymovie.get(i).encodeImage());
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
			if ( author != null) {
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
			if ( review != null) {
			for(int j=0;j<review.size();j++)
			{
				this.write_to_client(review.get(j));
			}
			this.write_to_client("review");
			}	
			else this.write_to_client("null");
			
			//Gửi cast
			ArrayList<String> casr = API.arraymovie.get(i).getCast();
			if ( casr != null) {
			for(int j=0;j<casr.size();j++)
			{
				this.write_to_client(casr.get(j));
			}
			this.write_to_client("cast");
			}	
			else this.write_to_client("null");
			
			//Gửi crew
			ArrayList<String> crew = API.arraymovie.get(i).getCrew();
			if ( crew != null) {
			for(int j=0;j<crew.size();j++)
			{
				this.write_to_client(crew.get(j));
			}			
			this.write_to_client("crew");
			}	
			else this.write_to_client("null");
			
		}
		this.write_to_client("end");				
	}

	public static void main(String[] args) {
		Controller_Server_SearchPhim a = new Controller_Server_SearchPhim();
		a.Open_Server(6000);		
//		ParseJsonFromAPI a = new ParseJsonFromAPI();
//		a.playTrailerFromBrowser2("ukJ5dMYx2no");
	}

}
