package Coverage;

import java.util.ArrayList;
import java.util.List;

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
        sondesList.forEach(Probe::logResults);
    }


}
