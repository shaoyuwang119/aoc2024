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
			System.out.print(line);


			// begin checking through each ArrayList
			boolean inc = false;
			boolean dec = false;

			for (int l=1; l<line.size(); l++) {
				if (line.get(l) > line.get(l-1)) {
					inc = true;
				} else {
					inc = false;
					break;
				}
			}

			for (int l=1; l<line.size(); l++) {
				if (line.get(l) < line.get(l-1)) {
					dec = true;
				} else {
					dec = false;
					break;
				}
			}

			System.out.print(" "+inc+" "+dec);

			boolean differReq = false;
			for (int l=1; l<line.size(); l++) {
				int diff = Math.abs(line.get(l) - line.get(l-1));
				if (diff >= 1 && diff <= 3) {
					differReq = true;
				} else {
					differReq = false;
					break;
				}
			}
            System.out.println(" "+differReq);
            
            if ((inc || dec) && differReq) {
                safeReports ++;
            }

		}
    
    System.out.println("FINAL ANSWER: "+safeReports);
	}

}
