package jus.poc.prodcons.v2;

import java.util.Random;

public class Consumer extends Actor {
	private int consTime;
	private boolean stopped;

	public Consumer(int consTime) {
		super();
		this.consTime = consTime;
		this.stopped = false;
	}
	
	public void stop() {
		this.stopped = true;
	}

	@Override
	public void run() {
		Random random = new Random();
		while (!this.stopped) {
			try {
				
				Message msg = this.buffer.get();
				System.out.println("Consumer:" + msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(random.nextInt(this.consTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
