package com.bit2016.network.util;

import java.net.InetAddress;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		try {
			
			Scanner scanner = new Scanner(System.in);
			
			while(true){
			System.out.print(">");
			String hostname = scanner.nextLine();
			
			if(hostname.equals("exit")){
				break;
			}

			InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
			
			for(InetAddress inetAddress : inetAddresses){
				System.out.println(hostname + ":" +inetAddress.getHostAddress());
			}
		} 
		}
		catch (java.net.UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
