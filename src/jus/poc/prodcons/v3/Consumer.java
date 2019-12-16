package jus.poc.prodcons.v3;

import java.util.Random;

public class Consumer extends Actor {
	private int consTime;
	private int nbMsg;
	private boolean stopped;

	public Consumer(int consTime, int nbMsg) {
		super();
		this.consTime = consTime;
		this.nbMsg = nbMsg;
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
				Message[] msgs = this.buffer.get(this.nbMsg);
				for (Message msg: msgs)
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
