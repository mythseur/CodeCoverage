package coverage;

import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.CodeFactory;

public class Coverage {

    private static String SONDE_VAR_NAME = "sonde";

    private static String PROBE_CONCRETE_NAME = ProbeImpl.class.getName();

    private static Class PROBE_INTERFACE_NAME = Probe.class;

    private static String CLASS_NAME = Coverage.class.getName();

    private static ProbeManager manager = new ProbeManagerImpl();

    public static ProbeManager getProbeManager() {
        return manager;
    }

    public static CtField registerClass(CodeFactory codeFactory, String actualClass) {
        return codeFactory.createCtField(
                SONDE_VAR_NAME,
                codeFactory.createCtTypeReference(PROBE_INTERFACE_NAME),
                String.format("new %s(%s.getProbeManager(), \"%s\")",
                        PROBE_CONCRETE_NAME,
                        CLASS_NAME,
                        actualClass),
                ModifierKind.STATIC
        );
    }

    public static String registerLine(Integer number)
    {
        return String.format("%s.probeLine(%d)", SONDE_VAR_NAME, number);
    }

    public static String registerMethod(String signature)
    {
        return String.format("%s.probeMethod(\"%s\")", SONDE_VAR_NAME, signature);
    }

    static void showResults()
    {
        getProbeManager().showResults();
    }
}
