package com.bit2016.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	 private static final int PORT = 5500;

	   public static void main(String[] args) {

	      ServerSocket serverSocket = null;
	      try {
	         // 1. 서버소켓 생성
	         serverSocket = new ServerSocket();

	         // 2. binding(소켓에 소켓주소(IP + port)을 바인딩한다)
	         InetAddress inetAddress = InetAddress.getLocalHost();
	         String hostAddress = inetAddress.getHostAddress();
	         
	         serverSocket.bind(new InetSocketAddress(hostAddress, PORT));  
	         
	         System.out.println("[server] binding " + hostAddress + ":" + PORT);

	         // 3. accept(클라이언트로부터 연결요청을 기다린다.)
	         
	         Socket socket = serverSocket.accept(); // block
	         
	         InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();  
	         ///원격지(접속한곳)의 인터넷 주소와 포트번호를 SocketAddress에 담아서 돌려준다.
	         
	         InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();				//소켓 어드레스 출력
	         String remoteHostAddress = inetRemoteHostAddress.getHostAddress();					//호스트 어드레스 
	         int remoteHostPort = inetSocketAddress.getPort();
	         System.out.println("[server] connected by client[" + remoteHostAddress + " : " + remoteHostPort + "]");

	        
	         try{
	         // 4. IOstream 받아오기
	         InputStream inputStream = socket.getInputStream();
	         OutputStream outputStream = socket.getOutputStream();

	         while(true){
	         // 5. 데이터 읽기
	         byte[] buffer = new byte[256];										//통신을 위한 byte형으로 변경
	         int readByteCount = inputStream.read(buffer);    //block
	         if(readByteCount == -1){
	        	 //정상종료(remote socket close() 불러서 정상적으로 소켓을 닫았다.
	        	 System.out.println("[server] closed by clinet");
	        	 break ; 
	         }
	         
	         String data = new String(buffer, 0 , readByteCount, "UTF-8");  	//byte를 문자열로 받기
	         
	         System.out.println("[server] received : " + data);

	         // 6. 쓰기
	         outputStream.write(data.getBytes("UTF-8") );
	         }
	         }
	         catch(IOException ex){
	        	 ex.printStackTrace();
	         }
	         finally{
	        	 try{
	        	 socket.close();
	        	 } catch(IOException ex){
		        	 ex.printStackTrace();
		         }
	         }
	         // 5. 자원정리(소켓 닫기)
	         socket.close();

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
