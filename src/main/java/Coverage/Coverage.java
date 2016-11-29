package Coverage;

public class Coverage {

    private static String SONDE_VAR_NAME = "sonde";

    private static String PROBE_CONCRETE_NAME = ProbeImpl.class.getName();

    private static String PROBE_INTERFACE_NAME = Probe.class.getName();

    private static String CLASS_NAME = Coverage.class.getName();

    private static ProbeManager manager = new ProbeManagerImpl();

    static ProbeManager getProbeManager() {
        return manager;
    }

    static String registerClass(Class c) {
        return String.format("%s %s = new %s(%s.getProbeManager())",
                PROBE_INTERFACE_NAME,
                SONDE_VAR_NAME,
                PROBE_CONCRETE_NAME,
                CLASS_NAME);
    }

    static String registerLine(Integer number)
    {
        return String.format("%s.probeLine(%d)", SONDE_VAR_NAME, number);
    }

    static String registerMethod(String signature)
    {
        return String.format("%s.probeMethod(%s)", SONDE_VAR_NAME, signature);
    }

    static void showResults()
    {
        getProbeManager().showResults();
    }
}
