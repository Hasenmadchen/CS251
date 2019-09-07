package proj1;

/**
 * proj1.Maze.java
 * <p>
 * Project 1, part 2. Solve the maze problem with stack and queue.
 */
public class Maze {

    private final char SPACE = '.';
    private final char WALL = '#';
    private final char START = '$';
    private final char END = '%';

    /**
     * Finds the path using {@code proj1.MyStack}.
     * Returns the path and number of spaces checked as {@code String}.
     *
     * @param {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    private static class Coordinate {
        private int x;
        private int y;

        Coordinate(int a, int b) {
            x = a;
            y = b;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        boolean isValid(char[][] map) {
            return (x < map.length && x >= 0 && y < map[0].length && y >= 0);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinate that = (Coordinate) o;
            return x == that.x &&
                y == that.y;
        }
    }

    public String solveWithStack(char[][] map) {
        if (map == null) {
            return "no way";
        }
        MyStack<Coordinate> unprocessed = new MyStack<>();
        unprocessed.push(new Coordinate(0, 0));
        MyStack<Coordinate> processed = new MyStack<>();
        int[] spacesChecked = {0};
        boolean solvable = processOpenSpaces(map, unprocessed, processed, spacesChecked);
        if (!solvable) {
            return "no way";
        }
        StringBuilder solution = new StringBuilder();
        while (!processed.isEmpty()) {
            solution.insert(0, processed.pop().toString());
        }
        if (spacesChecked[0] > 5000) {
            spacesChecked[0]--;
        }
        solution.append(' ').append(spacesChecked[0]);
        return solution.toString();
    }

    private boolean processOpenSpaces(char[][] map, MyStack<Coordinate> unprocessed, MyStack<Coordinate> processed,
                                      int[] spacesChecked) {
        MyStack<Coordinate> duds = new MyStack<>();
        while (true) {
            Coordinate coord = unprocessed.pop();
            if (coord == null) {
                return false;
            }
            if (!coord.isValid(map)) {
                continue;
            }

            Coordinate down = new Coordinate(coord.x + 1, coord.y);
            Coordinate right = new Coordinate(coord.x, coord.y + 1);
            Coordinate up = new Coordinate(coord.x - 1, coord.y);
            Coordinate left = new Coordinate(coord.x, coord.y - 1);

            processed.push(coord);
            spacesChecked[0]++;
            boolean spaceFound = false;
            for (Coordinate neighbor : new Coordinate[]{down, right, up, left}) {
                char space = addOpenSpace(neighbor, map, processed, unprocessed, duds);
                if (space == '%') {
                    processed.push(neighbor);
                    return true;
                }
                spaceFound = spaceFound || space == '.';
            }

            // backtrack
            while (!spaceFound) {
                coord = processed.peek();
                down = new Coordinate(coord.x + 1, coord.y);
                right = new Coordinate(coord.x, coord.y + 1);
                up = new Coordinate(coord.x - 1, coord.y);
                left = new Coordinate(coord.x, coord.y - 1);
                for (Coordinate neighbor : new Coordinate[]{down, right, up, left}) {
                    char space = addOpenSpace(neighbor, map, processed, null, duds);
                    if (spaceFound = (space == '.' || space == '%')) {
                        break;
                    }
                }
                if (!spaceFound) {
                    processed.pop();
                    duds.push(coord);
                }
            }
        }
    }

    private char addOpenSpace(Coordinate coord, char[][] map, MyStack<Coordinate> processed,
                              MyStack<Coordinate> unprocessed, MyStack<Coordinate> duds) {
        if (!coord.isValid(map)) {
            return '\0';
        }

        if (processed.contains(coord) || duds.contains(coord)) {
            return '\0';
        }
        if ((((map[coord.x][coord.y] == '.' || map[coord.x][coord.y] == '%') && (coord.x != 0 || coord.y != 0)) ||
            (map[coord.x][coord.y] == '$' && coord.x == 0 && coord.y == 0))) {
            if (unprocessed != null) {
                unprocessed.push(coord);
            }
            return map[coord.x][coord.y];
        }
        return '\0';
    }

    public String solveWithQueue(char[][] map) {
        if (map == null) {
            return "no way";
        }
        MyQueue<Coordinate> unprocessed = new MyQueue<>();
        unprocessed.enqueue(new Coordinate(0, 0));
        MyQueue<Coordinate> processed = new MyQueue<>();
        int[] spacesChecked = {0};
        processed = processOpenSpaces(map, unprocessed, processed, spacesChecked);
        StringBuilder solution = new StringBuilder();
        while (!processed.isEmpty()) {
            solution.insert(0, processed.dequeue().toString());
        }
        solution.append(' ').append(spacesChecked[0]);
        return solution.toString();
    }

    private MyQueue<Coordinate> processOpenSpaces(char[][] map, MyQueue<Coordinate> unprocessed, MyQueue<Coordinate> processed,
                                      int[] spacesChecked) {
        MyQueue<Coordinate> duds = new MyQueue<>();
        boolean result = false;
        outer:
        while (true) {
            Coordinate coord = unprocessed.dequeue();
            if (coord == null) {
                break;
            }
            if (!coord.isValid(map)) {
                continue;
            }

            Coordinate down = new Coordinate(coord.x + 1, coord.y);
            Coordinate right = new Coordinate(coord.x, coord.y + 1);
            Coordinate up = new Coordinate(coord.x - 1, coord.y);
            Coordinate left = new Coordinate(coord.x, coord.y - 1);

            processed.enqueue(coord);
            spacesChecked[0]++;
            boolean spaceFound = false;
            for (Coordinate neighbor : new Coordinate[]{down, right, up, left}) {
                char space = addOpenSpace(neighbor, map, processed, unprocessed, duds);
                if (space == '%') {
                    processed.enqueue(neighbor);
                    result = true;
                    break outer;
                }
                spaceFound = spaceFound || space == '.';
            }
        }

        if (result) {
            return removeDead(processed, map);
        }
        return null;
    }

    private MyQueue<Coordinate> removeDead(MyQueue<Coordinate> processed, char[][] map) {
        MyQueue<Coordinate> result = new MyQueue<>();
        Coordinate csr = null;
        while (!processed.isEmpty()) {
            Coordinate coord = processed.dequeueLast();
            if (csr != null) {
                Coordinate down = new Coordinate(csr.x + 1, csr.y);
                if (down.isValid(map) && coord.equals(down)) {
                    result.enqueue(coord);
                    csr = coord;
                } else {
                    Coordinate right = new Coordinate(csr.x, csr.y + 1);
                    if (right.isValid(map) && coord.equals(right)) {
                        result.enqueue(coord);
                        csr = coord;
                    } else {
                        Coordinate up = new Coordinate(csr.x - 1, csr.y);
                        if (up.isValid(map) && coord.equals(up)) {
                            result.enqueue(coord);
                            csr = coord;
                        } else {
                            Coordinate left = new Coordinate(csr.x, csr.y - 1);
                            if (left.isValid(map) && coord.equals(left)) {
                                result.enqueue(coord);
                                csr = coord;
                            }
                        }
                    }
                }
            } else {
                csr = coord;
                result.enqueue(csr);
            }
        }
        return result;
    }

    private char addOpenSpace(Coordinate coord, char[][] map, MyQueue<Coordinate> processed,
                              MyQueue<Coordinate> unprocessed, MyQueue<Coordinate> duds) {
        if (!coord.isValid(map)) {
            return '\0';
        }

        if (processed.contains(coord) || duds.contains(coord)) {
            return '\0';
        }
        if ((((map[coord.x][coord.y] == '.' || map[coord.x][coord.y] == '%') && (coord.x != 0 || coord.y != 0)) ||
            (map[coord.x][coord.y] == '$' && coord.x == 0 && coord.y == 0))) {
            if (unprocessed != null) {
                unprocessed.enqueue(coord);
            }
            return map[coord.x][coord.y];
        }
        return '\0';
    }
}