import java.util.*;
import java.io.*;

public class Day4a
{
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("input.txt"));
        File f = new File("tests.txt");
        
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
        
        
        // search from left to right
        for (int i=0; i<len; i++) {
            for (int j=0; j<len-3; j++) { // horizontal, left to right
                curStr = file[i][j] + file[i][j+1] + file[i][j+2] + file[i][j+3];
                if (curStr.equals("XMAS")) {
                    occ += 1;
                }
            }
            
            if (i < len-3) { // vertical, top to bottom
                for (int j=0; j<len; j++) { 
                    curStr = file[i][j] + file[i+1][j] + file[i+2][j] + file[i+3][j];
                    if (curStr.equals("XMAS")) {
                        occ += 1;
                    }
                }
            }
            
            
            if (i < len-3) { // diagonal, top left to to bottom right
                for (int j=0; j<len-3; j++) {
                    curStr = file[i][j] + file[i+1][j+1] + file[i+2][j+2] + file[i+3][j+3];
                    if (curStr.equals("XMAS")) {
                        occ += 1;
                    }
                }
            }
            
            if (i >= 3) { // diagonal, bottom left to top right
                for (int j=0; j<len-3; j++) {
                    curStr = file[i][j] + file[i-1][j+1] + file[i-2][j+2] + file[i-3][j+3];
                    if (curStr.equals("XMAS")) {
                        occ += 1;
                    }
                }
            }
        }
        
        // search from right to left
        for (int i=0; i<len; i++) {
            for (int j=len-1; j>=3; j--) { // horizontal, right to left
                curStr = file[i][j] + file[i][j-1] + file[i][j-2] + file[i][j-3];
                if (curStr.equals("XMAS")) {
                    occ += 1;
                }
            }
            
            if (i >= 3) { // vertical, bottom to top
                for (int j=len-1; j>=0; j--) { 
                    curStr = file[i][j] + file[i-1][j] + file[i-2][j] + file[i-3][j];
                    if (curStr.equals("XMAS")) {
                        occ += 1;
                    }
                }
            }
            
            
            if (i < len-3) { // diagonal, top right to to bottom left
                for (int j=len-1; j>=3; j--) {
                    curStr = file[i][j] + file[i+1][j-1] + file[i+2][j-2] + file[i+3][j-3];
                    if (curStr.equals("XMAS")) {
                        occ += 1;
                    }
                }
            }
            
            if (i >= 3) { // diagonal, bottom right to top left
                for (int j=len-1; j>=3; j--) {
                    curStr = file[i][j] + file[i-1][j-1] + file[i-2][j-2] + file[i-3][j-3];
                    if (curStr.equals("XMAS")) {
                        occ += 1;
                    }
                }
            }
            
        }
        
        System.out.println(occ);

        
    
	} 
}
