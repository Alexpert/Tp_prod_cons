package jus.poc.prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer {
	private Queue<Message> queue;

	ProdConsBuffer(int size) {
		this.queue = new Queue<Message>(size);
	}

	@Override
	public synchronized void put(Message msg) throws InterruptedException {
		while(this.queue.getFreeSize() == 0) {
//			System.out.println("freesize: " + this.queue.getFreeSize());
			wait();
		}
		
		try {
			this.queue.put(msg);
			notifyAll();
		} catch (FullQueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Message get() throws InterruptedException {
//		System.out.println("usedSize: " + this.queue.getUsedSize());
		
		while(this.queue.getUsedSize() == 0) {
//			System.out.println("freesize: " + this.queue.getFreeSize());
			wait();
		}
		
		try {
			Message msg = this.queue.get();
			notifyAll();
			return msg;
		} catch (EmptyQueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
