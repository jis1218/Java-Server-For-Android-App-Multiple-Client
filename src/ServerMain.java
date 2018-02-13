import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
	
	public static ArrayList<Socket> socketList = new ArrayList<>();
	
	public static void main(String args[]){		
		ServerBody serverBody = new ServerBody();
		serverBody.start();		
	}
}
