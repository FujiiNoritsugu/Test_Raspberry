package test.rasp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketTest {
	public static void main(String [] arg){
		Socket socket;
		try {
			socket = new Socket("54.238.172.248",80);
			if(socket.isConnected()){
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.print("test data");
				writer.close();
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
