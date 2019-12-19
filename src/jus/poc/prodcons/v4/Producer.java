package jus.poc.prodcons.v4;

import java.util.Random;

public class Producer extends Actor {
	private int id;
	private int prodTime;
	private int nbMsg;
	private int nbExpl;

	public Producer(int prodTime, int nbMsg, int id) {
		super();
		this.prodTime = prodTime;
		this.nbMsg = nbMsg;
		this.nbExpl = 1;
		this.id = id;
	}
	
	public Producer(int prodTime, int nbMsg, int nbExpl, int id) {
		super();
		this.prodTime = prodTime;
		this.nbMsg = nbMsg;
		this.nbExpl = nbExpl;
		this.id = id;
	}

	public int getNbMsg() {
		return this.nbMsg * this.nbExpl;
	}

	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < this.nbMsg; ++i) {
			try {
				Message msg = new Message(" " + this.id + ": msg n°" + i + " of " + nbMsg + " in " + this.nbExpl);
				this.buffer.put(msg, this.nbExpl);
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
		System.err.println("PRODUCER FINISHED " + this.id);
	}

}
