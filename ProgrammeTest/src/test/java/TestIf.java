import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIf {

    @Test
    public void Test1()
    {
        If laclasse = new If();
        assertEquals("Je suis autre chose !", laclasse.run(80));
    }



}
