package jus.poc.prodcons.v2;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {
	private Queue<Message> queue;
	private Semaphore fifoR;
	private Semaphore fifoW;

	ProdConsBuffer(int size) {
		this.queue = new Queue<Message>(size);
		this.fifoR = new Semaphore(1);
		this.fifoW = new Semaphore(1);
	}

	@Override
	public void put(Message msg) throws InterruptedException {
		this.fifoR.acquire();
		
		synchronized (this) {
			while(this.queue.getFreeSize() == 0) {
//				System.out.println("freesize: " + this.queue.getFreeSize());
				wait();
			}
		
			try {
				this.queue.put(msg);
				notifyAll();
				this.fifoR.release();
			} catch (FullQueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Message get() throws InterruptedException {
//		System.out.println("usedSize: " + this.queue.getUsedSize());
		this.fifoW.acquire();
		Message msg;
		synchronized (this) {
			while(this.queue.getUsedSize() == 0) {
//				System.out.println("freesize: " + this.queue.getFreeSize());
				wait();
			}
		
			try {
				msg = this.queue.get();
				notifyAll();
				this.fifoW.release();
				return msg;
			} catch (EmptyQueueException e) {
				// TODO Auto-generated catch block
				msg = null;
				e.printStackTrace();
			}
		}

		
		return msg;
	}

	@Override
	public int nmsg() {
		return this.queue.getUsedSize();
	}

	@Override
	public int totmsg() {
		return this.queue.getTotalCount();
	}

}
