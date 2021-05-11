import java.net.*;
import java.io.*;

public class Client {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String name;

	Client() {		
		try {
			startConnection();
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			startChat();
		} catch(IOException ioe) {
			System.out.println("Error establishing connection: " + ioe.getMessage());
		}		
	}

	private void startConnection() throws UnknownHostException, IOException {
		System.out.println("Connecting to server...");
		FileReader fr = new FileReader(new File("port.txt"));
		BufferedReader br = new BufferedReader(fr);
		socket = new Socket("localhost",Integer.parseInt(br.readLine()));
		fr.close();
		System.out.println("Connected");
	}

	private void startChat() throws IOException {	
		System.out.println("Enter your Name to enter the chat: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		this.name = str;
		out.writeObject(new Message(str));
		out.flush();
		new Thread(new ClientHelper(in)).start();
		while(true) {
			str = br.readLine();
			Message message;
			if(str.startsWith("private : ")) {
				String st = str.substring(10);
				String[] parts = st.split("-");
				String receiver = parts[0];
				String message_ = parts[1];
				message = new Message(name + " : " + message_, receiver);
			} else {
				message = new Message(str);
			}
			out.writeObject(message);
		} 		
	}
}