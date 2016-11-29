package Coverage;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ProbeImpl implements Probe {

    private Map<String, Map<Integer, Integer>> results;

    private String currentMethod;

    private Class probed;

    Logger logger = Logger.getLogger(ProbeImpl.class);

    ProbeImpl(ProbeManager manager, Class probed) {
        this.probed = probed;
        manager.registerProbe(this);
        results = new HashMap<>();
    }

    @Override
    public void probeMethod(String signature) {
        results.put(signature, new HashMap<>());
        currentMethod = signature;
    }

    @Override
    public void probeLine(Integer number) throws CoverageException {
        if (currentMethod != null) {
            results.get(currentMethod).put(number, results.get(currentMethod).get(number) + 1);
        } else {
            throw new CoverageException("Line probing when no method set");
        }
    }

    @Override
    public String getProbedClassName() {
        return probed.getName();
    }

    @Override
    public void logResults() {
        results.entrySet().forEach(
                entry ->
                {
                    logger.warn(entry.getKey());
                    entry.getValue().entrySet().forEach(
                            entryline -> logger.info(String.format(
                                    "Ligne %d passé %d fois",
                                    entryline.getValue(),
                                    entryline.getKey()
                            )));
                }
        );
    }
}
