package analyzer;

import analyzer.algorithms.BaseAlgorithm;

import java.util.concurrent.Callable;

public class MySearchCallable implements Callable<Boolean> {
    private final BaseAlgorithm algorithm;
    private final String path;
    private final String pattern;
    private final String type;

    public MySearchCallable(BaseAlgorithm algorithm, String path, String pattern, String type) {
        this.algorithm = algorithm;
        this.path = path;
        this.pattern = pattern;
        this.type = type;
    }

    @Override
    public Boolean call() {
        return algorithm.checkFile(path, pattern, type);
    }
}
