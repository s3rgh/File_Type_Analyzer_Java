package analyzer.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

public class NaiveAlgorithm implements BaseAlgorithm {
    @Override
    public boolean checkFile(String filename, String pattern, String type) {
        var mask = pattern.getBytes();
        byte[] fileBytes;

        var time = System.currentTimeMillis();
        try {
            fileBytes = Files.readAllBytes(Path.of(filename));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }

        for (int i = 0; i <= fileBytes.length - mask.length; i++) {
            if (Arrays.equals(Arrays.copyOfRange(fileBytes, i, i + mask.length), mask)) {
                System.out.println(type);
                var fullTime = (double) ((System.currentTimeMillis() - time) / 1000);
                System.out.printf(Locale.US, "It took %.3f seconds", fullTime);
                return true;
            }
        }
        System.out.println("Unknown file type");
        var fullTime = (double) ((System.currentTimeMillis() - time) / 1000);
        System.out.printf(Locale.US, "It took %.3f seconds", fullTime);
        return false;
    }
}
