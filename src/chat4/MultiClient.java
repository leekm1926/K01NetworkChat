package chat4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {

	public static void main(String[] args) {

		//클라이언트의 접속자명을 입력
		System.out.print("이름을 입력하세요:");
		Scanner scanner = new Scanner(System.in);
		String s_name = scanner.nextLine();
		
		PrintWriter out = null;
		//서버의 메시지를 읽어오는 기능을 Receiver클래스로 옮김
		//BufferedReader in = null;
		
		try
		{
			/*
			 C:\bin>java chat3.MultiClient 접속할IP주소
			 	=> 위와같이 접속하면 로컬이 아닌 내가 원하는 서버로
			 	접속할 수 있다.
			 */
			String ServerIP = "localhost";
			if(args.length > 0)
			{
				ServerIP = args[0];
			}
			
			Socket socket = new Socket(ServerIP, 9999);
			System.out.println("서버와 연결되었습니다...");
			
			//서버에서 보내는 메세지를 읽어올 Receiver쓰레드 객체생성 및 시작
			Thread receiver = new Receiver(socket);
			//setDaemon(true) 선언이 없으므로 독립쓰레드로 생성된다.
			receiver.start();
			
			out = new PrintWriter(socket.getOutputStream(), true);
			//접속자의 "대화명"을 서버측으로 전송한다.
			out.println(s_name);
			
			//이름전송 이후에는 메세지를 지속적으로 전송함.
			while(out != null)
			{
				try
				{
					
					
					//클라이언트는 내용을 입력한 후 서버로 전송한다.
					String s2 = scanner.nextLine();
					if(s2.equals("q") || s2.equals("Q"))
					{
						//만약 입력값이 q(Q)라면 while 루프를 탈출한다.
						break;
					}
					else
					{
						//q가 아니라면 서버로 입력한 내용을 전송한다. 
						out.println(s2);
					}
				}
				catch(Exception e)
				{
					System.out.println("예외:" + e);
				}
			}
			//클라이언트가 q를 입력하면 소켓과 스트림을 모두 자원해제한다.
			
			//while루프를 탈출하면 소켓을 즉시 종료한다.
			out.close();
			socket.close();
		}
		catch(Exception e)
		{
			System.out.println("예외발생[MultiClient]" +e);
		}
	}
}
