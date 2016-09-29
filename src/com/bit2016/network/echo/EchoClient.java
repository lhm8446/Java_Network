package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "192.168.1.12";
	private static final int SERVER_PORT = 5001;

	public static void main(String[] args) {
		Scanner scanner =null;
		Socket socket = null;
		try {
			// 1. socket 생성
			socket = new Socket();
			scanner = new Scanner(System.in);

			// 2. server 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] connected");

			// 3. IOStream 받아오기

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			// 서버로 부터 읽기
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			// 쓰기 , 서버로 전송
			
			
			while (true) {

				System.out.print(">>");
				String write = scanner.nextLine();

				if ("quit".equals(write)) {
					break;
				}
				pw.println(write);

				String data = br.readLine();
				if (data == null) {
					System.out.println("[server] closed by client");
					break;
				}
				System.out.println(">>" + data);
			}
			scanner.close();

		}  catch (SocketException ex) {
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
