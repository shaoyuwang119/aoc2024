import java.util.*;
import java.io.*;
import java.lang.*;

public class Day9a
{

	public static void main(String[] args) throws FileNotFoundException
	{
		File f = new File("input.txt");
	    
	    Scanner scan = new Scanner(f);
	    scan.useDelimiter("");
	    
	    long startTime = System.nanoTime();
	    
	    String disk = "";
	    ArrayList<Integer> blocks = new ArrayList<>();
	    
	    while (scan.hasNext()) {
	        disk += scan.next();
	    }
	    
	    
	    for (int i=0; i<disk.length(); i++) {
	        int digit = Integer.parseInt(disk.substring(i,i+1));
	        
	        for (int j=0; j<digit; j++) {
	            if (i%2 == 0) {
                    blocks.add(i/2); 
                } else {
                    blocks.add(null);
	            }
	        }
	       
	    }    
	    
	    System.out.println(blocks);
	    
	    // start moving
	    for (int i=blocks.size()-1; i>=0; i--) {
	        if (!doneMoving(blocks) && blocks.get(i) != null) {
	            blocks.set(nextFree(blocks), blocks.get(i));
	            blocks.set(i, null);
	            //System.out.println(blocks + doneMoving(blocks));
	        }
	    }
	    
	    System.out.println("\n\n" + blocks);
	    System.out.println("ANSWER " + checksum(blocks));
	    
	    System.out.println((System.nanoTime()-startTime)/1000000 + "millisecs");
	    
	    
	    
	}
	
	
	
	public static int nextFree(ArrayList<Integer> blocks) {
	    for (int i=0; i<blocks.size(); i++) {
	        if (blocks.get(i) == null) {
	            return i;
	        }
	        
	    }
	    
	    throw new Error("Free space not found in blocks!");
	}
	
	
	public static boolean doneMoving(ArrayList<Integer> blocks) {
	    int i=0;
	    
	    for (i=0; blocks.get(i)!=null; i++);
	    
	    while (i<blocks.size()) {
	        if (blocks.get(i++) != null) {
	            return false;
	        } 
        }   
        
        return true;
	}
	
	
	public static long checksum(ArrayList<Integer> blocks) {
	    long sum = 0L;
	    for (int i=0; i<blocks.size(); i++) {
	        if (blocks.get(i) != null) {
	            sum += i * blocks.get(i);
	        } else {
	            break;
	        }
	    }
	    
	    return sum;
	}
}
