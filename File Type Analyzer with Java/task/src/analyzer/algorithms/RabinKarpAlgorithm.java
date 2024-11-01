package analyzer.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.math.BigInteger;

public class RabinKarpAlgorithm implements BaseAlgorithm {
    private static final BigInteger X = BigInteger.valueOf(31);
    private static final BigInteger Q = BigInteger.valueOf(2147483647);

    @Override
    public boolean checkFile(String filename, String sample, String type) {
        String line;

        try {
            line = String.join("", Files.readAllLines(Path.of(filename)));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }
        return RKSearch(line, sample);
    }

    private boolean RKSearch(String line, String sample) {
        BigInteger sampleHash = calculateHash(sample);
        int sampleLength = sample.length();
        if (sampleLength > line.length()) {
            return false;
        }
        BigInteger currentHash = calculateHash(line.substring(0, sampleLength));
        int i = 0;

        while (true) {
            if (currentHash.equals(sampleHash)) {
                if (sample.equals(line.substring(i, i + sampleLength))) {
                    return true;
                }
            }
            if (i + sampleLength >= line.length()) {
                break;
            }
            // Пересчитываем текущее хэш-значение, "уменьшая" его и добавляя следующий символ
            currentHash = currentHash.subtract(BigInteger.valueOf(line.charAt(i)).multiply(X.pow(sampleLength - 1)))
                    .multiply(X)
                    .add(BigInteger.valueOf(line.charAt(i + sampleLength)))
                    .mod(Q);
            i++;
        }
        return false;
    }

    private BigInteger calculateHash(String line) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < line.length(); i++) {
            result = result.multiply(X).add(BigInteger.valueOf(line.charAt(i)));
        }
        return result.mod(Q);
    }
}
