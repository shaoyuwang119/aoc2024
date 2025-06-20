import java.util.*;
import java.io.*;

public class Day5b
{
	public static void main(String[] args) throws FileNotFoundException {
	    
		    Scanner lineScanner = new Scanner(new File("input.txt"));
        
        HashMap<Integer,ArrayList<Integer>> rules = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> wrongOrders = new ArrayList<ArrayList<Integer>>();
        
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
        System.out.println(rules);
        
        
        // scanner reads the updates
        while (lineScanner.hasNextLine()) {
            curLine = lineScanner.nextLine();
            curLine = curLine.replace(","," ");
            
            Scanner pageScanner = new Scanner(curLine);
            ArrayList<Integer> curList = new ArrayList<Integer>();
            
            boolean rightOrder = true;
            
            while (pageScanner.hasNextInt()) {
                curList.add(pageScanner.nextInt());
            }
            
            if (isCorrectOrder(rules, curList)) {
                continue;
            } else { 
                // adjust the incorrectly ordered updates so they are correct
                ArrayList<Integer> corrected = new ArrayList<Integer>(curList);
                
                // my wacky algorithm is to just keep swapping rulebreaking value pairs until the order is correct
                while (!isCorrectOrder(rules, corrected)) { 
                    for (int i=0; i<corrected.size(); i++) {
                        int beforeNum = corrected.get(i);
                        int afterNum = 0;
                        for (int j=0; j<i; j++) {
                            afterNum = corrected.get(j);
                            
                            if (rules.containsKey(beforeNum) && rules.get(beforeNum).contains(afterNum)) {

                                Collections.swap(corrected, i, j);
                                
                            }
                        }
                    }
                }
                
                System.out.println("original: " + curList);
                System.out.println("corrected: " + corrected + "");
                System.out.println("middle value: " + corrected.get(corrected.size()/2) + "\n");
                sum += corrected.get(corrected.size()/2);
                
                
            }
            
            pageScanner.close();
            
        }
        
        System.out.println("ANSWER " + sum);
        

	} 
	
	public static boolean isCorrectOrder(HashMap<Integer,ArrayList<Integer>> rules, 
	                                    ArrayList<Integer> upd) 
  { 
      // the algorithm here is to have a value from the current list, and check all the ints that come before that value in the list, 
      	// and see if any of those ints break the rules
	    for (int i=0; i<upd.size(); i++) {
                int beforeNum = upd.get(i);
                int afterNum = 0;
                for (int j=0; j<i; j++) {
                    afterNum = upd.get(j);
                    
                    if (rules.containsKey(beforeNum) && rules.get(beforeNum).contains(afterNum)) {
                        return false;
                    }
                }
            }
        return true;
	}
	
}
