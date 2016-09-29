package com.bit2016.network.echo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
        try{
        InetSocketAddress isa = (InetSocketAddress)socket.getRemoteSocketAddress();
        System.out.println("[server3" + getId() + "] connected by client[" + isa.getAddress().getHostAddress() + ":" + isa.getPort() + "]");
       
        
        // 4. IOStream 받아오기
       	 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
       	 //읽기  										// 문자열로 변환
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
       	 // 쓰기, 클라이언트로   // 개행을위해			// 문자열로 변환
          
            while (true) {
               // 5. 데이터 읽기
               String data = br.readLine();			// block
               if (data == null) {
                  System.out.println("closed by client");
                  break;
               }
               System.out.println("received:" + data);

               // 6. 쓰기
               pw.println(data);
            }
        }
        catch(SocketException ex){
       	 System.out.println("[server] abnormal closed by client");
        } catch(IOException ex){
          	 ex.printStackTrace();
           }
        finally{
       	 try{
       		 if(socket != null && socket.isClosed() == false){
       	 socket.close();
       		 }
       	 } catch(IOException ex){
	        	 ex.printStackTrace();
	         }
        }
	}
}
