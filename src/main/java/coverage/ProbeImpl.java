package coverage;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ProbeImpl implements Probe {

    private Map<String, Map<Integer, Integer>> results;

    private String currentMethod;

    private String probed;

    Logger logger = Logger.getLogger(ProbeImpl.class);

    public ProbeImpl(ProbeManager manager, String probed) {
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
    public void probeLine(Integer number)  {
        if (currentMethod != null) {
            results.get(currentMethod).put(number, results.get(currentMethod).get(number) + 1);
        }
    }

    @Override
    public String getProbedClassName() {
        return probed;
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
