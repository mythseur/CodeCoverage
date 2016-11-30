package programme;

import org.junit.Test;
import programme.Recursive;

import static org.junit.Assert.assertEquals;

public class TestRecursive {

    @Test
    public void testRecursive(){

        assertEquals(0, Recursive.decrement(5));
    }
}
