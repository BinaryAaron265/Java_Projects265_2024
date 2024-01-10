package ChattaApp;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket  serversocket;
	
	
	 Server(ServerSocket serversocket){
		 
		 this.serversocket =  serversocket;
	 }
	 
	 
	 
	 
	 public void starSever() {
		 try {
			 
			 while(!serversocket.isClosed()) {
				 
				 Socket  socket  =  serversocket.accept();
				 System.out.println("New Client Has Connected");
				 ClientHandler clienthandler  =  new ClientHandler(socket);
				 Thread thread  = new Thread(clienthandler);
				 
				 thread.start();
			 }
			 
		 }catch(Exception e) {
			 
			 e.printStackTrace();
		 }
		 
		 
	 }
	 
	 
	 public void closeServer() {
		 try {
			 
			 if(serversocket!=null) {
				 
				 serversocket.close(); 
			 }
			 
		 }catch(Exception e ) {
			 
			 e.printStackTrace();
		 }
		 
	 }
	 
	 
	 public static void main(String[] args)  throws IOException{
		 
		 ServerSocket serversocket  =  new ServerSocket(1234);
		 Server server =  new Server(serversocket);
		 server.starSever();
		 
		 
	 }

}
