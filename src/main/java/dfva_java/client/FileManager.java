package dfva_java.client;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisza
 */
public class FileManager {
    
    FileWriter writer;
    BufferedReader br;
    private void write_with_throws(String filepath, String text) throws IOException{
    	if(text==null){
    		return;
    	}
        File file = new File(filepath);
        if(!file.exists()){
            file.createNewFile();
        }
        writer = new FileWriter(file); 
        // Writes the content to the file
        writer.write(text); 
        writer.flush();

    }
    
    public void write_file(String filepath, String text){
        try {
            write_with_throws(filepath, text);
        } catch (IOException ex) {
            Logger.getLogger("dfva_java").log(Level.SEVERE, null, ex);
        }finally{
            try {
            	if(writer != null) writer.close();
            } catch (IOException ex) {
                Logger.getLogger("dfva_java").log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    private String read_file_with_throws(String filepath) throws FileNotFoundException, IOException{
        br = new BufferedReader(new FileReader(filepath));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        return sb.toString();
    
    }
    
    public String read_file(String filepath){
        String dev = null;
        try {
            dev = read_file_with_throws(filepath);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            Logger.getLogger("dfva_java").log(Level.SEVERE, null, ex);
        }finally{
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger("dfva_java").log(Level.SEVERE, null, ex);
            }
        }
        return dev;
    }
}