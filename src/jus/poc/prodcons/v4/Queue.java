package jus.poc.prodcons.v4;

public class Queue<T> {
	private int size;
	private T[] content;
	private int readIdx;
	private int writeIdx;
	private int count;
	private int totalPut;
	private int totalGet;
	
	@SuppressWarnings("unchecked")
	public Queue (int max) {
		this.size = max;
		this.content = (T[]) new Object[max + 1];
		this.readIdx = 0;
		this.writeIdx = 0;
		this.count = 0;
		this.totalPut = 0;
		this.totalGet = 0;
	}
	
	public void put(T e) throws FullQueueException {
		if (this.getFreeSize() == 0)
			throw new FullQueueException();
		
		this.content[this.writeIdx] = e;
		this.writeIdx = (this.writeIdx + 1) % (this.size + 1);
		this.count++;
		this.totalPut++;
	}

	public T get() throws EmptyQueueException {
		if (this.getUsedSize() == 0)
			throw new EmptyQueueException();
		
		T e = this.content[this.readIdx];
		this.readIdx = (this.readIdx + 1) % (this.size + 1);
		this.count--;
		this.totalGet++;
		return (e);
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getUsedSize() {
		return this.count;
	}
	
	public int getFreeSize() {
		return this.size - this.count;
	}
	
	public int getTotalPut() {
		return this.totalPut;
	}
	
	public int getTotalGet() {
		return this.totalGet;
	}

}
