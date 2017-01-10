package programme;

import java.util.ArrayList;

public class ForEach {

    public static int sum(ArrayList<Integer> list) {
        int res = 0;
        for (Integer i : list) {
            res += i;
        }
        return res;
    }
}
