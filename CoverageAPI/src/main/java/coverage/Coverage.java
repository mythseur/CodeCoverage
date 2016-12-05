package coverage;

public class Coverage {

    private static ProbeManager manager = new ProbeManagerImpl();

    public static ProbeManager getProbeManager() {
        return manager;
    }
}
