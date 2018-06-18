package edu.handong.csee.java.HW3.ChatCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Get directory, and load txt file in the directory by using thread.
 * 
 * @author smile
 *
 */
public class CSVFileLoader implements Runnable{

	private File directory = null;
	public HashMap<String, ArrayList<NDMData>> messages = new HashMap<String, ArrayList<NDMData>>();
	private ArrayList<String> name = new ArrayList<String>();
	
	/**
	 * Constructor get file and set the path to directory.
	 * @param file - to be read
	 */
	public CSVFileLoader(File file) {
		this.directory = file;
	}

	@Override
	public void run() {
		messages = getMessages(directory);
	}
	
	/**
	 * Method load .csv file and put content of the file to HashMap
	 * 
	 * @param file - to be read
	 * @return messages
	 */
	public HashMap<String, ArrayList<NDMData>> getMessages(File file) {
		
		try { 
			
			Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
			
			for(CSVRecord record : records) {
				String time = record.get(0).substring(11, 16);
				String user = record.get(1).trim();
				String messageString = record.get(2).trim();
				
				if(!messages.containsKey(user)) {
					messages.put(user, new ArrayList<NDMData>());
					messages.get(user).add(new NDMData(user, time, messageString));
					name.add(user);
				}
				else
					messages.get(user).add(new NDMData(user, time, messageString));
			}
		}catch (Exception e) {
			e.printStackTrace();					
		}
		
		return messages;
	}

	/**
	 * Method get the messages data.
	 * @return messages - HashMap containing messages data
	 */
	public HashMap<String, ArrayList<NDMData>> getMessages(){
		return messages;
	}
}
