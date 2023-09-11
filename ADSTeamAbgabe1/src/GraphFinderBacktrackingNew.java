public class GraphFinderBacktrackingNew {

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
        //Initialisierung von n und m
        int n = graph1.length;
        int m = graph2.length;
        int [] loesung = new int [n];
        boolean [] verwendet = new boolean[m];
        for(int i = 0; i < n; i++){
            loesung[i] = -1;
        }

        return backtrack(loesung, verwendet, graph1,graph2);
    }

    public static int[] backtrack(int[] bisherigeLoesung, boolean[] bisherVerwendet, int[][] graph1, int[][] graph2) {
        // Initialisierung von n und m
        int n = graph1.length;
        int m = graph2.length;

        // Kopien der ursprünglichen Arrays erstellen
        int[] neueLoesung = bisherigeLoesung.clone();
        boolean[] verwendet = bisherVerwendet.clone();

        int wenigsteFehler = Integer.MAX_VALUE;
        int[] besteLoesung = neueLoesung.clone();
        for (int i = 0; i < n; i++) {
            if (neueLoesung[i] == -1) {
                for (int j = 0; j < m; j++) {
                    if (!verwendet[j]) {
                        verwendet[j] = true;
                        int[] loesungsVersuch = neueLoesung.clone();
                        loesungsVersuch[i] = j;
                        loesungsVersuch = backtrack(loesungsVersuch, verwendet, graph1, graph2);
                        verwendet[j] = false;

                        // Vergleiche loesungsVersuch mit der besten Lösung
                        if (countErrors(loesungsVersuch, graph1, graph2) < wenigsteFehler) {
                            besteLoesung = loesungsVersuch.clone();
                            wenigsteFehler = countErrors(besteLoesung, graph1, graph2);
                        }
                    }
                }
                neueLoesung = besteLoesung;
            }
        }
        return neueLoesung;
    }

}