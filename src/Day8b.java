import java.util.*;
import java.io.*;
import java.lang.*;

public class Day8b
{

	public static void main(String[] args) throws FileNotFoundException
	{
		File f = new File("input.txt");
		
		Scanner lineScanner = new Scanner(f);
		Scanner tileScanner;

		ArrayList<ArrayList<String>> grid = new ArrayList<>();
		HashMap<String, ArrayList<int[]>> freqs = new HashMap<>();
        
        
		while (lineScanner.hasNext()) {
		    grid.add(new ArrayList<String>());
			String line = lineScanner.next();
			tileScanner = new Scanner(line);
			tileScanner.useDelimiter("");
			int col = 0;
			
            while (tileScanner.hasNext()) {
                String tile = tileScanner.next();
                grid.get(grid.size()-1).add(tile);
                ArrayList<int[]> coords = freqs.get(tile);
                
                if (freqs.containsKey(tile)) {
                    coords.add(new int[] {grid.size()-1, col});
                } else if (!tile.equals(".")) {
                    freqs.put(tile, new ArrayList<>());
                    coords = freqs.get(tile);
                    coords.add(new int[] {grid.size()-1, col});
                }
                
                col++;
            }
		}
		
		printGrid(grid);
		
		int unqNodes = 0;
		
		for (ArrayList<int[]> antsList : freqs.values()) {
		    for (int[] pos : antsList) {
		        for (int[] testPos : antsList) {
		            
		            if (Arrays.equals(pos, testPos)) {
		                continue;
		            }
		            
		            int[] dist = new int[] {testPos[0]-pos[0],testPos[1]-pos[1]};
		            
		            int m = 1; // m for multiplier
		            while (true) {
		                if (pos[0]-m*dist[0] >= 0 && pos[0]-m*dist[0] < grid.size() && pos[1]-m*dist[1] >= 0 && pos[1]-m*dist[1] < grid.size()) {
		                    System.out.println(Arrays.toString(pos) + " " + Arrays.toString(dist) + " " + m);
		                    grid.get(pos[0]-m*dist[0]).set(pos[1]-m*dist[1], "#");
		                    m++;
		                    continue;
		                }
		                break;
		            }
		            
		        }
		    }
		}
		
		for (ArrayList<String> row : grid) {
		    for (String col : row) {
		        if (!col.equals(".")) { unqNodes++; }
		    }
		}
		
		printGrid(grid);
		System.out.println("\nANSWER " + unqNodes);
		
		
		
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
