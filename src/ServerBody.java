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
				System.out.println("���� ���� �����");
				Socket clientSocket = serverSocket.accept();
				ServerMain.socketList.add(clientSocket);
				System.out.println(ServerMain.socketList.size()+ "��° ������ ���� ���� �Ǿ����ϴ�");
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
