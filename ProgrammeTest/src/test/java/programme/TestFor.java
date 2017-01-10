package programme;

import org.junit.Test;
import programme.For;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class TestFor {

    @Test
    public void testFor() {
        For f = new For(5);
        List<Integer> expected = new ArrayList<>(asList(0, 1, 2, 3, 4));
        assertEquals(expected, f.buildList());
        expected.sort((o1, o2) -> o2 - o1);
        assertEquals(expected, f.buildReverseList());
        assertEquals(10, ForEach.sum(f.buildList()));
    }


}
