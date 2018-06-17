package edu.handong.csee.java.HW3.ChatCounter;

import java.util.*;
import edu.handong.csee.java.HW3.ChatCounter.*;

/**
 * Read the message file and export the counter file.
 * Execute main method!
 * 
 * @author smile
 *
 */
public class ChatCounter {
	
	private HashMap<String, ArrayList<NDMData>> messages = new HashMap<String, ArrayList<NDMData>>();
	private HashMap<String, Integer> count = new HashMap<String, Integer>();
	private ArrayList<String> name = new ArrayList<String>();	

	/**
	 * Set argument[1] to inputPath and argument[3] to outputPath
	 * Execute run!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChatCounter main = new ChatCounter();
		String inputPath = args[1];
		String outputPath = args[3];
		
		main.run(inputPath, outputPath);
	}
	
	/**
	 * Method to arrange objects according to the required operation so that the action can be performed.
	 * 
	 * @param inputPath - indicates path to file location.
	 * @param outputPath - indicates location where should the file be stored
	 */
	public void run(String inputPath, String outputPath) {

		FileLoader fileLoader = new FileLoader(inputPath);
		RedundancyChecker checker = new RedundancyChecker();
		CounterMessages counter = new CounterMessages();
		FileWriter fileWriter = new FileWriter(outputPath);
		MessageSort messageSort = new MessageSort();
		
		//load file
		
		fileLoader.loadCSVFile();
		fileLoader.loadTXTFile();
		
		// Check Redundancy Messages
		
		messages = fileLoader.getMessages();
		name = fileLoader.getName();
		
		messages = checker.checkRedundancy(messages, name);
		
		// Export result file
		
		count = counter.counter(messages, name);
		count = messageSort.sortCounter(count);
		//messageSort.printSortedCount();
		
		fileWriter.wirteCSVFile(count);
	}
}

/*
for(int i = 0; i<name.size(); i++) {
	System.out.println(name.get(i));
	for(NDMData m : messages.get(name.get(i))) {
		System.out.println(m.getName() + " "+ m.getDate() + " " + m.getMessage());
	}
}*/