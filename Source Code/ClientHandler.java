import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String name;

	ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (SocketException se) {
			System.out.println("Error establishing connection: " + se.getMessage());
		} catch (IOException ioe) {
			System.out.println("Error establishing connection: " + ioe.getMessage());
		}
	}

	private void sendAll(String str) {
		Server.getClients().forEach((k,v) -> {
			try {
				ObjectOutputStream out_ = v.getOS();
				out_.writeObject(new Message(str));
				out_.flush();
			} catch (IOException ioe) {
				System.out.println("Error establishing connection: " + ioe.getMessage());
			}
		});
	}

	private void sendPrivate(String receiver, String message) throws IOException {
		if(!Server.getClients().containsKey(receiver)) return;
		ObjectOutputStream out = Server.getClients().get(receiver).getOS();
		out.writeObject(new Message(message, receiver));
		out.flush();
	}

	public void run() {
		try {
			Message message = (Message)in.readObject();
			String str = message.getMessage();
			this.name = str;
			Server.getClients().put(name,new Streams(in,out));
			System.out.println(str + " joined!");
			while(true) {
				message = (Message)in.readObject();
				if(message.getSendPrivate()) {
					sendPrivate(message.getReceiver(), message.getMessage());
				} else {
					str = message.getMessage();
					sendAll(name + " : " + str);
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error establishing connection: " + cnfe.getMessage());
		} catch (SocketException se) {
			System.out.println("Error establishing connection: " + se.getMessage());
		} catch (IOException ioe) {
			System.out.println("Error establishing connection: " + ioe.getMessage());
		}
	}
}