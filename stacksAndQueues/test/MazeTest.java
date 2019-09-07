import org.junit.Test;
import proj1.Maze;

import static org.junit.Assert.assertEquals;

public class MazeTest {

    private String queueFail = "queue test fail.";
    private String stackFail = "stack test fail.";

    @Test//(timeout = 1000)
    public void test1() {
        char[][] map = toMap("$#####\n" +
            ".....#\n" +
            "####%#\n" +
            "######");
        String result = "(0,0)(1,0)(1,1)(1,2)(1,3)(1,4)(2,4) 6";
        String result1 = "(0,0)(1,0)(1,1)(1,2)(1,3)(1,4)(2,4) 6";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));
        assertEquals(stackFail, result1, maze.solveWithStack(map));
    }

    @Test//(timeout = 1000)
    public void test2() {
        char[][] map = toMap(
            "$#########\n" +
            "..#.....##\n" +
            "#.######.#\n" +
            "#.......##\n" +
            "##.#.#%###\n" +
            "##########");
        String result = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(4,6) 12";
        String result1 = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(4,6) 10";

        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));
        assertEquals(stackFail, result1, maze.solveWithStack(map));

    }

    @Test//(timeout = 1000)
    public void test3() {
        char[][] map = toMap(
            "$#########\n" +
            "........##\n" +
            "#.######.#\n" +
            "#......%##\n" +
            "##.#.#####\n" +
            "##########");
        String result = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 18";
        String result1 = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 16";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));
        assertEquals(stackFail, result1, maze.solveWithStack(map));

    }

    private char[][] toMap(String str){
        String[] lines = str.split("\n");
        char[][] map = new char[lines.length][];
        for(int i = 0; i < lines.length; i++){
            map[i] = lines[i].toCharArray();
        }
        return map;
    }
}