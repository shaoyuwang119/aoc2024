import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Grid {
    private char[][] grid;

    private int rows;
    private int cols;

    public Grid(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            rows = lines.size();
            cols = lines.getFirst().length();
            grid = new char[rows][cols];

            for (int r = 0; r < rows; r++) {
                String line = lines.get(r);
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = line.charAt(c);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file.");
        } catch (IOException e) {
            System.out.println("IO exception occurred.");
        }
    }

    public Grid(int r, int c) {
        rows = r;
        cols = c;
        grid = new char[rows][cols];
    }

    public char get(int row, int col) {
        if (inBounds(row, col)) {
            return grid[row][col];
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + row + "," + col + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public char get(Coords pos) {
        if (inBounds(pos.r(), pos.c())) {
            return grid[pos.r()][pos.c()];
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + pos.r() + "," + pos.c() + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char[] getRow(int rowIndex) {
        return grid[rowIndex].clone();
    }

    public char[] getCol(int colIndex) {
        char[] col = new char[cols];
        for (int i=0; i<cols; i++) {
            col[i] = get(i, colIndex);
        }
        return col;
    }

    public void set(int row, int col, char replacement) {
        if (inBounds(row, col)) {
            grid[row][col] = replacement;
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + row + "," + col + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public void set(Coords pos, char replacement) {
        if (inBounds(pos.r(), pos.c())) {
            grid[pos.r()][pos.c()] = replacement;
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + pos.r() + "," + pos.c() + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public boolean inBounds(int row, int col) { return row >= 0 && row < rows && col >= 0 && col < cols; }
    public boolean inBounds(Coords pos) { return pos.r() >= 0 && pos.r() < rows && pos.c() >= 0 && pos.c() < cols; }

    public char[][] asArray() {
        char[][] newGrid = new char[grid.length][];
        for (int i=0; i<grid.length; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }

    public void print() {
        for (char[] r : grid) {
            for (char c : r) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public void fill(char c) {
        for (int i=0; i<rows; i++) {
            Arrays.fill(grid[i], c);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid other)) return false;
        return Arrays.deepEquals(this.grid, other.grid);
    }

}