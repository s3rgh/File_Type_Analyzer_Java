package analyzer.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class KMPAlgorithm implements BaseAlgorithm {
    @Override
    public boolean checkFile(String filename, String sample, String type) {
        String lines;

        try {
            lines = String.join("", Files.readAllLines(Path.of(filename)));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }

        return KMPSearch(lines, sample);
    }

    private int[] getPrefixFunction(String sample) {
        var values = new int[sample.length()];
        for (int i = 1; i < sample.length(); i++) {
            var j = 0;
            while (j + i < sample.length() && sample.charAt(j) == sample.charAt(i + j)) {
                values[i + j] = Math.max(values[i + j], j + 1);
                j++;
            }
        }
        return values;
    }

    private boolean KMPSearch(String text, String sample) {
        var prefix = getPrefixFunction(sample);
        var i = 0;
        var j = 0;
        while (i < text.length()) {
            if (sample.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == sample.length()) {
                return true;
            } else if (i < text.length() && text.charAt(i) != sample.charAt(j)) {
                if (j != 0) {
                    j = prefix[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
        return false;
    }
}
