package chat2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String s = ""; //클라이언트의 메세지를 저장
		String name="";// 클라이언트의 이름을 저장
		
		try
		{
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			socket = serverSocket.accept();
			
			//메세지를 보낼 준비
			out = new PrintWriter(socket.getOutputStream(), true);
			//메세지를 읽을(받을)준비
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			/*
			 클라이언트가 서버로 전송하는 최초의 메세지는 "대화명" 이므로
			 메세지를 읽은 후 변수에 저장하고 클라이언트쪽으로 Echo해준다.
			 */
			if(in != null)
			{
				name = in.readLine();//클라이언트의 이름을 읽어서 저장
				System.out.println(name + " 접속");//서버의 콘솔에 출력
				out.println("> " + name + "님이 접속했습니다.");//클라이언트에게 Echo
			}
			
			/*
			 두번째 메세지부터는 실제 대화내용이므로 읽어와서 콘솔에 출력하고
			 동시에 클라이언트 측으로 Echo해준다.
			 */
			while(in != null)
			{
				s = in.readLine();
				if(s==null)
				{
					break;
				}
				System.out.println(name + " ==> " + s);
				out.println(">  "+ name + " ==> " + s);
			}
			System.out.println("Bye...!!!");
		}
		catch(Exception e)
		{
			System.out.println("예외1:" + e);
			//e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
				out.close();
				socket.close();
				serverSocket.close();
			}
			catch(Exception e)
			{
				System.out.println("예외2:" + e);
				//e.printStackTrace();
			}
		}
	}

}
