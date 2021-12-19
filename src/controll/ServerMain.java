package controll;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerMain implements Runnable{
	private Thread thread;
	private String namethread;
	private BufferedReader 	in;
	private BufferedWriter 	out;
	private ServerSocket 	listen;
	private Socket			socket_server;
	private Controller_Server_SearchPhim[]       vm = new Controller_Server_SearchPhim[3];
	private int 			count_online=0;	
	
	
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
	
	public void Open_Server_main(int port)
	{
		try {						
			listen = new ServerSocket(port);			
			//Read from Client
			while (true)
			{
				System.out.println("Server main open port - waiting for accept");
				socket_server = listen.accept();			
				vm[this.count_online] = new Controller_Server_SearchPhim();
				vm[this.count_online].setNameThread(String.valueOf(this.count_online));
				vm[this.count_online].setSocket(socket_server);
				vm[this.count_online].start();
				this.count_online++;				
			}
		
		} catch (IOException e) {
			System.out.println("IOException in Openport");			
		} catch (NullPointerException e) {
			System.out.println("NullPointer in Openport");	
		}	
	}	
	
	private void write_to_client(String[] array)
	{
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
	
	private void write_to_client(String input)
	{
		try{
			out.write(input); out.newLine(); out.flush();
		} catch (IOException e) {
			System.out.println("IOException in function write_to_client");
		}
	}

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
	
	public boolean check_input(String input)
	{
		//Check by regex
		String regex = "^[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}$";
		boolean result_a = input.matches(regex);
		if ( !result_a ) return false;
		//Check by token
		StringTokenizer token = new StringTokenizer(input,".",false);
		while(token.hasMoreTokens())
		{
			String temp = token.nextToken();
			int convert = Integer.parseInt(temp);
			if( convert > 255 )
				return false;
		}
		return true;
	}
	
	
	public static void main(String[] args)
	{
		ServerMain a = new ServerMain();
		a.setNameThread("main");
		a.start();
	}

	@Override
	public void run() {
			this.Open_Server_main(6000);
	}
	
	public void setNameThread(String name)
	{
		this.namethread = name;
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
