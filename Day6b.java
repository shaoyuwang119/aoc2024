import java.util.*;
import java.io.*;
import java.lang.*;

public class Day6b
{
    
	public static void main(String[] args) throws FileNotFoundException 
	{
	    
		Scanner lineScanner = new Scanner(new File("input.txt"));
		
		ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> gridCopy = new ArrayList<ArrayList<String>>();
		ArrayList<int[]> path = new ArrayList<int[]>();
		
		int size = 0;

        while (lineScanner.hasNextLine()) {
            String curLine = lineScanner.nextLine();
            size++;
            
            grid.add(new ArrayList<String>());
            gridCopy.add(new ArrayList<String>());
            Scanner tileScanner = new Scanner(curLine);
            tileScanner.useDelimiter("");
            
            while (tileScanner.hasNext()) {
                String tile = tileScanner.next();
                grid.get(size-1).add(tile);
                gridCopy.get(size-1).add(tile);
            }
            
        }
        
        printGrid(grid);
        System.out.println();
        int[] startPos = findGuard(grid);
        
        int gRow = findGuard(grid)[0];
        int gCol = findGuard(grid)[1];
        int gDir = findGuard(grid)[2];
        path.add(new int[] {gRow, gCol});
        
        while (gRow > 0 && gRow < size-1 && gCol > 0 && gCol < size-1) {
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
            
            System.out.println(gRow + ", " + gCol + "\n");
            
        }
        
        
        // count how many X's(places that the guard has been to) there are
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (grid.get(row).get(col).equals("X") && !(row == startPos[0] && col == startPos[1])) {
                    path.add(new int[] {row,col});
                }
            }
        }
        path.add(new int[] {findGuard(grid)[0], findGuard(grid)[1]});
        
        printGrid(grid);
        
        for (int[] i : path) {
            System.out.print(Arrays.toString(i));
        }

        System.out.println("\n\nUnique Tiles: " + path.size());
        System.out.println(Arrays.toString(startPos));
        
        
        // check through each tile on the guard path to see if placing an obstacle there will cause loop 
        int obstPositions = 0;
        path.remove(0); // remove the first position
        
        
        for (int[] pos : path) {
            if (isLoop(pos, gridCopy)) {
                obstPositions++;
            }
            //printGrid(gridCopy);
        }
        
        System.out.println(obstPositions);
        
        
	}
	
	
	
	public static boolean isLoop(int[] obstPos, ArrayList<ArrayList<String>> grid) {
	    ArrayList<ArrayList<String>> testGrid = new ArrayList<ArrayList<String>>();
	    
	    // perform deep copy of grid onto testGrid
	    for (ArrayList<String> row : grid) {
	        testGrid.add(new ArrayList<String>());
	        for (String col : row) {
	            testGrid.get(testGrid.size()-1).add(col);
	        }
	    }
	    
	    ArrayList<ArrayList<Integer>> testPath = new ArrayList<ArrayList<Integer>>();
	    
	    testGrid.get(obstPos[0]).set(obstPos[1], "#");
	    
	    int size = testGrid.size();
	    printGrid(testGrid);
	    System.out.println("-");
	    int gRow = findGuard(testGrid)[0];
        int gCol = findGuard(testGrid)[1];
        int gDir = findGuard(testGrid)[2];
        
        while (gRow > 0 && gRow < size-1 && gCol > 0 && gCol < size-1) {
            if (gDir == 0 && !testGrid.get(gRow-1).get(gCol).equals("#")) {
                forward(testGrid);
                
            } else if (gDir == 1 && !testGrid.get(gRow).get(gCol+1).equals("#")) {
                forward(testGrid);
            } else if (gDir == 2 && !testGrid.get(gRow+1).get(gCol).equals("#")) {
                forward(testGrid);
            } else if (gDir == 3 && !testGrid.get(gRow).get(gCol-1).equals("#")) {
                forward(testGrid);
            } else {
                rotate(testGrid);
            }
            
            ArrayList<Integer> pos = new ArrayList<>();
            pos.add(findGuard(testGrid)[0]);
            pos.add(findGuard(testGrid)[1]);
            pos.add(findGuard(testGrid)[2]);
            
            gRow = findGuard(testGrid)[0];
            gCol = findGuard(testGrid)[1];
            gDir = findGuard(testGrid)[2];
            
            pos.add(findGuard(testGrid)[0]);
            pos.add(findGuard(testGrid)[1]);
            pos.add(findGuard(testGrid)[2]);
            
            if (testPath.contains(pos)) {
                printGrid(testGrid);
                System.out.println(Arrays.toString(obstPos));
                System.out.println("THIS IS A LOOP!!!\n");
                return true;
            } else {
                testPath.add(pos);
            }
	    
	    }
	    
	    printGrid(testGrid);
        System.out.println(Arrays.toString(obstPos) + "\n");
	    
	    return false;
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
	            if (guardPos[1] > 0) {
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
