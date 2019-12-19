package jus.poc.prodcons.v4;

public class Message {
	private String content;
	private int quantity;
	
	Message(String content) {
		this.content = content;
		this.quantity = 1;
	}
	
	Message(String content, int n) {
		this.content = content;
		this.quantity = n;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String toString() {
		return this.getContent();
	}
	
	public void decrementQuantity() {
		synchronized(this) {
			this.quantity--;
			notifyAll();
		}
	}
	
	public void waitExtinction() throws InterruptedException {
		synchronized(this) {
			while (this.quantity > 0)
				wait();
		}
	}
}
