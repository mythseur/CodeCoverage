package coverage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProbeManagerImpl implements ProbeManager {

    private List<Probe> sondesList;

    ProbeManagerImpl()
    {
        sondesList = new ArrayList<>();
    }

    @Override
    public void registerProbe(Probe probe) {
        sondesList.add(probe);
    }

    @Override
    public void showResults() {
        OutputWriter.writeResults(sondesList.stream().map(Probe::getResults).map(Map::entrySet).
                flatMap(Collection::stream).collect(Collectors.toMap(
                        Map.Entry::getKey,
                Map.Entry::getValue
        )));
    }


}
