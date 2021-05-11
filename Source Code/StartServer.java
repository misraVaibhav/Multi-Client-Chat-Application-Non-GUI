import java.net.*;
import java.io.*;

public class StartServer {		
	public static void main(String[] args) {
		System.out.println("Starting server...");
		try {
			new Server(new ServerSocket(0));
		} catch(IOException ioe) {
			System.out.println("Error establishing connection: " + ioe.getMessage());
		}		
	}
}