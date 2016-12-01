import coverage.Coverage;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class RunTestCoverage extends RunListener{

    @Override
    public void testRunFinished(Result result) throws Exception {
        Coverage.getProbeManager().showResults();
        super.testRunFinished(result);
    }
}
