package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Controller_Client_SearchPhim {
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
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
				
			System.out.print("Write to server:");
			//Communicate
			while(true)
			{				
				over = input.readLine();

				//Send to server
				out.write(over);
				out.newLine();
				out.flush();
				
				//Receive from server
				while(true)
				{
					receive = in.readLine();								
					switch(receive) {
						case "end":
							System.out.print("Please input new : ");						
							break;
						case "bye":
							System.out.println("Close connection!");
							break;
						case "exception":
							System.out.println("Server have problem, force close");
							break;
						default:
							System.out.println("Receive from Server: "+receive);
							continue;
						}					
						break;
				}
				if (receive.equals("bye")||receive.equals("exception"))
					break;
			}		
			
			//Close
			input.close();	
			Close_Client();
		} catch (IOException e) {
			System.out.println("IOException!!");
			System.out.println("Close");
		} 
	}
	
	//Function Close Client
	private void Close_Client()
	{
		try {			
			in.close();
			out.close();
			socket.close();
		} catch(Exception e) {
			System.out.println("Catch Exception!! Force Close");
		}
	}
	
	public static void main(String[] args) {

	}

}
