package jus.poc.prodcons.v2;

public interface IProdConsBuffer {
	// Puts m in the prodcons buffer
	public void put(Message M) throws InterruptedException;
	
	// Retrieves a message from the prodcons buffer, following a FIFO order
	public Message get() throws InterruptedException;
	
	// returns the nb of messages currently available in the buffer
	public int nmsg();
	
	//return the total number of message that have been put in the buffer since it's creation
	public int totmsg();
}
