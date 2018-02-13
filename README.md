##### 1대 다 통신

##### 아래와 같이 코딩하여 server가 여러개의 client를 받을 수 있게끔 하였다.새로운 socket이 추가될 때마다 Connection 객체가 추가되게 하였고 이 connection 객체를 ArrayList 안에 넣어 채팅을 할 때 list 안에 있는 모든 소켓에 뿌려줄 수 있도록 하였다. serverSocket은 계속 while문 안에서 돌면서 client를 받는다. (오류처리 하나도 안되어 있음)
```java
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
```
```java
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
```


##### 네이버 지식인 퍼옴
##### 일단, 통신에서 소켓은 1:1로 연결되는 개념입니다.
##### 즉, 하나의 서버소켓에는 하나의 소켓만 연결할 수 있습니다.
 
##### 따라서, 하나의 연결이 추가될때마다 새로운 서버소켓을 생성할 필요가 있습니다.
##### 새롭게 생성되는 서버소켓들은 해쉬테이블로 관리하면 좋을것입니다.
 
##### 해쉬테이블은 가변형 데이터가 저장되는 컬렉션입니다.
##### 벡터와의 차이점은, 하나의 데이터마다 고유한 이름을 가진다는 점이죠.
 
##### 이러한점을 이용해, 접속한 사용자의 아이디로 구분하여 각각의 소켓을 따로 맺습니다.
##### 메세지를 보낼때는, 사용자의 아이디에 해당하는 서버소켓을 해쉬테이블에서 꺼내 사용하면 되죠.
 
##### 전체메세지를 보낼때는 해쉬테이블 전체를꺼내 루프를돌면서 보내면 되고,
##### 연결을 끊을때도 해당 사용자의 아이디에 해당하는 소켓을 찾아서 연결해제 합니다.

##### 읽을만한 사이트 https://stackoverflow.com/questions/11129212/tcp-can-two-different-sockets-share-a-port