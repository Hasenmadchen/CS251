package proj1;


/**
 * proj1.Maze.java
 * <p>
 * Project 1, part 2. Solve the maze problem with stack and queue.
 */
public class Maze {

    private final char SPACE = '.';
    private final char START = '$';
    private final char END = '%';

    /**
     * Finds the path using {@code proj1.MyStack}.
     * Returns the path and number of spaces checked as {@code String}.
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

    /**
     * @param map {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    public String solveWithStack(char[][] map) {
        if (map == null) {
            return "no way";
        }
        MyStack<Coordinate> unprocessed = new MyStack<>();
        unprocessed.push(new Coordinate(0, 0));
        MyStack<Coordinate> processed = new MyStack<>();
        int[] spacesChecked = {0};
        MyStack<Coordinate> result = processOpenSpaces(map, unprocessed, processed, spacesChecked);
        if (result == null) {
            return "no way";
        }
        StringBuilder solution = new StringBuilder();
        while (!result.isEmpty()) {
            solution.append(result.pop().toString());
        }
        solution.append(' ').append(spacesChecked[0]);
        return solution.toString();
    }

    private MyStack<Coordinate> processOpenSpaces(char[][] map, MyStack<Coordinate> unprocessed, MyStack<Coordinate> processed,
                                      int[] spacesChecked) {
        MyStack<Coordinate> duds = new MyStack<>();
        MyStack<Coordinate> result;
        outer:
        while (true) {
            Coordinate coord = unprocessed.pop();
            if (coord == null) {
                result = null;
                break;
            }
            if (!coord.isValid(map)) {
                continue;
            }

            Coordinate down = new Coordinate(coord.x + 1, coord.y);
            Coordinate right = new Coordinate(coord.x, coord.y + 1);
            Coordinate up = new Coordinate(coord.x - 1, coord.y);
            Coordinate left = new Coordinate(coord.x, coord.y - 1);

            if(!processed.contains(coord)) {
                processed.push(coord);
                spacesChecked[0]++;
            }
            boolean spaceFound = false;
            for (Coordinate neighbor : new Coordinate[]{down, right, up, left}) {
                char space = addOpenSpace(neighbor, map, processed, unprocessed, duds);
                if (space == END) {
                    processed.push(neighbor);
                    result = processed;
                    break outer;
                }
                spaceFound = spaceFound || space == SPACE;
            }
        }
        if(result != null) {
            result = removeDead(result, map);
        }
        return result;
    }

    private char addOpenSpace(Coordinate coord, char[][] map, MyStack<Coordinate> processed,
                              MyStack<Coordinate> unprocessed, MyStack<Coordinate> duds) {
        if (!coord.isValid(map)) {
            return '\0';
        }

        if (processed.contains(coord) || duds.contains(coord)) {
            return '\0';
        }
        if ((((map[coord.x][coord.y] == SPACE || map[coord.x][coord.y] == END) && (coord.x != 0 || coord.y != 0)) ||
            (map[coord.x][coord.y] == START && coord.x == 0 && coord.y == 0))) {
            if (unprocessed != null) {
                unprocessed.push(coord);
            }
            return map[coord.x][coord.y];
        }
        return '\0';
    }

    private MyStack<Coordinate> removeDead(MyStack<Coordinate> processed, char[][] map) {
        MyStack<Coordinate> result = new MyStack<>();
        Coordinate csr = null;
        while (!processed.isEmpty()) {
            Coordinate coord = processed.pop();
            if (csr != null) {
                Coordinate down = new Coordinate(csr.x + 1, csr.y);
                if (down.isValid(map) && coord.equals(down)) {
                    result.push(coord);
                    csr = coord;
                } else {
                    Coordinate right = new Coordinate(csr.x, csr.y + 1);
                    if (right.isValid(map) && coord.equals(right)) {
                        result.push(coord);
                        csr = coord;
                    } else {
                        Coordinate up = new Coordinate(csr.x - 1, csr.y);
                        if (up.isValid(map) && coord.equals(up)) {
                            result.push(coord);
                            csr = coord;
                        } else {
                            Coordinate left = new Coordinate(csr.x, csr.y - 1);
                            if (left.isValid(map) && coord.equals(left)) {
                                result.push(coord);
                                csr = coord;
                            }
                        }
                    }
                }
            } else {
                csr = coord;
                result.push(csr);
            }
        }
        return result;
    }

    /**
     * @param map {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    public String solveWithQueue(char[][] map) {
        if (map == null) {
            return "no way";
        }
        MyQueue<Coordinate> unprocessed = new MyQueue<>();
        unprocessed.enqueue(new Coordinate(0, 0));
        MyQueue<Coordinate> processed = new MyQueue<>();
        int[] spacesChecked = {0};
        processed = processOpenSpaces(map, unprocessed, processed, spacesChecked);
        if(processed == null) {
            return "no way";
        }
        StringBuilder solution = new StringBuilder();
        while (!processed.isEmpty()) {
            solution.insert(0, processed.dequeue().toString());
        }
        solution.append(' ').append(spacesChecked[0]);
        return solution.toString();
    }

    private MyQueue<Coordinate> processOpenSpaces(char[][] map, MyQueue<Coordinate> unprocessed,
                                                  MyQueue<Coordinate> processed, int[] spacesChecked) {
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

            if(!processed.contains(coord)) {
                processed.enqueue(coord);
                spacesChecked[0]++;
            }
            boolean spaceFound = false;
            for (Coordinate neighbor : new Coordinate[]{down, right, up, left}) {
                char space = addOpenSpace(neighbor, map, processed, unprocessed, duds);
                if (space == END) {
                    processed.enqueue(neighbor);
                    result = true;
                    break outer;
                }
                spaceFound = spaceFound || space == SPACE;
            }
        }

        if (result) {
            return removeDead(processed, map);
        }
        return null;
    }

    private char addOpenSpace(Coordinate coord, char[][] map, MyQueue<Coordinate> processed,
                              MyQueue<Coordinate> unprocessed, MyQueue<Coordinate> duds) {
        if (!coord.isValid(map)) {
            return '\0';
        }

        if (processed.contains(coord) || duds.contains(coord)) {
            return '\0';
        }
        if ((((map[coord.x][coord.y] == SPACE || map[coord.x][coord.y] == END) && (coord.x != 0 || coord.y != 0)) ||
            (map[coord.x][coord.y] == START && coord.x == 0 && coord.y == 0))) {
            if (unprocessed != null) {
                unprocessed.enqueue(coord);
            }
            return map[coord.x][coord.y];
        }
        return '\0';
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
}