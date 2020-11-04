package chat6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {

	public static void main(String[] args) {

		System.out.print("이름을 입력하세요:");
		Scanner scanner = new Scanner(System.in);
		String s_name = scanner.nextLine();
		
		//PrintWriter out = null;
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
			
			Thread sender = new Sender(socket, s_name);
			sender.start();
		}
		catch(Exception e)
		{
			System.out.println("예외발생[MultiClient]" + e);
		}
			
	}
}