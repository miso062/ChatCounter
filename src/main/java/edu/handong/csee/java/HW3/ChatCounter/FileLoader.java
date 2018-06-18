package edu.handong.csee.java.HW3.ChatCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Get directory, from that directory load .csv file and .txt file and put it in HashMap.
 * 
 * @author smile
 */
public class FileLoader {

	private File directory = null;
	public HashMap<String, ArrayList<NDMData>> messages = new HashMap<String, ArrayList<NDMData>>();
	private ArrayList<String> name = new ArrayList<String>();
	
	/**
	 * Constructor get path and set the path to directory.
	 * @param path - indicates path to file location.
	 */
	public FileLoader(String path) {
		directory = new File(path);
	}
	
	/**
	 * Method that read the directory and get messages in csv file.
	 */
	public void loadCSVFile() {
		
		HashMap<String, ArrayList<NDMData>> csvMessages = new HashMap<String, ArrayList<NDMData>>();
		ArrayList<CSVFileLoader> csvFile = new ArrayList<CSVFileLoader>();
		ArrayList<Thread> threadForCSV = new ArrayList<Thread>();
		
		for(File file : directory.listFiles()) {
			if(file.getName().contains(".csv")) {
				
				CSVFileLoader csvFileLoader = new CSVFileLoader(file);
				csvFile.add(csvFileLoader);
				
				Thread loadCSV = new Thread(csvFileLoader);
				threadForCSV.add(loadCSV);
				
				loadCSV.start();
			}
		}
		
		for(Thread thread : threadForCSV) {
			try {
				thread.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for(CSVFileLoader file : csvFile) {
			HashMap<String, ArrayList<NDMData>> messages = new HashMap<String, ArrayList<NDMData>>();
			messages = file.getMessages();
			
			for(String key : messages.keySet()) {
				if(!csvMessages.containsKey(key)) {
					csvMessages.put(key, new ArrayList<NDMData>());
					csvMessages.get(key).addAll(messages.get(key));
					name.add(key);
				}
				else
					csvMessages.get(key).addAll(messages.get(key));
			}
		}
		
		for(String key : csvMessages.keySet()) {
			if(!messages.containsKey(key)) {
				messages.put(key, new ArrayList<NDMData>());
				messages.get(key).addAll(csvMessages.get(key));
				name.add(key);
			}
			else
				messages.get(key).addAll(messages.get(key));
		}
	}
	
	/**
	 * Method that read the directory and get messages in csv file.
	 */
	public void loadTXTFile() {
		
		HashMap<String, ArrayList<NDMData>> txtMessages = new HashMap<String, ArrayList<NDMData>>();
		ArrayList<TXTFileLoader> txtFile = new ArrayList<TXTFileLoader>();
		ArrayList<Thread> threadForTXT = new ArrayList<Thread>();
		
		for(File file : directory.listFiles()) {
			if(file.getName().contains(".txt")) {
				
				TXTFileLoader txtFileLoader = new TXTFileLoader(file);
				txtFile.add(txtFileLoader);
				
				Thread loadTXT = new Thread(txtFileLoader);
				threadForTXT.add(loadTXT);
				
				loadTXT.start();
			}
		}
		
		for(Thread thread : threadForTXT) {
			try {
				thread.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(TXTFileLoader file : txtFile) {
			HashMap<String, ArrayList<NDMData>> messages = new HashMap<String, ArrayList<NDMData>>();
			messages = file.getMessages();
			
			for(String key : messages.keySet()) {
				if(!txtMessages.containsKey(key)) {
					txtMessages.put(key, new ArrayList<NDMData>());
					txtMessages.get(key).addAll(messages.get(key));
					name.add(key);
				}
				else
					txtMessages.get(key).addAll(messages.get(key));
			}
		}
		
		for(String key : txtMessages.keySet()) {
			if(!messages.containsKey(key)) {
				messages.put(key, new ArrayList<NDMData>());
				messages.get(key).addAll(txtMessages.get(key));
				name.add(key);
			}
			else
				messages.get(key).addAll(txtMessages.get(key));
		}
	}

	
	/**
	 * Method get the messages data.
	 * @return messages - HashMap containing messages data
	 */
	public HashMap<String, ArrayList<NDMData>> getMessages(){
		return messages;
	}

	/**
	 * Method get the user_id(name) data.
	 * @return name - ArrayList containing  user_id(name) data.
	 */
	public ArrayList<String> getName(){
		return name;
	}
}


/*
for(int i = 0; i<name.size(); i++) {
	System.out.println(name.get(i));
	for(NDMData m : messages.get(name.get(i))) {
		System.out.println(m.getName() + " "+ m.getDate() + " " + m.getMessage());
	}
}*/