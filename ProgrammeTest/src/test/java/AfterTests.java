import coverage.Coverage;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestDoubleRecursive.class, TestFor.class, TestIf.class, TestRecursive.class})
public class AfterTests {

    @AfterClass
    public static void showCoverageResults(){
        Coverage.getProbeManager().showResults();
    }
}
