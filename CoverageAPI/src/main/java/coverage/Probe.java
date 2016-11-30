package coverage;

public interface Probe {

    void probeMethod(String signature);

    void probeLine(Integer number);

    String getProbedClassName();

    void logResults();
}
