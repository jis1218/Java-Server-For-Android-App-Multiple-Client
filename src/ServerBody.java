import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBody extends Thread {
	
	private static final int PORT_NUM = 9090;
	
	@Override
	public void run() {
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(PORT_NUM);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true){
			
			try {
				System.out.println("서버 연결 대기중");
				Socket clientSocket = serverSocket.accept();
				ServerMain.socketList.add(clientSocket);
				System.out.println(ServerMain.socketList.size()+ "번째 소켓이 서버 연결 되었습니다");
				Connection con = new Connection(clientSocket);
				con.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
					
			}
			
		}
		
		

	}
}
