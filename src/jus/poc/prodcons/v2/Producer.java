package jus.poc.prodcons.v2;

import java.util.Random;

public class Producer extends Actor {
	static private String[] writings = 
		{"On ne devrait jamais quitter Montauban",
		 "Mais dis-donc, on est tout de même pas venu pour beurrer les sandwichs !", 
		 "C'est curieux chez les marins ce besoin de faire des phrases !",
		 "Je ne rêve pas en couleur, je ne rêve pas en noir, je ne rêve pas du tout, je n'ai pas le temps!",
		 "C'est jamais bon de laisser dormir les créances, et surtout de permettre au petit personnel de rêver.",
		 "Je vais lui montrer qui c'est Raoul. Aux quatre coins de Paris qu'on va le retrouver, éparpillé par petits bouts, façon Puzzle.",};
	
	private int prodTime;
	private int nbMsg;

	public Producer(int prodTime, int nbMsg) {
		super();
		this.prodTime = prodTime;
		this.nbMsg = nbMsg;
	}
	
	public int getNbMsg() {
		return this.nbMsg;
	}

	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < this.nbMsg; ++i) {
			try {
				Message msg = new Message("msg n°" + i + " of " + nbMsg);
//				Message msg = this.beCreative();
				this.buffer.put(msg);
				System.out.println("Producer:" + msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(random.nextInt(this.prodTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.err.println("PRODUCER FINISHED");
	}

	private Message beCreative() {
		return new Message(Producer.writings[new Random().nextInt(Producer.writings.length)]);
	}

}
