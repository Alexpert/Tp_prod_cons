package jus.poc.prodcons.v3;

import java.util.ArrayList;
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
				System.out.println("freesize: " + this.queue.getFreeSize());
				wait();
			}
		
			try {
				this.queue.put(msg);
				this.fifoR.release();
				notifyAll();
			} catch (FullQueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Message get() throws InterruptedException {
		System.out.println("usedSize: " + this.queue.getUsedSize());
		Message msg;
		this.fifoW.acquire();
		
		synchronized (this) {
			while(this.queue.getUsedSize() == 0) {
				System.out.println("freesize: " + this.queue.getFreeSize());
				wait();
			}
		
			try {
				msg = this.queue.get();
				this.fifoW.release();
				notifyAll();
			} catch (EmptyQueueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				msg = null;
			}
		}
		return msg;
	}

	@Override
	public Message[] get(int n) throws InterruptedException {
		Message[] msgs = new Message[n];
		System.out.println("usedSize: " + this.queue.getUsedSize());
		this.fifoW.acquire();
		
		while (n > 0) {
			synchronized (this) {
				while(this.queue.getUsedSize() == 0) {
					System.out.println("freesize: " + this.queue.getFreeSize());
					wait();
				}
			
				try {
					msgs[n - 1] = this.queue.get();
					notifyAll();
					
				} catch (EmptyQueueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			--n;
		}

		this.fifoW.release();
		
		return msgs;
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
