package org.wf;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String[][] matrix = {
                {"a", "b", "c", "d", "c"},
                {"f", "g", "w", "i", "o"},
                {"c", "h", "i", "l", "l"},
                {"p", "q", "n", "s", "d"},
                {"u", "v", "d", "x", "y"}
        };
        List<String> wordstream = List.of("cold", "wind", "snow", "chill");

        wordFinder wf = new searchCode(matrix);
        List<String> top10Words = wf.Find(wordstream);
        System.out.println(top10Words);
    }
}