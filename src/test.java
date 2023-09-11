import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LanguageDetector {


    public static class HashMap<T> {

        public class Entry {
            String key;
            T value;

            Entry(String key, T value) {
                this.key = key;
                this.value = value;
            }
        }

        Entry[] table;
        int basis;
        int filled = 0;

        public HashMap(int N, int basis) {
            table = (Entry[]) Array.newInstance(Entry[].class.getComponentType(), N);
            this.basis = basis;
        }

        public double fillRatio() {
            return (double) this.filled / this.table.length;
        }

        public int hashCode(String s) {
            int N = this.table.length;
            int n = s.length();
            int h = 0;

            for(int i = 1; i <= n; i++){
                int k = (int) s.charAt(i-1);
                int B = this.basis;
                h = (h * B + k) % N;
            }

            for(int m=0; m <= N; m++){
                if(this.table[h] != null && !this.table[h].key.equals(s)){
                    h = (h + 2*m +1) % N;
                }
                else{
                    return h;
                }
            }
            return -1;
        }

        public T get(String key) {
            int h = hashCode(key);
            if(h == -1){
                return null;
            }

            Entry e = this.table[h];
            if(e != null){
                return e.value;
            }
            return null;
        }

        public boolean add(String key, T value) {
            int h = hashCode(key);
            if(h == -1){
                return false;
            }
            if(this.table[h] == null){
                filled += 1;
            }
            this.table[h] = new Entry(key, value);
            return true;
        }
    }

    HashMap<HashMap<Integer>> languageTable;
    // n = the length of n-grams to use
    int n;

    // N = the size of the language-specific hash tables ("Tabelle 2")
    int N;

    int basis;

    private List<String> languageKeyList;

    public LanguageDetector(int n, int N) {
        this.n = n;
        this.N = N;
        this.basis = 31;

        this.languageTable = new HashMap<HashMap<Integer>>(101, this.basis);
        this.languageKeyList = new ArrayList<String>();

    }

    public void learnLanguage(String language, String text) {
        HashMap<Integer> textTable = this.languageTable.get(language);
        if(textTable == null){
            languageKeyList.add(language);
            textTable = new HashMap<Integer>(this.N, this.basis);
        }

        addnGrams(textTable, text);
        this.languageTable.add(language, textTable);

    }

    private HashMap<Integer> addnGrams(HashMap<Integer> textTable, String text){
        String nGram;
        for(int i = 0; i <= text.length() - n; i++){
            nGram = text.substring(i, i+n);
            Integer nGramCount = textTable.get(nGram);
            if(nGramCount == null){
                nGramCount = 1;
            } else {
                nGramCount +=1 ;
            }
            textTable.add(nGram, nGramCount);

        }

        return textTable;
    }


    public HashMap<Integer> apply(String text){
        HashMap<Integer> languageOfText = new HashMap<Integer>(this.N, this.basis);

        // Initialisieren von der Liste mit leeren Wert
        for(String language : languageKeyList) {
            languageOfText.add(language, 0);
        }

        String nGram;

        for(int i = 0; i <= text.length() - n; i++) {
            ArrayList<String> listOfCurrent = new ArrayList<String>();
            nGram = text.substring(i, i+n);
            int maxNGramCount = 0;

            for(String language : languageKeyList){
                Integer nGramCount = this.languageTable.get(language).get(nGram);
                if(nGramCount != null){
                    if(nGramCount > maxNGramCount){
                        listOfCurrent = new ArrayList<>();
                        listOfCurrent.add(language);
                        maxNGramCount = nGramCount;
                    }
                    else if(nGramCount == maxNGramCount){
                        listOfCurrent.add(language);
                    }
                }
            }
            for(String current : listOfCurrent){
                Integer numberOfLanguage = languageOfText.get(current);
                languageOfText.add(current, numberOfLanguage + 1);
            }
        }

        return languageOfText;

    }

    public int getCount(String ngram, String language) {
        HashMap<Integer> textTable = this.languageTable.get(language);
        if(textTable == null){
            return 0;
        }
        Integer nGramCount = textTable.get(ngram);
        if(nGramCount == null){
            return 0;
        }
        return nGramCount;
    }

    private static String read(String filename) {
        // !!! NO NEED TO CHANGE !!!
        // open a file, read its lines, return them as an array.
        try {

            StringBuilder sb = new StringBuilder();

            Reader in = new InputStreamReader(new FileInputStream(filename),
                    "UTF-8");

            BufferedReader reader = new BufferedReader(in);

            String s;
            while ((s = reader.readLine()) != null) {
                // Ignoriere Leerzeilen und Kommentare
                if (s.length() != 0 && s.charAt(0) != '#') {
                    sb.append(s);
                }
            }

            reader.close();

            return sb.toString();

        } catch (IOException e) {
            String msg = "I/O-Fehler bei " + filename + "\n" + e.getMessage();
            throw new RuntimeException(msg);
        }
    }


    public static LanguageDetector runTest(String BASE, int n, int N) {
        long startTime = System.nanoTime();

        LanguageDetector ld = new LanguageDetector(n,N);

        String[] languages = {"de", "en","eo","fi","fr","it"};
        String basePath = BASE + "/alice/";

        for(String language : languages){
            String filename = basePath + "alice." + language + ".txt";
            String text = read(filename);
            ld.learnLanguage(language, text);
        }

        // 1. read "Alice in Wonderland" in 6 languages using read(), see above
        // 2. learn the 6 languages with language detector
        // 3. apply language detector to the below 57 sample sentences.
        //    measure accuracy, fillratio, execution time.
        // 4. run for different n-gram lengths + table sizes, and report.

        String[] sentences = new String[] {

                // 11 x English
                "I'm going to make him an offer he can't refuse.",
                "Toto, I've got a feeling we're not in Kansas anymore.",
                "May the Force be with you.",
                "If you build it, he will come.",
                "I'll have what she's having.",
                "A martini. Shaken, not stirred.",
                "Some people can’t believe in themselves until someone else believes in them first.",
                "I feel the need - the need for speed!",
                "Carpe diem. Seize the day, boys. Make your lives extraordinary.",
                "Nobody puts Baby in a corner.",
                "I'm king of the world!",

                // 11 x Deutsch
                "Aber von jetzt an steht ihr alle in meinem Buch der coolen Leute.",
                "Wäre, wäre, Fahradkette",
                "Sehe ich aus wie jemand, der einen Plan hat?",
                "Erwartet mein Kommen, beim ersten Licht des fünften Tages.",
                "Du bist terminiert!",
                "Ich hab eine Wassermelone getragen.",
                "Einigen wir uns auf Unentschieden!",
                "Du wartest auf einen Zug, ein Zug der dich weit weg bringen wird.",
                "Ich bin doch nur ein Mädchen, das vor einem Jungen steht, und ihn bittet, es zu lieben.",
                "Ich genoss seine Leber mit ein paar Fava-Bohnen, dazu einen ausgezeichneten Chianti.",
                "Dumm ist der, der Dummes tut.",

                // 9 x Esperanto
                "Al du sinjoroj samtempe oni servi ne povas.",
                "Al la fiŝo ne instruu naĝarton.",
                "Fiŝo pli granda malgrandan englutas.",
                "Kia patrino, tia filino.",
                "La manĝota fiŝo estas ankoraŭ en la rivero.",
                "Ne kotas besto en sia nesto.",
                "Ne singardema kokino fidas je vulpo.",
                "Por sperto kaj lerno ne sufiĉas eterno.",
                "Unu hako kverkon ne faligas.",

                // 8 x Finnisch
                "Hei, hauska tavata.",
                "Olen kotoisin Suomesta.",
                "Yksi harrastuksistani on lukeminen.",
                "Nautin musiikin kuuntelusta.",
                "Juhannusperinteisiin kuuluu juhannussauna tuoreiden saunavihtojen kera, sekä pulahtaminen järveen.",
                "Aamu on iltaa viisaampi.",
                "Työ tekijäänsä neuvoo.",
                "Niin metsä vastaa, kuin sinne huudetaan.",

                // 9 x Französisch
                "Franchement, ma chère, c’est le cadet de mes soucis.",
                "À tes beaux yeux.",
                "Si j’aurais su, j’aurais pas v’nu!",
                "Merci la gueuse. Tu es un laideron mais tu es bien bonne.",
                "Vous croyez qu’ils oseraient venir ici?",
                "La barbe ne fait pas le philosophe.",
                "Inutile de discuter.",
                "Paris ne s’est pas fait en un jour!",
                "Quand on a pas ce que l’on aime, il faut aimer ce que l’on a.",

                // 9 x Italienisch
                "Azzurro, il pomeriggio è troppo azzurro e lungo per me",
                "Con te, cos lontano e diverso Con te, amico che credevo perso ",
                "è restare vicini come bambini la felicità",
                "Buongiorno, Principessa!",
                "Ho Ucciso Napoleone.",
                "L’amore vince sempre.",
                "La semplicità è l’ultima sofisticazione.",
                "Una cena senza vino e come un giorno senza sole.",
                "Se non hai mai pianto, i tuoi occhi non possono essere belli."
        };

        String[] labels = new String[] {

                "english","english","english","english","english","english","english","english","english","english","english",
                "german","german","german","german","german","german","german","german","german","german","german",
                "esperanto","esperanto","esperanto","esperanto","esperanto","esperanto","esperanto","esperanto","esperanto",
                "finnish","finnish","finnish","finnish","finnish","finnish","finnish","finnish",
                "french","french","french","french","french","french","french","french","french",
                "italian","italian","italian","italian","italian","italian","italian","italian","italian"

        };

        int correct = 0;
        int wrong = 0;
        double fillRatio = 0;

        for(int i=0; i < sentences.length; i++){
            HashMap<Integer> result = ld.apply(sentences[i]);
            fillRatio = result.fillRatio();
            String resultLanguage = findMaxCountLanguage(result, languages);
            if(resultLanguage.equals(labels[i].substring(0, 2))){
                correct += 1;
            }
            else{
                wrong += 1;
            }
        }


        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        System.out.println("N: "+ N + "   - n: " + n + "   - Duration: " + duration + "ms   - Fillratio: "+ fillRatio + "   - Accuracy: " + (double) correct / (correct + wrong));

        assert sentences.length == labels.length;
        return ld;
    }

    public static String findMaxCountLanguage(HashMap<Integer> languages, String[] languageKeyList){
        int maxNumber = -1;
        String resultLanguage = null;
        for(String language : languageKeyList){
            Integer count = languages.get(language);
            if(count > maxNumber){
                maxNumber = count;
                resultLanguage = language;
            }
            else if(count == maxNumber){
                if(resultLanguage != null && language.compareTo(resultLanguage) < 0){
                    resultLanguage = language;
                }
            }
        }
        return resultLanguage;
    }

    /*
    N: 120001   - n: 0   - Duration: 64ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.0
    N: 1200001   - n: 0   - Duration: 96ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.0
    N: 120001   - n: 1   - Duration: 54ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.15789473684210525
    N: 1200001   - n: 1   - Duration: 125ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.15789473684210525
    N: 120001   - n: 2   - Duration: 112ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.38596491228070173
    N: 1200001   - n: 2   - Duration: 162ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.38596491228070173
    N: 120001   - n: 3   - Duration: 67ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.5964912280701754
    N: 1200001   - n: 3   - Duration: 110ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.5964912280701754
    N: 120001   - n: 4   - Duration: 81ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.631578947368421
    N: 1200001   - n: 4   - Duration: 211ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.631578947368421
    N: 120001   - n: 5   - Duration: 99ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.631578947368421
    N: 1200001   - n: 5   - Duration: 306ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.631578947368421
    N: 120001   - n: 6   - Duration: 153ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.631578947368421
    N: 1200001   - n: 6   - Duration: 256ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.631578947368421
    N: 120001   - n: 7   - Duration: 87ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.6140350877192983
    N: 1200001   - n: 7   - Duration: 168ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.6140350877192983
    N: 120001   - n: 8   - Duration: 111ms   - Fillratio: 4.999958333680553E-5   - Accuracy: 0.5614035087719298
    N: 1200001   - n: 8   - Duration: 216ms   - Fillratio: 4.999995833336806E-6   - Accuracy: 0.5614035087719298


     */

    public static void main(String[] args) {
        int[] N_array = {120001, 1200001};

        for(int n = 0; n < 9; n++) {
            for(int N : N_array) {
                runTest("./src/main/resources", n, N);
            }
        }
    }

}