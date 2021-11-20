package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Controller_Server_SearchPhim {
	private BufferedReader 	in;				//read from pipe socket
	private BufferedWriter 	out;			//write to pipe
	private ServerSocket 	listen; 		//Socket Communicate
	private Socket			socket_server;	//Socket in server
	
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
			
			//Read from Client
			while (true)
			{
					data = in.readLine(); 
					//Check out connect client
					if(data.equals("bye"))					
					{
						this.write_to_client("bye");	
						break;
					}
					if(!check_input(data))
					{
						this.write_to_client(new String[] {
								"Sai cấu trúc, vui lòng nhập lại",
								"end"
						});
						continue;
					}
					data = this.trim_extend(data);
					//Tự điền thêm vào - có thể viết hàm hoặc ghi trực tiếp trong while connect
			}	
			
			//Close
			Thread.sleep(1000);
			System.out.println("Close Connection");
			this.Close_Server();
			if(data.equals("bye"))
				break;
			} catch (IOException e) {
				//Lỗi khi client ter
				System.out.println("IOException in Openport - Client terminated");		
				break;
			} catch (NullPointerException e) {
				System.out.println("NullPointer in Openport");	
				this.write_to_client("exception");
			} 
				catch (InterruptedException e) {		
				System.out.println("Interrupted in Openport");
				this.write_to_client("exception");
			} 
		} //Loop while
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
	
	public static void main(String[] args) {

	}

}
