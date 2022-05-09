package co.so.menu;

public class App {
    public static void main( String[] args ){
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
						Thread.sleep(10000);
						multiStock.run();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            	}
            }
        };
        
        Thread subTread1 = new Thread(task);
        Thread subTread2 = new Thread(task2);
        
        subTread1.start();
        subTread2.start();
    }
}
