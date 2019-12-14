package jus.poc.prodcons.v1;

public abstract class Actor implements Runnable {
	
	protected ProdConsBuffer buffer;

	public void setBuffer(ProdConsBuffer buffer) {
		this.buffer = buffer;
	}

}
