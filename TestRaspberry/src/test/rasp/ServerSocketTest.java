package test.rasp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketTest {

	ExecutorService service;
	ControlThread controlThread;
    static ServerSocket serverSoc = null;

	public ServerSocketTest(){
		service = Executors.newFixedThreadPool(10);
		controlThread = new ControlThread();
	}

		public static void main(String[] args)
	    {
		try {
	        // create an instance of the serial communications class
//	        final Serial serial = SerialFactory.createInstance();
//
//	        // create and register the serial data listener
//	        serial.addListener(new SerialDataListener() {
//	            @Override
//	            public void dataReceived(SerialDataEvent event) {
//	                // print out the data received to the console
//	                System.out.print(event.getData());
//	            }
//	        });

			try {
				serverSoc = new ServerSocket(80);
//	            serial.open(Serial.DEFAULT_COM_PORT, 38400);
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
	          //String [] commandArray = command.split(":");

	          //serial.writeln(commandArray[0], commandArray[1]);

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

	private static void controlFinger(String command) throws IOException{


	}
}
