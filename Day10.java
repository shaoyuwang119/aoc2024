import java.io.*;
import java.util.*;

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Grid grid = new Grid("input.txt");

        part1(grid);
        part2(grid);

    }

    public static void part1(Grid grid) {
        int totalScore = 0;
        HashSet<Coords> visited;

        for (int r=0; r<grid.rows; r++) {
            for (int c=0; c<grid.cols; c++) {
                Coords pos = new Coords(r, c);
                if (grid.get(r, c) == '0') {
                    visited = new HashSet<>();
                    int curScore = findScore(grid, pos, visited);
                    totalScore += curScore;
                }
            }
        }

        System.out.println("(Part 1) Total Score: " + totalScore);
    }

    public static void part2(Grid grid) {
        int totalRating = 0;

        for (int r=0; r<grid.rows; r++) {
            for (int c=0; c<grid.cols; c++) {
                Coords pos = new Coords(r, c);
                if (grid.get(r, c) == '0') {
                    int curRating = findRating(grid, pos);
                    totalRating += curRating;
                }
            }
        }
        System.out.println("(Part 2) Total Rating: " + totalRating);
    }

    public static int findScore(Grid grid, Coords curPos, Set<Coords> visited) {
        int score = 0;
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        Coords nextPos;
        
        visited.add(curPos);

        if (grid.get(curPos) == '9') {
            return 1;
        }

        for (int[] dir : dirs) {
            nextPos = new Coords(curPos.r + dir[0], curPos.c + dir[1]);
            if (grid.inBounds(nextPos) && grid.get(nextPos) == grid.get(curPos)+1 && !visited.contains(nextPos)) {
                score += findScore(grid, nextPos, visited);
            }
        }

        return score;
    }

    public static int findRating(Grid grid, Coords curPos) {
        int score = 0;
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        Coords nextPos;

        if (grid.get(curPos) == '9') {
            return 1;
        }

        for (int[] dir : dirs) {
            nextPos = new Coords(curPos.r + dir[0], curPos.c + dir[1]);
            if (grid.inBounds(nextPos) && grid.get(nextPos) == grid.get(curPos)+1) {
                score += findRating(grid, nextPos);
            }
        }

        return score;
    }
}
