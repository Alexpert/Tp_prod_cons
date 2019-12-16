package jus.poc.prodcons.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;


public class TestProdCons {
	public static void main(String[] args) throws IOException, InterruptedException {
		String filePath = "options.xml";
		
		Properties properties = new Properties();
		properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream(filePath));
		
		int nProd 		= 50;
		int nCons 		= 50;
		int bufSz 		= 10;
		int prodTime 	= 100;
		int consTime 	= 100;
		int minProd 	= 1;
		int maxProd 	= 200;
		int minCons 	= 1;
		int maxCons 	= 200;
		
//		int nProd 		= Integer.parseInt(properties.getProperty("nProd"));
//		int nCons 		= Integer.parseInt(properties.getProperty("nCons"));
//		int bufSz 		= Integer.parseInt(properties.getProperty("bufSz"));
//		int prodTime 	= Integer.parseInt(properties.getProperty("prodTime"));
//		int consTime 	= Integer.parseInt(properties.getProperty("consTime"));
//		int minProd 	= Integer.parseInt(properties.getProperty("minProd"));
//		int maxProd 	= Integer.parseInt(properties.getProperty("maxProd"));
//		int minCons 	= Integer.parseInt(properties.getProperty("minCons"));
//		int maxCons 	= Integer.parseInt(properties.getProperty("maxCons"));
		
		Random rand = new Random();
		ArrayList<Producer> producers = new ArrayList<>();
		ArrayList<Consumer> consumers = new ArrayList<>();
		ArrayList<Actor> actors = new ArrayList<>();
		ProdConsBuffer buffer = new ProdConsBuffer(bufSz);
		
		System.out.println("nProd " + nProd);
		System.out.println("nCons " + nCons);
		System.out.println("bufSz " + bufSz);
		System.out.println("prodTime " + prodTime);
		System.out.println("consTime " + consTime);
		System.out.println("minProd " + minProd);
		System.out.println("maxProd " + maxProd);
		System.out.println("minCons " + minCons);
		System.out.println("maxCons " + maxCons);
		
		int totalMsg = 0;
		
		for (int i = 0; i < nProd; i++) {
			Producer producer = new Producer(prodTime, rand.nextInt(maxProd - minProd) + minProd);
			producer.setBuffer(buffer);
			producers.add(producer);
			actors.add(producer);
			
			totalMsg += producer.getNbMsg();
		}

		for (int i = 0; i < nCons; i++) {
			Consumer consumer = new Consumer(consTime, rand.nextInt(maxCons - minCons) + minCons);
			consumer.setBuffer(buffer);
			consumers.add(consumer);
			actors.add(consumer);
		}
		
		Collections.shuffle(actors);
		
		for (Actor actor: actors)
			new Thread(actor).start();
		
		while(buffer.totmsg() < totalMsg) {
			Thread.sleep(100);
		}
		
		System.err.println("GAME OVER");
		System.exit(0);

	}

}
