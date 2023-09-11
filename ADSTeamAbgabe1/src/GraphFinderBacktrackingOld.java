public class GraphFinderBacktrackingOld {

    public static int countErrors(int[] solution, int[][] graph1, int[][] graph2) {
        int n = solution.length;
        int[][] graph3 = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int h = 0; h < n; h++){
                if(solution[i] < 0){
                    continue;
                } else if (solution[h] < 0) {
                    continue;
                } else{
                    graph3[i][h] = graph2[solution[i]][solution[h]];
                }
            }
        }
        int result = 0;
        for(int j = 0; j < n; j++){
            for(int k = 0; k < j; k++){
                if(graph1[j][k] != graph3[j][k]){
                    result++;
                }
            }
        }
        return result;
    }

    public static int[] findBacktracking(int[][] graph1, int[][] graph2) {
        int n = graph1.length;

        int[] loesung = new int[n];

        for(int i = 0; i < n; i++){
            loesung[i] = -1;
        }

        int[] beste_loesung = new int[n];
        for(int i = 0; i < n; i++){
            beste_loesung[i] = -1;
        }

        backtrack(graph1, graph2, loesung, beste_loesung, 0);
        return beste_loesung;
    }

    private static void backtrack(int[][] graph1, int[][] graph2, int[] loesung, int[] beste_loesung, int zustand){
        int n = graph1.length;

        if(zustand == n){
            if (countErrors(loesung, graph1, graph2) < countErrors(beste_loesung, graph1, graph2)) {
                for(int i = 0; i < n; i++){
                    beste_loesung[i] = loesung[i];
                }
            }
            return;
        }

        for(int i = 0; i < n; i++){
            loesung[zustand] = i;
            backtrack(graph1, graph2, loesung, beste_loesung, zustand + 1);
            loesung[zustand] = -1;
        }
    }
}