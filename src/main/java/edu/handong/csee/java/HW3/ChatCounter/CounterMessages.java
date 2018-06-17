package edu.handong.csee.java.HW3.ChatCounter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Count user's message.
 * 
 * Get a HashMap containing messages and an ArrayList containing user_id.
 * Get number of messages by name.
 * Create a new HashMap and enter a name and messages number in HashMap.
 * 
 * @author smile
 *
 */
public class CounterMessages {

	public HashMap<String, Integer> count = new HashMap<String, Integer>();
	public int valueCount = 0;
	
	/**
	 * Method count messages number by name
	 * 
	 * @param messages - HashMap containing messages information
	 * @param name - ArrayList containing user_id
	 * @return count - HashMap with user_id and messages number
	 */
	public HashMap<String, Integer> counter(HashMap<String, ArrayList<NDMData>> messages, ArrayList<String> name) {
		
		for(int i = 0; i < name.size(); i++) {
			valueCount = messages.get(name.get(i)).size();
			count.put(name.get(i), valueCount);
		}
		
		return count;
	}
	
	/**
	 * Method print user_id and messages number.
	 */
	public void printCount() {
		
		System.out.println("<kakao_id, count>");
		
		for(String key : count.keySet()) {
			System.out.println(key +", "+ count.get(key));
		}
	}
}