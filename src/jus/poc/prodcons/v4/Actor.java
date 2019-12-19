package jus.poc.prodcons.v4;

public abstract class Actor implements Runnable {
	
	protected ProdConsBuffer buffer;

	public void setBuffer(ProdConsBuffer buffer) {
		this.buffer = buffer;
	}

}
