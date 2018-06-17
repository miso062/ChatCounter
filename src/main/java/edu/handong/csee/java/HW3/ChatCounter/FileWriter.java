package edu.handong.csee.java.HW3.ChatCounter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileWriter {
	
	private File fileName = null;
	
	public FileWriter(String path) {
		fileName = new File(path);
	}

	public void wirteCSVFile(HashMap<String, Integer> count) {
		
		try {  
			
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			out.write("kakao_id, count\n");
			
			for(String key : count.keySet()) {
				out.write(key + " , " + count.get(key) + "\n");
			}
			
			out.close();
		} catch (Exception e) {
			
		}
	}
}