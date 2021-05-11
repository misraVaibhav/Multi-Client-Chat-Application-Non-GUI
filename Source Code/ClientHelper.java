import java.net.*;
import java.io.*;

public class ClientHelper implements Runnable {

	private ObjectInputStream in;

	ClientHelper(ObjectInputStream in) {
		this.in = in;
	}

	public void run() {
		try {
			while(true) {
				Message message = (Message)in.readObject();
				System.out.println(message.getMessage());
			}
		} catch (SocketException se) {
			System.out.println("Error establishing connection: " + se.getMessage());
		} catch (IOException ioe) {
			System.out.println("Error establishing connection: " + ioe.getMessage());
		} catch(ClassNotFoundException cnfe) {
			System.out.println("Error establishing connection: " + cnfe.getMessage());
		}	
	}
}