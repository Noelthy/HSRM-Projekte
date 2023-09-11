public class CountErrors {


    public static int countErrors(int[] solution, int[][] graph1, int[][] graph2) {
        int n = solution.length;
        int[][] graph3 = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int h = 0; h < n; h++){
                if(solution[i] < 0){

                } else if (solution[h] < 0) {
                    
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


}

