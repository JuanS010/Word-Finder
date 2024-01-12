package org.wf;

import java.util.*;

public class searchCode implements wordFinder{

    // two-dimensional array of string for matrix
    private final String[][] matrix;

    // Stores found words as key and their frequency as a value
    private final HashMap<String, Integer> words;

    // Length of strings in each cell
    private final Integer cellStrLen;

    // Constructor takes matrix as an argument and initializes variables
    public searchCode(String[][] matrix) {

        this.cellStrLen = matrix[0][0].length(); // All cells have the same string length
        this.matrix = matrix;
        this.words = new HashMap<>();
    }

    // Implementation of Find. Updates the 'words' map
    public List<String> Find(List<String> wordstream) {

        if (wordstream.isEmpty()) {
            return getTop10Words(words);
        }

        // Get map of initial letters and their associated words
        Map<String, HashSet<String>> initials = getInitialLetters(wordstream);

        // Iterate through the cells of the matrix
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {

                String cellLetter = matrix[x][y].toLowerCase();

                // Check if some possible word contains the cell string as initial letter
                if (initials.containsKey(cellLetter)) {

                    HashSet<String> possibleWords = initials.get(cellLetter);

                    // Iterate over possible words
                    for (String word : possibleWords) {

                        if (findToRight(word.substring(cellStrLen), x, y)) {
                            words.put(word, words.getOrDefault(word, 0) + 1);
                        }

                        // Avoid store duplicate words if they have the same length as the cell
                        if (word.length() == cellStrLen) {
                            continue;
                        }

                        if (findToDown(word.substring(cellStrLen), x, y)) {
                            words.put(word, words.getOrDefault(word, 0) + 1);
                        }

                    }

                }
            }
        }

        return getTop10Words(words);
    }

    // Method to find to the right from the given position
    private Boolean findToRight(String word, int x, int y) {

        // Base case. Iterated over the entire word. The word was found
        if (word.isEmpty()) {
            return true;
        }

        // Check if it is possible move to next cell
        if (y+1 >= matrix[x].length) {
            return false;
        }

        // Check if the portion of the word matches the current cell
        y += 1;
        if (word.length()-cellStrLen >= 0 && word.substring(0, cellStrLen).equals(matrix[x][y])) {
            return findToRight(word.substring(cellStrLen), x, y);
        }

        return false;
    }

    // Method to find to the bottom from the given position
    private Boolean findToDown(String word, int x, int y) {

        // Base case. Iterated over the entire word. The word was found
        if (word.isEmpty()) {
            return true;
        }

        // Check if it is possible move to next cell
        if (x+1 >= matrix.length || matrix[x+1].length <= y) {
            return false;
        }

        // Check if the portion of the word matches the current cell
        x += 1;
        if (word.length()-cellStrLen >= 0 && word.substring(0, cellStrLen).equals(matrix[x][y])) {
            return findToDown(word.substring(cellStrLen), x, y);
        }

        return false;
    }

    // Method to get initials letters and their associated words
    private Map<String, HashSet<String>> getInitialLetters(List<String> wordstream) {

        Map<String, HashSet<String>> initials = new HashMap<>();

        for (String word : wordstream) {

            word = word.toLowerCase();

            // Check if word length is multiple of the cell string lengths
            // This validation avoid loop over unfindable words later
            if (word.length() % cellStrLen != 0) {
                continue;
            }

            String initialLetter = word.substring(0, cellStrLen);

            if (!initials.containsKey(initialLetter)) {
                initials.put(initialLetter, new HashSet<>());
            }
            if (!initials.get(initialLetter).contains(word)) {
                HashSet<String> wordsList =  initials.get(initialLetter);
                wordsList.add(word);
                initials.put(initialLetter, wordsList);
            }
        }

        return initials;
    }

    // Method to get the top 10 words most appeared
    private List<String> getTop10Words(HashMap<String, Integer> words) {

        List<String> top10Words = words.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        return top10Words;
    }
}
