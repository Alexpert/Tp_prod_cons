package jus.poc.prodcons.v3;

public class Message {
	private String content;
	
	Message(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String toString() {
		return this.getContent();
	}
}
