import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class CountErrorsTest {


    static void runTest(int[] solution, int[][] graph1, int[][] graph2, int expected) {
        int result = CountErrors.countErrors(solution, graph1, graph2);
        assertEquals( expected, result );
    }


    @Test
    public void test1() {
        int[][] graph1 = new int[][] {
                {0,1,0,0},
                {1,0,1,1},
                {0,1,0,0},
                {0,1,0,0}
        };
        int[][] graph2 =  new int[][] {
                {0,1,0,0,0,0},
                {1,0,1,1,0,0},
                {0,1,0,0,1,0},
                {0,1,0,0,0,1},
                {0,0,1,0,0,0},
                {0,0,0,1,0,0}
        };
        // Test Case 1a (see visualization in the exercise letter)
        int[] solution = new int[] {0,1,2,3};
        int expected = 0;
        runTest(solution, graph1, graph2, expected);
        // Test Case 1b (see visualization in the exercise letter)
        int[] solution2 = new int[] {3,2,1,0};
        int expected2 = 4;
        runTest(solution2, graph1, graph2, expected2);
    }


    @Test
    public void test2() {
        int[][] graph1 = new int[][] {
                {0,1,0,0},
                {1,0,1,1},
                {0,1,0,0},
                {0,1,0,0}
        };
        int[][] graph2 = new int[][] {
                {0,0,1,0,0,0},
                {0,0,1,1,0,0},
                {1,1,0,0,1,0},
                {0,1,0,0,0,0},
                {0,0,1,0,0,1},
                {0,0,0,0,1,0}
        };
        // Test Case 2 (see visualization in the exercise letter)
        int[] solution = new int[] {0,2,1,4};
        int expected = 0;
        runTest(solution, graph1, graph2, expected);
    }


    @Test
    public void test3() {
        int[][] graph1 = new int[][] {
                {0,1,1,0,0,0,0,0},
                {1,0,0,0,0,0,0,0},
                {1,0,0,1,1,1,1,1},
                {0,0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0,0}
        };
        int[][] graph2 = new int[][] {
                {0,1,1,0,0,0,0,0,0},
                {1,0,0,1,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,1,1,1},
                {0,0,0,1,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0}
        };
        // Test Case 3 (see visualization in the exercise letter)
        int[] solution = new int[] {0,1,2,4,5,6,7,8};
        int expected = 5;
        runTest(solution, graph1, graph2, expected);
    }


    @Test
    public void test4() {
        int[][] graph1 = new int[][] {
                {0,1,1,1,1},
                {1,0,1,0,0},
                {1,1,0,1,1},
                {1,0,1,0,1},
                {1,0,1,1,0},
        };
        int[][] graph2 = new int[][] {
                // "Haus 1"
                {0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                // "Haus 2"
                {0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                // "Haus 3"
                {0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0},
                // "Haus 4"
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0},
        };

        // Test Case 4 (see visualization in the exercise letter)
        int[] solution = new int[] {0,1,2,3,4};
        int expected = 2;
        runTest(solution, graph1, graph2, expected);
    }

}
