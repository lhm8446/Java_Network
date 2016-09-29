package com.bit2016.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	 private static final int PORT = 5001;

	   public static void main(String[] args) {

	      ServerSocket serverSocket = null;
	      try {
	         // 1. 서버소켓 생성
	         serverSocket = new ServerSocket();

	         // 2. binding(소켓에 소켓주소(IP + port)을 바인딩한다)
	         InetAddress inetAddress = InetAddress.getLocalHost();
	         String hostAddress = inetAddress.getHostAddress();
	         
	         serverSocket.bind(new InetSocketAddress(hostAddress, PORT));   	// IP , 포트 넘버 붙이기 
	         
	         System.out.println("[server] binding " + hostAddress + ":" + PORT);

	         while(true){
	        	// 3. accept(클라이언트로부터 연결요청을 기다린다.)
	         Socket socket = serverSocket.accept(); // block    
	         
	         Thread thread = new EchoServerReceiveThread(socket);
	         thread.start();
	         
	         }
//			 InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();  
	         ///원격지(접속한곳)의 인터넷 주소와 포트번호를 SocketAddress에 담아서 돌려준다.
//	         
//	         InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();				//소켓 어드레스 출력
//	         String remoteHostAddress = inetRemoteHostAddress.getHostAddress();					//호스트 어드레스 
//	         int remoteHostPort = inetSocketAddress.getPort();
//	         System.out.println("[server] connected by client[" + remoteHostAddress + " : " + remoteHostPort + "]");

	      }  catch(SocketException ex){
	        	System.out.println("[server] abnormal close by clinet");
	        }catch (IOException e) {
	         e.printStackTrace();
	      } finally {
	         if (serverSocket != null && serverSocket.isClosed() == false) {
	            try {
	               serverSocket.close();
	            } catch (IOException e) {
	               e.printStackTrace();
	            }
	         }
	      }
	   }
}
