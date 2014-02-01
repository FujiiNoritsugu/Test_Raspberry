package test.rasp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTest {

	ExecutorService service;
	ClientTest controlThread;
    static ServerSocket serverSoc = null;

	public ServerTest(){
		service = Executors.newFixedThreadPool(10);
		controlThread = new ClientTest();
	}

		public static void main(String[] args)
	    {
		try {

			try {
				serverSoc = new ServerSocket(80);
			} catch (IOException e) {
				e.printStackTrace();
			}

	          System.out.println("Waiting for Connection. ");
	         Socket socket = serverSoc.accept();
	          System.out.println("Connect to " + socket.getInetAddress());

	          BufferedReader reader = new BufferedReader
	            (new InputStreamReader(socket.getInputStream()));

	      while(true){

	          String command = reader.readLine();

	          System.out.println("Message from client :" + command);

	          if(command.matches("end")){
		          reader.close();
		          socket.close();
		          serverSoc.close();
	            System.out.println("Stopped.");
	            break;
	          }
	      }

		}
		catch (IOException e) {
		    e.printStackTrace();
		}

	}

}
