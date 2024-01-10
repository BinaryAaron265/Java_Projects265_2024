package ChattaApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
  
	  public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	  private Socket socket;
	  private BufferedReader bufferedreader;
	  private BufferedWriter bufferedwriter;
	  private String clientUserName;
	 
	  
	  
	  ClientHandler(Socket socket){
		  
		  try {
		  this.socket =  socket;
		  this.bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		  this.bufferedreader = new  BufferedReader(new InputStreamReader(socket.getInputStream()));
		  this.clientUserName =  bufferedreader.readLine();
		  clientHandlers.add(this);
		  
		  broadcastMessage("Server:" +clientUserName+" has entered the chat");
		  
		  }catch(IOException e) {
			  closeEveryThing(socket,bufferedreader,bufferedwriter);
			  
			  
		  }
		  
		  
	  }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String messageFromClient  ;
		while(socket.isConnected()) {
			
			try {
				messageFromClient = bufferedreader.readLine();
				broadcastMessage(messageFromClient);
				
				
			}catch(IOException e) {
				
				closeEveryThing(socket,bufferedreader,bufferedwriter);
				break;
			}
		}
	}
	private void broadcastMessage(String messagetosend) {
		// TODO Auto-generated method stub
		for(ClientHandler clienthander : clientHandlers) {
			
			try {
				
				if(!clienthander.equals(clientUserName)) {
					
					clienthander.bufferedwriter.write(messagetosend);
					clienthander.bufferedwriter.newLine();
					clienthander.bufferedwriter.flush();
				}
				
			}catch(IOException e) {
				
				closeEveryThing(socket,bufferedreader,bufferedwriter);
			}
		}
		
	}
	
	private void closeEveryThing(Socket socket, BufferedReader bufferedreader, BufferedWriter bufferedwriter) {
		// TODO Auto-generated method stub
		removeClientHandler();
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
	public void removeClientHandler() {
		
		clientHandlers.remove(this);
		broadcastMessage("Server: " +clientUserName+"Has Left Chat ");
	}
	

}
