# Word Finder

This code implements the 'searchCode' class that implements the 'wordFinder' interface.

From a given word stream, the initials of each word are obtained and stored as the key, with the word itself being stored in a set as the value.

Once the map is obtained, it iterates over all the cells of the matrix in order to search for words. For this purpose, the keys of the map from the previous step are compared with the string in each cell.

In the case of a match, a search for possible words starting with the letter of the cell is performed. If the word is found, it is stored in a map where the number of occurrences of the word in the matrix is associated with it.

At the end of the iteration over the matrix, the top 10 words with the most occurrences are returned.