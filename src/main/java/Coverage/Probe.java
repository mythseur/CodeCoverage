package Coverage;

public interface Probe {

    void probeMethod(String signature);

    void probeLine(Integer number) throws CoverageException;

    String getProbedClassName();

    void logResults();
}
