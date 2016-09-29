package com.bit2016.network.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer2 {
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
         InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
         ///원격지(접속한곳)의 인터넷 주소와 포트번호를 SocketAddress에 담아서 돌려준다.
         InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();
         String remoteHostAddress = inetRemoteHostAddress.getHostAddress();
         int remoteHostPort = inetSocketAddress.getPort();
         System.out.println("[server] connected by client[" + remoteHostAddress + ":" + remoteHostPort + "]");

         try {
            // 4. IOStream 받아오기
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            while (true) {
               // 5. 데이터 읽기
               String data = br.readLine();
               if (data == null) {
                  System.out.println("[server] closed by client");
                  break;
               }
               System.out.println("[server] received:" + data);

               // 6. 쓰기
               pw.println(data);

            }
         } catch(SocketException ex){
	        	System.out.println("[server] abnormal close by clinet");
	        } catch (IOException ex) {
            ex.printStackTrace();
         } finally {
            try {
               // 5. 자원정리(소켓 닫기)
               socket.close();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if (serverSocket != null && serverSocket.isClosed() == false) {
            try {
               serverSocket.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   }
}

