package com.bit2016.network.thread;

public class UppercaseAlphabetRunableImpl extends UppercaseAlphabet implements Runnable{

	@Override
	public void run() {
		print();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
