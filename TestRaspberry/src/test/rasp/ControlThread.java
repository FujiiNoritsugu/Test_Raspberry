package test.rasp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;

public class ControlThread implements Runnable {


	private static Socket socket = null;
	private static PrintWriter writer = null;

    public static void main(String args[]) throws InterruptedException {

        // !! ATTENTION !!
        // By default, the serial port is configured as a console port
        // for interacting with the Linux OS shell.  If you want to use
        // the serial port in a software program, you must disable the
        // OS from using this port.  Please see this blog article by
        // Clayton Smith for step-by-step instructions on how to disable
        // the OS console for this port:
        // http://www.irrational.net/2012/04/19/using-the-raspberry-pis-serial-port/

        System.out.println("<--Pi4J--> Serial Communication Example ... started.");
        System.out.println(" ... connect using settings: 9600, N, 8, 1.");
        System.out.println(" ... data received on serial port should be displayed below.");

		try {
			socket = new Socket("54.238.172.248",80);
			if(socket.isConnected()){
				writer = new PrintWriter(socket.getOutputStream());
				writer.print("test data");
				writer.close();
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        // create and register the serial data listener
        serial.addListener(new SerialDataListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                // print out the data received to the console
            	String data = event.getData();
                System.out.print(event.getData());
                writer.print(data);
            }
        });

        try {
            // open the default serial port provided on the GPIO header
            serial.open(Serial.DEFAULT_COM_PORT, 9600);

            // continuous loop to keep the program running until the user terminates the program
            for (;;) {
                try {
                    // write a formatted string to the serial transmit buffer
                    serial.write("CURRENT TIME: %s", new Date().toString());

                    // write a individual bytes to the serial transmit buffer
                    serial.write((byte) 13);
                    serial.write((byte) 10);

                    // write a simple string to the serial transmit buffer
                    serial.write("Second Line");

                    // write a individual characters to the serial transmit buffer
                    serial.write('\r');
                    serial.write('\n');

                    // write a string terminating with CR+LF to the serial transmit buffer
                    serial.writeln("Third Line");
                }
                catch(IllegalStateException ex){
                    ex.printStackTrace();
                }

                // wait 1 second before continuing
                Thread.sleep(1000);
            }

        }
        catch(SerialPortException ex) {
            System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
    }

}
