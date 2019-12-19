package jus.poc.prodcons.v3;

import java.util.Random;

public class Producer extends Actor {	
	private int prodTime;
	private int nbMsg;

	public Producer(int prodTime, int nbMsg) {
		super();
		this.prodTime = prodTime;
		this.nbMsg = nbMsg;
	}
	
	public int getNbMsg() {
		return this.nbMsg;
	}

	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < this.nbMsg; ++i) {
			try {
				Message msg = new Message("msg n°" + i + " of " + nbMsg);
				this.buffer.put(msg);
				System.out.println("Producer:" + msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(random.nextInt(this.prodTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.err.println("PRODUCER FINISHED");
	}
}
