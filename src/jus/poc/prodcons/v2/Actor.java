package jus.poc.prodcons.v2;

public abstract class Actor implements Runnable {
	
	protected ProdConsBuffer buffer;

	public void setBuffer(ProdConsBuffer buffer) {
		this.buffer = buffer;
	}

}
