package ChattaApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	 private Socket socket;
		private BufferedReader bufferedreader;
		  private BufferedWriter bufferedwriter;
		  private String userName;
		
	 public Client(Socket socket, String userName) {
		super();
		try {
		this.socket = socket;
		 this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		  this.bufferedreader = new  BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.userName = userName;
		
		}catch(IOException e) {
			
			closeEveryThing(socket,bufferedreader,bufferedwriter);
		}
	}
	 
	 
	 public void sendMessage() {
		 
		 try {
			 bufferedwriter.write(userName);
			 bufferedwriter.newLine();
			 bufferedwriter.flush();
			 Scanner sc  =  new Scanner(System.in);
			 while(socket.isConnected()) {
				 String messageTosend  =  sc.nextLine();
				 bufferedwriter.write(userName + ": "+messageTosend);
				 bufferedwriter.newLine();
				 bufferedwriter.flush();
				 
			 }
			 
			 
		 }catch(Exception e) {
			 closeEveryThing(socket,bufferedreader,bufferedwriter);
			 
		 }
	 }
	 
	 public void listenForMessage() {
		 
		 new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String messageFromGroupChat;
				
				while(socket.isConnected()) {
					try {
						messageFromGroupChat = bufferedreader.readLine();
						System.out.println(messageFromGroupChat);
						
						
					}catch(IOException e) {
						 closeEveryThing(socket,bufferedreader,bufferedwriter);
						
					}
					
				}
			}

	
			
			 
			 
		 }).start();
	 }


		private void closeEveryThing(Socket socket, BufferedReader bufferedreader, BufferedWriter bufferedwriter) {
			// TODO Auto-generated method stub
			try {
				
				if(bufferedreader!=null) {
					bufferedreader.close();
				}
				if(bufferedwriter!=null) {
					bufferedwriter.close();
				}
				if(socket!=null) {
					socket.close();
				}
			}catch(IOException e) {
				
				e.printStackTrace();
			}
		}

		
	 public static void main(String[] args) throws IOException {
		 
		 Scanner sc  = new Scanner(System.in);
		 System.out.println("Enter Username : " );
		 String username =   sc.nextLine();
		 Socket socket =  new Socket("localhost",1234);
		 
		 Client client  =  new Client(socket,username);
		 client.listenForMessage();
		 client.sendMessage();
		 
	 }
	 
}
