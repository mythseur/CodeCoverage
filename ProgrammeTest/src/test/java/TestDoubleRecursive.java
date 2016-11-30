import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDoubleRecursive {

    @Test
    public void testDoubleRecursive(){
        DoubleRecursive dr = new DoubleRecursive();

        assertEquals(3, dr.f1(2,5));
    }

    @Test
    public void testDoubleRecursive2(){
        DoubleRecursive dr = new DoubleRecursive();

        assertEquals(2, dr.f1(5,2));
    }
}
