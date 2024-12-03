import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException {
	Scanner scan = new Scanner(new File("input.txt"));
        File f = new File("input.txt");
        String file = "";
        int sum = 0;
        
        
        while (scan.hasNextLine()) {
            file += scan.nextLine();
        }
        
        for (int i=0; i<file.length()-1; i++) {
    	    if (file.substring(i+1,i+2).equals("u") && file.substring(i+2,i+3).equals("l") && file.substring(i+3,i+4).equals("(")) {
    	        String sx = "";
    	        String sy = "";
    	        int x = 0;
    	        int y = 0;
    	        int j = i+4;
    	        
    	        while (!file.substring(j,j+1).equals(",")) {
    	            sx += file.substring(j,j+1);
    	            j++;
    	        }
    	        
    	        try {
    	            x = Integer.parseInt(sx);
    	        } catch (NumberFormatException e) {
    	            continue;
    	            
    	        }
    	        
    	        j++;
    	        
    	        while (!file.substring(j,j+1).equals(")")) {
    	            sy += file.substring(j,j+1);
    	            j++;
    	        }
    	        try {
    	            y = Integer.parseInt(sy);
    	        } catch (NumberFormatException e) {
    	            continue;
    	        }
    	        
    	        sum += x*y;
    	        
    	    }
    	
        }
		    System.out.println(sum);
	}
	
	    
}
