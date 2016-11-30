package coverage;

import org.junit.runner.JUnitCore;
import org.reflections.Reflections;

import java.util.Set;

public class RunTestCoverage {
    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        Reflections reflections = new Reflections("");

        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        junit.run();
        Coverage.showResults();
    }
}
