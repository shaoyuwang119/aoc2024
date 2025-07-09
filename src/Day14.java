import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.*;

public class Day14 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        String input = Files.readString(Path.of("src/input.txt"));
        part1(input);
        part2(input);

        System.out.println(System.currentTimeMillis()-start + " ms");
    }

    static void part1(String p) {
        int rows = 103, cols = 101;
        Grid grid = new Grid(rows, cols);

        Scanner scan = new Scanner(p);
        int[] quadrantBots = new int[4];

        while (scan.hasNextLine()) {
            int[] pos = findNextPair(scan);
            int pr = pos[0], pc = pos[1];
            int[] vel = findNextPair(scan);
            int vr = vel[0], vc = vel[1];

            int pcNew = (pc + vc * 100) % cols;
            int prNew = (pr + vr * 100) % rows;

            if (pcNew < 0) {
                pcNew = cols + pcNew;
            }
            if (prNew < 0) {
                prNew = rows + prNew;
            }

            Integer quad = findQuadrant(prNew, pcNew, rows, cols);
            if (quad != null) quadrantBots[quad]++;

            scan.nextLine();
        }

        int safetyFactor = 1;
        for (int i : quadrantBots) {
            safetyFactor *= i;
        }

        System.out.println("Part 1: " + safetyFactor);
    }

    static void part2(String p) {
        Scanner scan = new Scanner(p);
        int rows = 103, cols = 101;
        ArrayList<int[]> pos = new ArrayList<>();
        ArrayList<int[]> vel = new ArrayList<>();
        while (scan.hasNextLine()) {
            pos.add(findNextPair(scan));
            vel.add(findNextPair(scan));
            scan.nextLine();
        }
        for (int i=0; i<10000; i++) {
            Grid grid = new Grid(rows, cols);
            for (int bot=0; bot<pos.size(); bot++) {
                int rNew = pos.get(bot)[0] + vel.get(bot)[0];
                rNew %= rows;
                if (rNew < 0) {
                    rNew += rows;
                }

                int cNew = pos.get(bot)[1] + vel.get(bot)[1];
                cNew %= cols;
                if (cNew < 0) {
                    cNew += cols;
                }
                pos.get(bot)[0] = rNew;
                pos.get(bot)[1] = cNew;
                grid.set(rNew, cNew, '#');
            }

            int cluster = largestCluster(grid, pos);
            if (cluster >= 20) { // I just picked an arbitrary number that is large enough to be a statistical anomaly
                System.out.println("Part 2: " + (i+1));
                grid.print();
            }

        }

    }

    static int[] findNextPair(Scanner scan) {
        int[] pair = new int[2];
        if (scan.hasNext()) {
            String token = scan.next();
            Pattern pattern = Pattern.compile("[pv]=(-?\\d+),(-?\\d+)");
            Matcher match = pattern.matcher(token);
            if (match.find()) {
                pair[1] = Integer.parseInt(match.group(1));
                pair[0] = Integer.parseInt(match.group(2));
            }
        }
        return pair;
    }

    static Integer findQuadrant(int r, int c, int rows, int cols) {
        // top-left is quadrant 0, goes clockwise
        int midRow = rows/2;
        int midCol = cols/2;

        if (r == midRow || c == midCol) return null;

        boolean top = r < midRow;
        boolean left = c < midCol;

        if (top && left) return 0;
        if (top) return 1;
        if (!left) return 2;
        return 3;
    }

    static int largestCluster(Grid grid, ArrayList<int[]> pos) {
        int largest = 0;
        for (int[] pair : pos) {
            Coords c = new Coords(pair[0], pair[1]);
            HashSet<Character> target = new HashSet<>();
            target.add('#');
            int cur = grid.findClusterSize(c, target, new HashSet<>());
            if (cur > largest) {
                largest = cur;
            }
        }
        return largest;
    }
}
