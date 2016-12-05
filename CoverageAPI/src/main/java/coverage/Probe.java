package coverage;

import java.util.Map;

public interface Probe {

    void probeMethod(String signature);

    void probeLine(Integer number);

    String getProbedClassName();

    void logResults();

    Map<String, Map<Integer, Integer>> getResults();
}