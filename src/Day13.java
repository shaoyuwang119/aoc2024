import java.io.*;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.*;
import java.math.BigDecimal;

public class Day13 {

    public static void main(String[] args) throws FileNotFoundException{
        long start = System.currentTimeMillis();

        String path = "src/input.txt";
        day13(path);

        System.out.println(System.currentTimeMillis()-start + " ms");
    }

    static void day13(String p) throws FileNotFoundException {
        long tokens1 = 0;
        long tokens2 = 0;
        Scanner scan = new Scanner(new File(p));
        while (scan.hasNextLine()) {
            int ax, ay, bx, by;
            int prizeX, prizeY;
            ax = findNextInt(scan);
            ay = findNextInt(scan);
            scan.nextLine();
            bx = findNextInt(scan);
            by = findNextInt(scan);
            scan.nextLine();
            prizeX = findNextInt(scan);
            prizeY = findNextInt(scan);

            long[] sol1 = solveSystem(ax, bx, prizeX, ay, by, prizeY);
            long[] sol2 = solveSystem(ax, bx, prizeX+10000000000000L, ay, by, prizeY + 10000000000000L);
            if (sol1 != null) {
                tokens1 += sol1[0]*3 + sol1[1];
            }
            if (sol2 != null) {
                tokens2 += sol2[0]*3 + sol2[1];
            }

        }

        System.out.println("Part 1: " + tokens1);
        System.out.println("Part 2: " + tokens2);
    }

    static int findNextInt(Scanner scan) {
        while (scan.hasNext()) {
            String token = scan.next();
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher match = pattern.matcher(token);
            if (match.find()) {
                return Integer.parseInt(match.group());
            }
        }
        return 0;
    }

    static long[] solveSystem(int x1, int x2, long px, int y1, int y2, long py) {
        long[] sol = new long[2];
        long c = x1 * py - y1 * px;
        long det = (-1L *x2*y1) - (-1L *x1*y2);
        BigDecimal b0 = new BigDecimal((double) c / det);
        double b = b0.setScale(4, RoundingMode.HALF_UP).doubleValue();
        BigDecimal a0 = new BigDecimal((px - x2*b)/x1);
        double a = a0.setScale(4, RoundingMode.HALF_UP).doubleValue();
        if (b != (long) b || b < 0 || a != (long) a || a < 0) {
            return null;
        }

        sol[0] = (long) a;
        sol[1] = (long) b;
        return sol;
    }

}
