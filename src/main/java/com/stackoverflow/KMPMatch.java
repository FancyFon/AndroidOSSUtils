package com.stackoverflow;

/**
 * Knuth-Morris-Pratt Algorithm for Pattern Matching
 *
 * https://stackoverflow.com/questions/1507780/searching-for-a-sequence-of-bytes-in-a-binary-file-with-java
 */
public class KMPMatch {

    /**
     * Finds the first occurrence of the pattern in the text.
     * 
     * @return index of first occurence, or -1 if not found
     */
    public int indexOf(byte[] data, byte[] pattern, int startPosition) {
        int[] failure = computeFailure(pattern);

        int j = 0;
        if (data.length <= startPosition) {
            return -1;
        }

        for (int i = startPosition; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) {
                j++;
            }
            if (j == pattern.length) {
                return i - pattern.length + 1;
            }
        }
        return -1;
    }

    /**
     * Computes the failure function using a boot-strapping process, where the
     * pattern is matched against itself.
     */
    private int[] computeFailure(byte[] pattern) {
        int[] failure = new int[pattern.length];

        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }

        return failure;
    }
}
