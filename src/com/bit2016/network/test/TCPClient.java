package com.bit2016.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {

	private static final String SERVER_IP = "192.168.1.17";
	private static final int SERVER_PORT = 5500;
	
	public static void main(String[] args) {
		Socket socket = null;
		try{
		// 1. socket 생성
		socket = new Socket();
		
		//1.1 socket buffer size 확인
		int receiveBufferSize = socket.getReceiveBufferSize();
		int sendBufferSize = socket.getSendBufferSize();
		
		System.out.println(receiveBufferSize + ":" + sendBufferSize);
		
		// 1-2 socket buffer size 변경
		
		socket.setReceiveBufferSize(10*1024);
		socket.setSendBufferSize(10 * 1024);
		
		int receiveBufferSize2 = socket.getReceiveBufferSize();
		int sendBufferSize2 = socket.getSendBufferSize();
		
		System.out.println(receiveBufferSize2 + ":" + sendBufferSize2);
		
		// 1.3 SO_NODELAY( Nagle Algorithm off)
		socket.setTcpNoDelay(true);
		
		// 1.4 SO_TIMEOUT
		socket.setSoTimeout(1);
		
		// 2. server 연결
		socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT ));
		System.out.println("[client] connected");
		
		// 3. IOStream 받아오기 
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		String data = "hello\n";
		os.write(data.getBytes("UTF-8"));
		
		byte[] buffer = new byte[256];
		int readByteCount = is.read(buffer);
		if(readByteCount == -1){
			System.out.println("[server] closed by client");
			return;
		}
		
		data = new String(buffer, 0, readByteCount, "UTF-8");
		System.out.print("[client] received : " + data);
		
		}catch (SocketTimeoutException ex){
			System.out.println("[client] time out :" + ex );
		} 
		catch (IOException ex){
			ex.printStackTrace();
		}
		finally {
			try{
				if (socket != null & socket.isClosed() == false){
					socket.close();
				}
			}catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
