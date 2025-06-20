import java.util.*;
import java.io.*;

public class Day4b
{
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("input.txt"));
        
        // initalize array
        scan.useDelimiter("");
        int len = 140;
        String[][] file = new String[len][len];
        int occ = 0;
        
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                file[i][j] = scan.next();
                if (file[i][j].equals("\n")) {
                    file[i][j] = scan.next();
                }
            }
        }
        String curStr = "";
        
        // begin searching for MAS's in an X-shape
        boolean isXmas = false;
        for (int i=1; i<len-1; i++) {
            for (int j=1; j<len-1; j++) {
                if (file[i][j].equals("A")) {
                    isXmas = (file[i+1][j+1].equals("M") && file[i-1][j-1].equals("S") &&
                              file[i-1][j+1].equals("M") && file[i+1][j-1].equals("S")
                              ||
                              file[i-1][j-1].equals("M") && file[i+1][j+1].equals("S") &&
                              file[i+1][j-1].equals("M") && file[i-1][j+1].equals("S")
                              ||
                              file[i-1][j+1].equals("M") && file[i+1][j-1].equals("S") &&
                              file[i-1][j-1].equals("M") && file[i+1][j+1].equals("S")
                              ||
                              file[i+1][j-1].equals("M") && file[i-1][j+1].equals("S") &&
                              file[i+1][j+1].equals("M") && file[i-1][j-1].equals("S"));
                              
                    if (isXmas) {
                        occ ++;
                    }
                }
            }
        }
            
        System.out.println(occ);

	} 
}
