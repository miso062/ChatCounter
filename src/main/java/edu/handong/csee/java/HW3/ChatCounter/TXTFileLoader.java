package edu.handong.csee.java.HW3.ChatCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Get directory, and load txt file in the directory by using thread.
 * 
 * @author smile
 *
 */
public class TXTFileLoader implements Runnable{

	private File directory = null;
	public HashMap<String, ArrayList<NDMData>> messages = new HashMap<String, ArrayList<NDMData>>();
	private ArrayList<String> name = new ArrayList<String>();
	
	/**
	 * Constructor get file and set the path to directory.
	 * @param file - to be read
	 */
	public TXTFileLoader(File file) {
		this.directory = file;
	}
	
	@Override
	public void run() {
		messages = getMessages(directory);
	}
	
	/**
	 * Method load .txt file and put content of the file to HashMap.
	 * 
	 * @param file - to be read
	 * @return messages
	 */
	public HashMap<String, ArrayList<NDMData>> getMessages(File file) {

		try {
					
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line;
			String user = "";
			String time = "";
			String messageString = "";
					
			while((line = reader.readLine()) !=null) {
					
				String pattern = "\\[(.+)\\] \\[(.*[0-9]+:[0-9]+.*)\\] (.+)";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);
				
				if(m.find()) {
					
					user = m.group(1).trim();
					time = m.group(2).trim();
					messageString = m.group(3).trim();
							
					time = setAmpm(time);
					
					if(!messages.containsKey(user)) {
						messages.put(user, new ArrayList<NDMData>());
						messages.get(user).add(new NDMData(user, time, messageString));
						name.add(user);
					}
					
					else {
						messages.get(user).add(new NDMData(user, time, messageString));
					}
				}						
			}
		}catch (Exception e) {
	
		}
		
		return messages;
	}
	
	/**
	 * Method that change the time of .txt file from 12-hour clock to 24-hour clock
	 * 
	 * @param time - txt file's time
	 * @return time - changed time
	 */
	public String setAmpm(String time) {
		
		String timePattern1 = "(..) ([0-9]+):([0-9]+)";
		String timePattern2 = "([0-9]+):([0-9]+) (..)";
		
		Pattern tPattern1 = Pattern.compile(timePattern1);
		Pattern tPattern2 = Pattern.compile(timePattern2);
		
		Matcher tMatcher1 = tPattern1.matcher(time);
		Matcher tMatcher2 = tPattern2.matcher(time);
		
		if(tMatcher1.find()) {
			
			String ampm = tMatcher1.group(1);
			int hour = Integer.parseInt(tMatcher1.group(2));
		
			if(ampm.equals("오후") && hour >=1 && hour <= 11)
				hour += 12;
			else if(ampm.equals("오전") && hour == 12)
				hour -= 12;
	
			String hourStr = String.format("%02d", hour);
			time = hourStr +":"+ tMatcher1.group(3);
		}
		
		if(tMatcher2.find()) {
			
			String ampm = tMatcher2.group(3);
			int hour = Integer.parseInt(tMatcher2.group(1));
			
			if(ampm.equals("PM") && hour >=1 && hour <= 11)
				hour += 12;
			else if(ampm.equals("AM") && hour == 12)
				hour -= 12;
			
			String hourStr = String.format("%02d", hour);
			time = hourStr +":"+ tMatcher2.group(2);
		}
		
		return time;
	}
	
	/**
	 * Method get the messages data.
	 * @return messages - HashMap containing messages data
	 */
	public HashMap<String, ArrayList<NDMData>> getMessages(){
		return messages;
	}
	
}
