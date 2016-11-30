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
        results.computeIfAbsent(signature, k -> new HashMap<>());
        currentMethod = signature;
    }

    @Override
    public void probeLine(Integer number)  {
        Integer current = results.get(currentMethod).get(number);
        if (currentMethod != null) {
            results.get(currentMethod).put(number, (current == null ? 0 : current) + 1);
        }
    }

    @Override
    public String getProbedClassName() {
        return probed;
    }

    @Override
    public void logResults() {
        logger.warn(probed);
        results.entrySet().forEach(
                entry ->
                {
                    logger.warn(entry.getKey());
                    entry.getValue().entrySet().forEach(
                            entryline -> logger.info(String.format(
                                    "Ligne %d passé %d fois",
                                    entryline.getKey(),
                                    entryline.getValue()
                            )));
                }
        );
    }
}
