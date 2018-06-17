package edu.handong.csee.java.HW3.ChatCounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Sort HashMap containing counter and set the order according to size.
 * 
 * @author smile
 *
 */
public class MessageSort {
	
	public LinkedHashMap<String, Integer> sortedCount = new LinkedHashMap<String, Integer>();
	
	public HashMap<String, Integer> sortCounter(HashMap<String, Integer> count){
		
		Iterator it = sortByValue(count).iterator();
		
		while(it.hasNext()) {

            String temp = (String) it.next();
            //System.out.println(temp + " = " + count.get(temp));
            
            sortedCount.put(temp, count.get(temp));
		}
		
		return sortedCount;
	}
	
	public static List<String> sortByValue(final Map<String, Integer> count) {

        List<String> list = new ArrayList<String>();
       	list.addAll(count.keySet());

        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings("unchecked")
			public int compare(Object o1,Object o2) {
                Object v1 = count.get(o1);
                Object v2 = count.get(o2);

                return ((Comparable<Object>) v1).compareTo(v2);
            }
        });

        Collections.reverse(list);
        return list;
    }
	
	/**
	 * Method print user_id and messages number in numerical order(larger number first!).
	 */
	public void printSortedCount() {
		
		System.out.println("<kakao_id, count>");
		
		for(String key : sortedCount.keySet()) {
			System.out.println(key +", "+ sortedCount.get(key));
		}
	}
}