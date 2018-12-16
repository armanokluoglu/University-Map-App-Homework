package data.layer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IzmapReader {
	
	public List<String> readLines(String path) {
    	List<String> lines = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lines;
	}
}