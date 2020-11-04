package chat4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

//서버가 보내는 Echo메세지를 읽어오는 쓰레드 클래스
public class Receiver extends Thread {

	Socket socket;
	BufferedReader in = null;
	
	//Client가 접속시 생성한 Socket객체를 생성자에서 매개변수로 받음.
	public Receiver(Socket socket)
	{
		this.socket = socket;
		
		/*
		 Socket객체를 기반으로 input스트림을 생성한다.
		 */
		try
		{
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		}
		catch(Exception e)
		{
			System.out.println("예외1:" + e);
		}
	}
	
	/*
	 	Thread에서 main() 의 역할을 하는 메소드로
	 	직접 호출하면 안되고, 반드시 start()를 통해 간접적으로
	 	호출해야 쓰레드가 생성된다.
	 */
	@Override
	public void run() {
		//스트림을 통해 서버가 보낸 내용을 라인단위로 읽어온다.
		while( in != null)
		{
			try
			{
				System.out.println("Thread Receive : " + in.readLine());
			}
			catch(SocketException e)
			{
				/*
				 클라이언트가 q를 입력하여 접속을 종료하면 무한루프가 발생되므로
				 탈출할 수 있도록 별도의 catch블럭을 추가하고 break를 걸어준다.
				 */
				System.out.println("SocketException발생됨. 루프탈출.");
				break;
			}
			catch(Exception e)
			{
				System.out.println("예외2:" + e);
			}
		}
		try
		{
			in.close();
		}
		catch(Exception e)
		{
			System.out.println("예외3:" + e);
		}
	}
}
