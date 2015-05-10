package com.mygdx.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteText {
	
	
	    public WriteText(){

	    }
	    
	    public static void WriteName(String name) {
	        BufferedWriter writer = null;
	        try {
	            //create a temporary file
	            
	            File file = new File("nimi.txt");

	            // This will output the full path where the file will be written to...
	            System.out.println(file.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(file));
	            writer.write(name);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            }
	        }
	    }
	    
	    public static String ReadName() {
	    	String everything = "error";
	        try(BufferedReader br = new BufferedReader(new FileReader("nimi.txt"))) {
	            StringBuilder sb = new StringBuilder();
	            String line = br.readLine();

	            while (line != null) {
	                sb.append(line);
	                sb.append(System.lineSeparator());
	                line = br.readLine();
	                
	            }
	            everything = sb.toString();
	            
	            return everything;
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return everything;
	    }
	

}
