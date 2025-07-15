import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day15 {

    public static void main(String[] args) {
        String input = "src/input.txt";
        part1(input);
        part2(input);
    }

    static void part1(String path) {
        Grid grid;
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            throw new Error("An error occurred.");
        }

        StringBuilder gridStr = new StringBuilder();
        StringBuilder movesSB = new StringBuilder();
        boolean addingToGrid = true;

        Coords robotPos = new Coords();

        for (int l=0; l<lines.size(); l++) {
            String line = lines.get(l);
            if (line.isEmpty()) addingToGrid = false;
            if (addingToGrid) {
                gridStr.append(line).append("\n");
                if (line.indexOf('@') >= 0) {
                    robotPos = new Coords(l,line.indexOf('@'));
                }
            }
            else movesSB.append(line);
        }

        grid = new Grid(gridStr.toString());
        String moves = movesSB.toString();

        //grid.print();

        for (char c : moves.toCharArray()) {
            Coords mvt = movementCoords(c);
            Coords newRobotPos = robotPos.add(mvt);
            moveCircleBoxes(grid, newRobotPos, mvt);
            if (moveRobot(grid, robotPos, mvt)) {
                robotPos = newRobotPos;
            }
        }
        //grid.print();
        System.out.println("Part 1: " + calculateSum(grid));

    }

    static void part2(String path) {
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            throw new Error("An error occurred.");
        }

        int gridRows = 0;
        while (gridRows < lines.size() && !lines.get(gridRows).isEmpty()) {
            gridRows++;
        }

        Grid grid = new Grid(gridRows, lines.getFirst().length()*2); // 2x wide
        StringBuilder movesSB = new StringBuilder();
        boolean addingToGrid = true;

        Coords robotPos = new Coords();

        for (int l=0; l<lines.size(); l++) {
            String line = lines.get(l);
            if (line.isEmpty()) addingToGrid = false;
            if (addingToGrid) {
                for (int i=0; i<line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        grid.set(l, 2*i, '#');
                        grid.set(l, 2*i+1, '#');
                    } else if (line.charAt(i) == '.') {
                        grid.set(l, 2*i, '.');
                        grid.set(l, 2*i+1, '.');
                    } else if (line.charAt(i) == 'O') {
                        grid.set(l, 2*i, '[');
                        grid.set(l, 2*i+1, ']');
                    } else {
                        grid.set(l, 2*i, '@');
                        grid.set(l, 2*i+1, '.');
                        robotPos = new Coords(l, 2*i);
                    }
                }
            }
            else movesSB.append(line);
        }

        //grid.print();
        String moves = movesSB.toString();

        for (char c : moves.toCharArray()) {
            Coords mvt = movementCoords(c);
            Coords newRobotPos = robotPos.add(mvt);
            boolean moveable = squareBoxesMoveable(grid, newRobotPos, mvt);
            if (moveable) {
            moveSquareBoxes(grid, newRobotPos, mvt);
            }
            if (moveRobot(grid, robotPos, mvt)) {
                robotPos = newRobotPos;
            }
            //grid.print();
        }
        System.out.println("Part 2: " + calculateSum(grid));
    }

    static Coords movementCoords(char c) {
        return switch (c) {
            case '^' -> new Coords(-1, 0);
            case '>' -> new Coords(0, 1);
            case 'v' -> new Coords(1, 0);
            default -> new Coords(0, -1);
        };
    }

    static boolean moveCircleBoxes(Grid grid, Coords pos, Coords mvt) {
        Coords newPos = pos.add(mvt);

        if (grid.get(pos) == '.') {
            return true;
        }
        if (grid.get(pos) == '#') {
            return false;
        }

        if (moveCircleBoxes(grid, newPos, mvt)) {
            grid.set(newPos, 'O');
            grid.set(pos, '.');
            return true;
        }
        return false;
    }

    static void shiftSquareBox(Grid grid, Coords pos, Coords mvt) {
        Coords newPos = pos.add(mvt);
        if (grid.get(pos) == ']') {
            grid.set(pos, '.');
            grid.set(newPos, ']');
            if(!mvt.equals(new Coords(0, -1))) grid.set(pos.add(0,-1), '.');
            grid.set(newPos.add(0,-1), '[');
        } else if (grid.get(pos) == '[') {
            grid.set(pos, '.');
            grid.set(newPos, '[');
            if(!mvt.equals(new Coords(0, 1))) grid.set(pos.add(0,1), '.');
            grid.set(newPos.add(0,1), ']');
        }
    }

    static boolean moveSquareBoxes(Grid grid, Coords pos, Coords mvt) {
        if (grid.get(pos) == '.') {
            return true;
        }

        if (mvt.r() == 0) { // horizontal movement
            Coords newPos = pos.add(mvt); // extend it since grid is 2x wide
            if (moveSquareBoxes(grid, newPos, mvt)) {
                shiftSquareBox(grid, pos, mvt);
                return true;
            }
            return false;

        } else { // vertical movement

            Coords pos1 = pos;
            if (grid.get(pos) == ']') pos1 = pos1.add(0,-1);
            Coords pos2 = pos1.add(0,1);

            if (moveSquareBoxes(grid, pos1.add(mvt), mvt) && moveSquareBoxes(grid, pos2.add(mvt), mvt)) {
                shiftSquareBox(grid, pos1, mvt);
                shiftSquareBox(grid, pos2, mvt);
                return true;
            }
        }
        return false;
    }

    static boolean squareBoxesMoveable(Grid grid, Coords pos, Coords mvt) {
        if (grid.get(pos) == '#') {
            return false;
        }
        if (grid.get(pos) == '.') {
            return true;
        }
        if (mvt.r() == 0) {
            return squareBoxesMoveable(grid, pos.add(mvt).add(mvt), mvt);
        } else {
            Coords pos1 = pos;
            if (grid.get(pos) == ']') pos1 = pos1.add(0,-1);
            Coords pos2 = pos1.add(0,1);
            return squareBoxesMoveable(grid, pos1.add(mvt), mvt) && squareBoxesMoveable(grid, pos2.add(mvt), mvt);
        }
    }

    static boolean moveRobot(Grid grid, Coords pos, Coords mvt) {
        if (grid.get(pos) != '@') {
            throw new IllegalArgumentException("Robot not found at coords" + pos);
        }
        Coords newPos = pos.add(mvt);
        if (grid.get(newPos) == '.') {
            grid.set(newPos, '@');
            grid.set(pos, '.');
            return true;
        }
        return false;
    }

    static int calculateSum(Grid grid) {
        int sum=0;
        for (int row=0; row<grid.rows(); row++) {
            for (int col = 0; col <grid.cols(); col++) {
                char c = grid.get(row, col);
                if (c == 'O' || c == '[') {
                    sum += row*100 + col;
                }
            }
        }
        return sum;
    }
}
