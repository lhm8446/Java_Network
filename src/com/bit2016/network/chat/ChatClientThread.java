package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class ChatClientThread extends Thread {
	
	private BufferedReader br;

	public ChatClientThread(BufferedReader br){
		this.br=br;
	}
	@Override
	public void run() {
		
		while(true){
		try {
			String data = br.readLine();
			
			if(data==null){
				break;
			}
			
			System.out.println(data);
			
		}catch (SocketException e) {
			System.exit(0);
		}catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
}
