package jus.poc.prodcons.v4;

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
		this.fifoW.acquire();
		
		synchronized (this) {
			while(this.queue.getFreeSize() == 0) {
				System.out.println("freesize: " + this.queue.getFreeSize());
				wait();
			}
		
			try {
				this.queue.put(msg);
				notifyAll();
			} catch (FullQueueException e) {
				e.printStackTrace();
			}
		}
		
		this.fifoW.release();
		
		msg.waitExtinction();
	}

	@Override
	public void put(Message msg, int n) throws InterruptedException {
		fifoW.acquire();
		
		synchronized (this) {
			while (n > 0) {
				while (this.queue.getFreeSize() == 0)
					wait();

				try {
					this.queue.put(msg);
					notifyAll();
				} catch (FullQueueException e) {
					e.printStackTrace();
				}
				
				--n;
			}
		}
		this.fifoW.release();
		
		msg.waitExtinction();
	}

	@Override
	public Message get() throws InterruptedException {
		System.out.println("usedSize: " + this.queue.getUsedSize());
		Message msg;
		this.fifoR.acquire();
		
		synchronized (this) {
			while(this.queue.getUsedSize() == 0) {
				System.out.println("freesize: " + this.queue.getFreeSize());
				wait();
			}
		
			try {
				msg = this.queue.get();
				msg.decrementQuantity();
				notifyAll();
			} catch (EmptyQueueException e) {
				msg = null;
				e.printStackTrace();
			}
		}
		msg.waitExtinction();
		this.fifoR.release();
		return msg;
	}

	@Override
	public Message[] get(int n) throws InterruptedException {
		Message[] msgs = new Message[n];
		this.fifoR.acquire();
		
		synchronized (this) {
			while (n > 0) {
				while(this.queue.getUsedSize() == 0)
					wait();

				Message msg;
				try {
					msg = this.queue.get();
					msgs[n - 1] = msg;
					msg.decrementQuantity();
					notifyAll();
				} catch (EmptyQueueException e) {
					msg = null;
					e.printStackTrace();
				}
				
				--n;
			}
		}
		
		this.fifoR.release();
		return msgs;
	}

	@Override
	public int nmsg() {
		return this.queue.getUsedSize();
	}

	@Override
	public int totmsg() {
		return this.queue.getTotalPut();
	}
	
	public int totread() {
		return this.queue.getTotalGet();
	}

}
