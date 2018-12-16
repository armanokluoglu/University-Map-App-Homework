package data.layer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IzmapWriter {
	
	public void write(String path, String str) {
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(str);
	    } catch (IOException ioe) {
		    ioe.printStackTrace();
		} finally { 
		    try {
		    	if(bw!=null) {
		    		bw.close();
		    	}
		    } catch(Exception ex) {
		    	ex.printStackTrace();
		    }
		}
	}
}