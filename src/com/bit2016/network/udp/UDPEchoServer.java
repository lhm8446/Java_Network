package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoServer {
	
	public static final int PORT = 5555;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		
		DatagramSocket socket = null;
		
		try {
			// 1.소켓생성
			socket = new DatagramSocket(PORT);  // 포트 번호 입력
			
			while(true){
				
			// 2.데이터 수신 
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			
			System.out.println("[server] 대기중");
			socket.receive(receivePacket); 			//blocking
			
			System.out.println("[server] 수신");
			String message = new String(receivePacket.getData(),0 ,receivePacket.getLength(),"UTF-8");
			
			System.out.println("[server] received: " +message);
			
			// 3. 데이터 송신
			byte[] sendData = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, receivePacket.getAddress(), receivePacket.getPort());
			socket.send(sendPacket);
			
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
	}
}
