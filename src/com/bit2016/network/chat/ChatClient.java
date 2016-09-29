package com.bit2016.network.chat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
	
	private static final String SERVER_IP = "192.168.1.17";
	private static final int SERVER_PORT = 9090;
	
	public static void main(String[] args){
		
		Socket socket = null;
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			
			socket = new Socket();
			
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			// 서버로 부터 읽기
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			// 쓰기 , 서버로 전송

			System.out.print("닉네임>>");
			String name = scanner.nextLine();
			pw.println("Join :" + name);
			
			while (true) {

				System.out.print(">>");
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					break;
				}
				pw.println(input);

				String data = br.readLine();
				if (data == null) {
					System.out.println("[server] closed by client");
					break;
				}
				System.out.println(">>" + data);
			}
			scanner.close();

			
		} catch (SocketException ex) {
			System.out.println("abnormal closed server");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (socket != null & socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
