import java.io.*;
import java.net.*; 	

public class Streams {

	private ObjectInputStream in;
	private ObjectOutputStream out;

	Streams (ObjectInputStream in, ObjectOutputStream out) {
		this.out = out;
		this.in = in;
	}

	public ObjectInputStream getIS() {
		return this.in;
	}

	public ObjectOutputStream getOS() {
		return this.out;
	}
}