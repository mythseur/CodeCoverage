package coverage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProbeImpl implements Probe {

    private Map<String, Map<Integer, Integer>> results;

    private String currentMethod;

    private String probed;

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
        System.out.println(probed);
        results.entrySet().forEach(
                entry ->
                {
                    System.out.println(entry.getKey());
                    entry.getValue().entrySet().forEach(
                            entryline -> System.out.println(String.format(
                                    "Ligne %d passé %d fois",
                                    entryline.getKey(),
                                    entryline.getValue()
                            )));
                }
        );
    }

    public Map<String, Map<Integer, Integer>> getResults()
    {
        return Collections.singletonMap(probed, results.entrySet().stream()
        .map(Map.Entry::getValue).map(Map::entrySet).flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                )));
    }
}
