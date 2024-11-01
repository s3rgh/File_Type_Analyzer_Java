package analyzer.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilePatternDatabase {
    private List<FilePattern> patterns = new ArrayList<>();
    private static FilePatternDatabase INSTANCE;

    private FilePatternDatabase() {
    }

    public static FilePatternDatabase createInstance(String path) {
        if (INSTANCE == null) {
            INSTANCE = new FilePatternDatabase();
            INSTANCE.setPatterns(INSTANCE.readPatterns(path));
        }
        return INSTANCE;
    }

    public List<FilePattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<FilePattern> patterns) {
        this.patterns = patterns;
    }

    private List<FilePattern> readPatterns(String path) {
        List<String> fileContent;

        try {
            fileContent = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new IllegalStateException("something went wrong during reading the file " + "patterns.db", e);
        }

        for (String s : fileContent) {
            var strings = s.split(";");
            try {
                patterns.add(new FilePattern(
                        Integer.parseInt(
                                strings[0]),
                        removeQuotes(strings[1]),
                        removeQuotes(strings[2]))
                );
            } catch (NumberFormatException e) {
                throw new IllegalStateException("incorrect parsing " + strings[0], e);
            }
        }
        return patterns;
    }

    private String removeQuotes(String string) {
        return string.replaceAll("^\"|\"$", "");
    }
}
