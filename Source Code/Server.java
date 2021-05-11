import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	private ServerSocket server;
	private static Hashtable<String,Streams> clients = new Hashtable<String,Streams>();

	public static Hashtable<String,Streams> getClients() {
		return Server.clients;
	}

	Server(ServerSocket server) {
		this.server = server;
		try {
			System.out.println("Server Running");
			storePort();			
			startListening();
		} catch(IOException ioe) {
			System.out.println("Error establishing connection: " + ioe.getMessage());
		}
	}

	private void storePort() throws FileNotFoundException, IOException {
		File fl = new File("port.txt");
		FileOutputStream f = new FileOutputStream(fl,false);
		f.write(String.valueOf(server.getLocalPort()).getBytes());
		f.close();
	}

	private void startListening() throws IOException {
		while(true) {			
			Socket socket = server.accept();
			new Thread(new ClientHandler(socket)).start();
			System.out.println("Client connected at: " + socket.getRemoteSocketAddress());
		}
	}
}