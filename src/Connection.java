import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
	Socket clientSocket = null;
	BufferedReader bufferedReader = null;
	OutputStream outputStream = null;
	ArrayList<Socket> socketList = null;

	Connection(Socket clientSocket){
		this.clientSocket = clientSocket;
		try {
			bufferedReader = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try{
			while(true){
				System.out.println("채팅 대기중");
				String str = bufferedReader.readLine();
				System.out.println(str);
				if("exit".equals(str)){
					break;
				}
				str += "\r\n";
				
				for(Socket socket : ServerMain.socketList){
					outputStream = socket.getOutputStream();
					outputStream.write(str.getBytes());
					outputStream.flush();
				}
				
				
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
