import java.util.*;
import java.io.*;

public class Day3b
{
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("input.txt"));
        File f = new File("input.txt");
        String file = "";
        int sum = 0;
        
        boolean doMul = true;
        
        
        while (scan.hasNextLine()) {
            file += scan.nextLine();
        }
        
        for (int i=0; i<file.length()-6; i++) {
            if (doMul) {
        	    if (file.substring(i,i+4).equals("mul(")) {
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
        	        
        	    } else if (file.substring(i,i+7).equals("don't()")) {
        	        doMul = false;
        	        continue;
        	    }
        	
            } else {
                if (file.substring(i,i+4).equals("do()")) {
                    doMul = true;
                }
                continue;
            }
        }
		    System.out.println(sum);
	}
	
	    
}
