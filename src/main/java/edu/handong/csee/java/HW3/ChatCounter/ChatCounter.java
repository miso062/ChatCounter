package edu.handong.csee.java.HW3.ChatCounter;

import java.util.*;
import edu.handong.csee.java.HW3.ChatCounter.*;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;

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
	
	private String inputPath;
	private String outputPath;
	private boolean help;

	/**
	 * main method
	 * Execute run method !
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChatCounter main = new ChatCounter();
		main.run(args);
	}
	
	/**
	 * Method to arrange objects according to the required operation,
	 * so that the action can be performed.
	 * 
	 * @param args
	 */
	public void run(String[] args) {

		Options options = createOptions();
		
		if(parseOptions(options, args)) {
			if(help) {
				printHelp(options);
				return;
			}
		}
		
		FileLoader fileLoader = new FileLoader(inputPath);
		RedundancyChecker checker = new RedundancyChecker();
		CounterMessages counter = new CounterMessages();
		MessageSort messageSort = new MessageSort();
		FileWriter fileWriter = new FileWriter(outputPath);
		
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
		messageSort.printSortedCount();
		
		//fileWriter.wirteCSVFile(count);
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
			
		}catch (Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt(inputPath).hasArg().required().build());
		options.addOption(Option.builder("o").longOpt(outputPath).hasArg().required().build());
		options.addOption(Option.builder("h").longOpt("help").build());
		
		return options;
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "";
		String footer = "";
		
		formatter.printHelp("ChatCounter", header, options, footer, true);
	}
}

/*
for(int i = 0; i<name.size(); i++) {
	System.out.println(name.get(i));
	for(NDMData m : messages.get(name.get(i))) {
		System.out.println(m.getName() + " "+ m.getDate() + " " + m.getMessage());
	}
}*/