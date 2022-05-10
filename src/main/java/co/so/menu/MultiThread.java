package co.so.menu;

import co.so.stock.app.Run;

public class MultiThread {
	public void run() {
		multiThread();
	}
	
	private void multiThread() {
		MultiStock multiStock = new MultiStock();
//    	Run run = new Run();
//    	run.run();
    	
    	Runnable task = new Runnable() {
            public void run() {
            	MainMenu menu = new MainMenu();
                menu.run();
            }
        };
        
        Runnable task2 = new Runnable() {
            public void run() {
            	while(true) {
            		try {
            			Thread.sleep(10000); // 10초
            			multiStock.run();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            	}
            }
        };

        Runnable task3 = new Runnable() {
        	public void run() {
        		while(true) {
        			try {
        				Thread.sleep(600000); // 10분
        				multiStock.init();
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
        		}
        	}
        };
        
        Thread subTread1 = new Thread(task);
        Thread subTread2 = new Thread(task2);
        Thread subTread3 = new Thread(task3);
        
        subTread1.start();
        subTread2.start();
        subTread3.start();
	}
	
}
