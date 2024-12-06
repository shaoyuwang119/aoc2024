import java.util.*;
import java.io.*;
import java.lang.*;

public class Day6a
{
    
	public static void main(String[] args) throws FileNotFoundException 
	{
	    
		Scanner lineScanner = new Scanner(new File("input.txt"));
		
		ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
		int rows = 0;
		int cols = 0;

        while (lineScanner.hasNextLine()) {
            String curLine = lineScanner.nextLine();
            rows++;
            
            grid.add(new ArrayList<String>());
            Scanner tileScanner = new Scanner(curLine);
            tileScanner.useDelimiter("");
            
            while (tileScanner.hasNext()) {
                String tile = tileScanner.next();
                cols++;
                
                grid.get(rows-1).add(tile);
            }
            
        }
        
        printGrid(grid);
        
        System.out.println(Arrays.toString(findGuard(grid)) + "\n");
        
        int gRow = findGuard(grid)[0];
        int gCol = findGuard(grid)[1];
        int gDir = findGuard(grid)[2];
        
        while (gRow > 0 && gRow < rows-1 && gCol > 0 && gCol < cols-1) {
            
            if (gDir == 0 && !grid.get(gRow-1).get(gCol).equals("#")) {
                forward(grid);
            } else if (gDir == 1 && !grid.get(gRow).get(gCol+1).equals("#")) {
                forward(grid);
            } else if (gDir == 2 && !grid.get(gRow+1).get(gCol).equals("#")) {
                forward(grid);
            } else if (gDir == 3 && !grid.get(gRow).get(gCol-1).equals("#")) {
                forward(grid);
            } else {
                rotate(grid);
            }
            
            gRow = findGuard(grid)[0];
            gCol = findGuard(grid)[1];
            gDir = findGuard(grid)[2];
            
            //printGrid(grid);  // this statement slows the down the by a lot so I didn't use it
            System.out.println(gRow + ", " + gCol + "\n");
            
        }
        
        printGrid(grid);
        System.out.println();
        
        // count how many X's(places that the guard has been to) there are
        int x = 0;
        for (ArrayList<String> row : grid) {
            for (String col : row) {
                if (col.equals("X")) {
                    x += 1;
                }
            }
        }
        
        x++;
        System.out.println("ANSWER " + x);
        
        
	}
	
	
	
	public static void forward(ArrayList<ArrayList<String>> grid)
	{  
	    int[] guardPos = findGuard(grid);
	    
	    switch (grid.get(guardPos[0]).get(guardPos[1])) {
	        case "^":
	            if (guardPos[0] > 0) {
	                grid.get(guardPos[0]).set(guardPos[1], "X");
	                grid.get(guardPos[0]-1).set(guardPos[1], "^");
	            } else {
	                throw new Error("GUARD WENT OUT OF BOUNDS!");
	            }
	            break;
	            
	        case ">":
	            if (guardPos[1] < grid.get(0).size()-1) {
	                grid.get(guardPos[0]).set(guardPos[1], "X");
	                grid.get(guardPos[0]).set(guardPos[1]+1, ">");
	            } else {
	                throw new Error("GUARD WENT OUT OF BOUNDS!");
	            }
	            break;
	            
	        case "v":
	            if (guardPos[0] < grid.size()-1) {
	                grid.get(guardPos[0]).set(guardPos[1], "X");
	                grid.get(guardPos[0]+1).set(guardPos[1], "v");
	            } else {
	                throw new Error("GUARD WENT OUT OF BOUNDS!");
	            }
	            break;
	            
	        case "<":
	            if (guardPos[1] != 0) {
	                grid.get(guardPos[0]).set(guardPos[1], "X");
	                grid.get(guardPos[0]).set(guardPos[1]-1,"<");
	            } else {
	                throw new Error("GUARD WENT OUT OF BOUNDS!");
	            }
	            break;
	            
            default:
                throw new Error("GUARD NOT FOUND IN GRID!");
	    }
	}
	
	
	
	public static void rotate(ArrayList<ArrayList<String>> grid) {
	    int[] guardPos = findGuard(grid);
	        switch (grid.get(guardPos[0]).get(guardPos[1])) {
    	        case "^":
                    grid.get(guardPos[0]).set(guardPos[1], ">"); // (n,e,s,w) -> (0,1,2,3)
    	            break;
    	            
    	        case ">":
    	            grid.get(guardPos[0]).set(guardPos[1], "v");
    	            break;
    	            
    	        case "v":
    	            grid.get(guardPos[0]).set(guardPos[1], "<");
    	            break;
    	            
    	        case "<":
    	            grid.get(guardPos[0]).set(guardPos[1], "^");
    	            break;
    	            
    	        default:
    	            throw new Error("GUARD NOT FOUND IN GRID!");
	    }
	}
	
	
	
	public static int[] findGuard(ArrayList<ArrayList<String>> grid) // returns an array containing position[row, col, dir] of the guard
	{
	    int currentRow = 0;
	    for (ArrayList<String> row : grid) {
	        int currentCol = 0;
	        for (String col : row) {
	            String tile = grid.get(currentRow).get(currentCol);

	            if (tile.equals("^")) {
	                return new int[] {currentRow, currentCol, 0};
	            } else if (tile.equals(">")) {
	                return new int[] {currentRow, currentCol, 1};
	            } else if (tile.equals("v")) {
	                return new int[] {currentRow, currentCol, 2};
	            } else if (tile.equals("<")) {
	                return new int[] {currentRow, currentCol, 3};
	            }
	            
	            currentCol++;
	            
	        }
	        
	        currentRow++;
	    }
	    
	    throw new Error("GUARD NOT FOUND IN GRID!");
	}
	
	
	
	public static void printGrid(ArrayList<ArrayList<String>> grid)
	{
	    for (ArrayList<String> row : grid) {
	        for (String col : row) {
	            System.out.print(col);
	        }
	        System.out.println();
	    }
	}
}
