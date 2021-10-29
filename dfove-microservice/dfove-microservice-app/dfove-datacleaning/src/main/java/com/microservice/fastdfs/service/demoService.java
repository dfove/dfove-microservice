
package com.microservice.fastdfs.service;


public class demoService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("4111111111111111");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(i);
				}
			}
		}.start();;
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
