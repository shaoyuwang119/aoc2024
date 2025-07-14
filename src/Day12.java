import java.nio.file.Path;
import java.util.*;

public class Day12 {

    private static final Grid grid = new Grid(Path.of("src/input.txt"));
    private static ArrayList<HashMap<Coords, boolean[]>> regions = new ArrayList<>();

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1(){
        long totalPrice = 0;
        HashSet<Coords> visited = new HashSet<>();
        Coords currentPos;
        grid.print();

        for (int i=0; i<grid.getRows(); i++) {
            for (int j=0; j<grid.getCols(); j++) {
                currentPos = new Coords(i, j);
                if(visited.contains(new Coords(i, j))) {
                    continue;
                }
                regions.add(new HashMap<>());
                int[] pair = findPlot(currentPos, visited, grid.get(currentPos));
                totalPrice += (long) pair[0] * pair[1];
            }
        }

        System.out.println("Part 1: " + totalPrice);
    }

    public static void part2() {
        int totalPrice = 0;

        for (HashMap<Coords, boolean[]> region : regions) {
            int sides = findSides(region);
            totalPrice += sides * region.size();
        }

        System.out.println("Part 2: " + totalPrice);
    }

    public static int[] findPlot(Coords pos, HashSet<Coords> visited, char testPlant) {
        int[] pair = {0, 0}; // i0 = area, i1 = perimeter

        if (!grid.inBounds(pos)) {
            return new int[] {0, 1};
        }

        char curPlant = grid.get(pos);

        if (visited.contains(pos) && curPlant == testPlant) {
            return new int[] {0, 0};
        }

        if (curPlant != testPlant) {
            return new int[] {0, 1};
        }

        visited.add(pos);
        pair[0]++;

        int[][] directions = {{-1,0}, {0,1}, {1,0}, {0,-1}};
        boolean[] exposedSides = new boolean[4];

        for (int i=0; i<4; i++) {
            Coords tempPos = pos.add(directions[i][0], directions[i][1]);
            int[] tempPair = findPlot(tempPos, visited, curPlant);
            pair[0] += tempPair[0];
            pair[1] += tempPair[1];


            if (!grid.inBounds(tempPos) || grid.get(tempPos) != curPlant) {
                exposedSides[i] = true;
            }
        }
        regions.getLast().put(pos, exposedSides);

        return pair;

    }

    public static int findSides(HashMap<Coords, boolean[]> region) {
        // algorithm:
        // each tile will have a boolean array of size 4 which
        // shows which sides of the tile are exposed
        // (it goes clockwise by sides: top-right-bottom-left)
        // (this will be stored in a hashmap)
        // consecutive horizontal tiles in the same id will have top and bottom side array items
        // cleared and merged into 1 side
        // same goes for consecutive vertical tiles, but it's left and right

        // first checks horizontal tiles (top and bottom)
        int totalSides = 0;
        int[][] dirs = {{0,1}, {1,0}, {0,1}, {1,0}}; // the directions that the testPos will travel in
        for (Coords pos : region.keySet()) {
            for (int i=0; i<4; i++) {
                boolean currentSide = region.get(pos)[i];
                if (!currentSide) {
                    continue; // skip if the current side is not an exposed side
                }

                Coords testPos = pos;
                boolean testSide = true;
                // scan along a straight line, and turn all sides false until it arrives at a tile that does not share an exposed side
                // or arrives at open space
                while (region.containsKey(testPos) && testSide) {
                    region.get(testPos)[i] = false;
                    testPos = testPos.add(dirs[i][0], dirs[i][1]);
                    if (region.containsKey(testPos)) {
                        testSide = region.get(testPos)[i];
                    }
                }
                // reset the test variables now that we go the opposite way
                testPos = pos;
                testSide = true;
                while (region.containsKey(testPos) && testSide) {
                    region.get(testPos)[i] = false;
                    testPos = testPos.add(-dirs[i][0], -dirs[i][1]);
                    if (region.containsKey(testPos)) {
                        testSide = region.get(testPos)[i];
                    }
                }
                totalSides++;
            }
        }
        return totalSides;
    }
}
