package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {

	public static final int PORT = 9000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(PORT);
				// 2.데이터 수신 
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				
				System.out.println("[server] 대기중");
				socket.receive(receivePacket); 			//blocking
				
				System.out.println("[server] 수신");
				String message = new String(receivePacket.getData(),0 ,receivePacket.getLength(),"UTF-8");
				
				System.out.println("[server] received: " +message);
				
				// 3. 데이터 송신
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = format.format(new Date());

				byte[] sendData = data.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, 0 ,sendData.length, receivePacket.getAddress(), receivePacket.getPort());
				socket.send(sendPacket);
				
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
