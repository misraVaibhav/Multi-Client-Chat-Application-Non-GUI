import java.io.*;

public class Message implements Serializable{

	private String message;
	private String receiver;
	private Boolean sendPrivate;

	Message(String message) {
		this.message = message;
		this.sendPrivate = false;
	}

	Message(String message, String receiver) {
		this.message = message;
		this.receiver = receiver;
		this.sendPrivate = true;
	}

	public String getMessage() {
		return this.message;
	}

	public Boolean getSendPrivate() {
		return this.sendPrivate;
	}

	public String getReceiver() {
		return this.receiver;
	}
}