import java.util.*;
import java.io.*;

public class Day5a
{
	public static void main(String[] args) throws FileNotFoundException {
		Scanner lineScanner = new Scanner(new File("input.txt"));
        
	        HashMap<Integer,ArrayList<Integer>> rules = new HashMap<Integer, ArrayList<Integer>>();
	        String curLine = lineScanner.nextLine();
	        int sum = 0;
	        
	        // scanner reads the rules
	        while (!curLine.equals("")) {
	            
	            curLine = curLine.replace("|", " ");
	            Scanner intScanner = new Scanner(curLine);
	            
	            int before = intScanner.nextInt();
	            int after = intScanner.nextInt();
	            
	            try {
	                rules.get(before).add(after);
	            } catch (NullPointerException e) {
	                ArrayList<Integer> afterList = new ArrayList<Integer>();
	                afterList.add(after);
	                rules.put(before, afterList);
	            }
	            
	            curLine = lineScanner.nextLine();
	        }
	        
	        // scanner reads the pages
	        while (lineScanner.hasNextLine()) {
	            curLine = lineScanner.nextLine();
	            
	            curLine = curLine.replace(","," ");
	            Scanner pageScanner = new Scanner(curLine);
	            ArrayList<Integer> curList = new ArrayList<Integer>();
	            boolean rightOrder = true;
	            
	            while (pageScanner.hasNextInt()) {
	                curList.add(pageScanner.nextInt());
	            }
	            
	            // check each list element one-by-one to see if later elements exist for that corresponding key in rules HashMap
	            for (int i=0; i<curList.size(); i++) {
	                int beforeNum = curList.get(i);
	                int afterNum = 0;
	                for (int j=0; j<i; j++) {
	                    afterNum = curList.get(j);
	                    
	                    if (rules.containsKey(beforeNum) && rules.get(beforeNum).contains(afterNum)) {
	                        rightOrder = false;
	                        break;
	                    }
	                }
	                if (!rightOrder) {
	                    break;
	                }
	            }
	            
	            if (!rightOrder) {
	                continue;
	            }
	            
	            int midIndex = curList.size()/2;
	            sum += curList.get(midIndex);
	            
	        }
	        
        System.out.println(sum);

	} 
	
}
