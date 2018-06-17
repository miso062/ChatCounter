package edu.handong.csee.java.HW3.ChatCounter;

import java.util.ArrayList;
import java.util.HashMap;
import edu.handong.csee.java.HW3.ChatCounter.FileLoader;
import edu.handong.csee.java.HW3.ChatCounter.NDMData;

/**
 * Remove duplicate messages.
 * 
 * Get a HashMap containing messages and an ArrayList containing user_id.
 * Check if message's data and content already exist in new HashMap(empty HashMap).
 * If it duplicate, don't put it in the HashMap.
 * 
 * @author smile
 *
 */
public class RedundancyChecker {

	public HashMap<String, ArrayList<NDMData>> removedMessage = new HashMap<String, ArrayList<NDMData>>();
	public ArrayList<NDMData> messagesData = new ArrayList<NDMData>();
	public ArrayList<NDMData> duplicateRemovedData = new ArrayList<NDMData>();
	
	/**
	 * Method check the duplicate message and make new HashMap without duplicate message!
	 * 
	 * @param messages - HashMap containing messages information
	 * @param name - ArrayList containing user_id - name
	 * @return checkRedundancy - HashMap with duplicate messages removed
	 */
	public HashMap<String, ArrayList<NDMData>> checkRedundancy(HashMap<String, ArrayList<NDMData>> messages, ArrayList<String> name) {
		
		for(String id : name) {
			
			for(int i = 0; i < messages.get(id).size(); i++) {
				NDMData checkData = messages.get(id).get(i);
				
				outerloop:
				if(duplicateRemovedData.size() == 0) {
					duplicateRemovedData.add(checkData);
					removedMessage.put(id, new ArrayList<NDMData>());
					removedMessage.get(id).add(checkData);
					duplicateRemovedData.add(checkData);
				}
				
				else if(duplicateRemovedData.size() > 0){
					for(NDMData data : duplicateRemovedData) {
						if(data.getDate().equals(checkData.getDate()) && data.getMessage().startsWith(checkData.getMessage())) { //.equals(checkData.getMessage()
							break outerloop;
						}
					}
					
					duplicateRemovedData.add(checkData);
					removedMessage.get(id).add(checkData);
				}
		
			}
			duplicateRemovedData.clear();
		}
		
		return removedMessage;
	}
}
		

// "사진" "photo"
// "이모티콘" "emoticon"

/*
for(int i = 0; i<name.size(); i++) {
	System.out.println(name.get(i));
	for(NDMData m : removedMessage.get(name.get(i))) {
		System.out.println(m.getName() + " "+ m.getDate() + " " + m.getMessage());
	}
}
*/