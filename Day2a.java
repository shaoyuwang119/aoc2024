import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException {
		int safeReports = 0;
		Scanner lineScan = new Scanner(new File("input.txt"));

		for (int i=0; i<1000; i++) {

			String lineStr = lineScan.nextLine();
			Scanner levelScan = new Scanner(lineStr);

			ArrayList<Integer> line = new ArrayList<Integer>();
			while (levelScan.hasNextInt()) {
				line.add(levelScan.nextInt());
			}

            
            if (isSafe(line)) {
                safeReports ++;
            }

		}
    
    System.out.println(safeReports);
	}
	
	public static boolean isSafe(ArrayList<Integer> list) {
	    	boolean inc = false;
			boolean dec = false;

			for (int i=1; i<list.size(); i++) {
				if (list.get(i) > list.get(i-1)) {
					inc = true;
				} else {
					inc = false;
					break;
				}
			}

			for (int i=1; i<list.size(); i++) {
				if (list.get(i) < list.get(i-1)) {
					dec = true;
				} else {
					dec = false;
					break;
				}
			}


			boolean differReq = false;
			for (int i=1; i<list.size(); i++) {
				int diff = Math.abs(list.get(i) - list.get(i-1));
				if (diff >= 1 && diff <= 3) {
					differReq = true;
				} else {
					differReq = false;
					break;
				}
			}
			
			return((inc || dec) && differReq);
	}

}
