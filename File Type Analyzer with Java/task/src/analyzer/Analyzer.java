package analyzer;

import analyzer.algorithms.RabinKarpAlgorithm;
import analyzer.database.FilePattern;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

import static analyzer.database.FilePatternDatabase.createInstance;

public class Analyzer {
    public void analyze(String[] input) {
        var algorithm = new RabinKarpAlgorithm();
        var directoryPath = input[0];
        var patternsDbPath = input[1];
        var patternsList = createInstance(patternsDbPath).getPatterns();

        try (var stream = Files.newDirectoryStream(Path.of(directoryPath))) {
            var es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (Path p : stream) {
                var priority = 0;
                var type = "Unknown file type";

                for (FilePattern fp : patternsList) {
                    var callable = new MySearchCallable(algorithm, p.toString(), fp.pattern(), fp.type());
                    var future = es.submit(callable);
                    var check = future.get();
                    if (check) {
                        var newPriority = fp.priority();
                        if (newPriority > priority) {
                            priority = newPriority;
                            type = fp.type();
                        }
                    }
                }
                System.out.println(p.getFileName() + ": " + type);
            }
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
